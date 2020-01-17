package fun.clclcl.yummic.jsch;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import fun.clclcl.yummic.jsch.pool.SshConfig;
import fun.clclcl.yummic.jsch.pool.SshSessionManager;
import org.apache.commons.lang.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class SshExec {

    static final Logger LOGGER = LoggerFactory.getLogger(SshExec.class);

    //mkdir -p
    public static boolean mkdirs(String path, SshSessionManager sshManager, SshConfig sshConfig) {
        String cmd = "mkdir -p " + path;
        MutableInt status = new MutableInt();
        String result = runCommand(cmd, status, sshManager, sshConfig);
        if (status.intValue() != 0) {
            LOGGER.error("mkdir failed. code: {}, result: {}", status.intValue(), result);
        }
        return status.intValue() == 0;
    }

    public static boolean ifFileExists(String path, SshSessionManager sshManager, SshConfig sshConfig) {
        String cmd = "ls " + path;
        MutableInt status = new MutableInt();
        String result = runCommand(cmd, status, sshManager, sshConfig);
        if (status.intValue() != 0) {
            LOGGER.error("ls file failed. code: {}, result: {}", status.intValue(), result);
        }
        return status.intValue() == 0;

    }

    public static void reboot(SshSessionManager sshManager, SshConfig sshConfig) {
        String cmd = "systemctl reboot";
        MutableInt status = new MutableInt();
        String result = runCommand(cmd, status, sshManager, sshConfig);
        LOGGER.error("Reboot return code: {}, result: {}", status.intValue(), result);
    }

    public static String runCommand(String cmd, MutableInt status, SshSessionManager sshManager, SshConfig sshConfig) {
        Session session = null;
        try {
            session = sshManager.getSession(sshConfig);
            ChannelExec channel= (ChannelExec) session.openChannel("exec");
            channel.setCommand(cmd);
            channel.setInputStream(null);
            channel.setErrStream(null);
            InputStream in = channel.getInputStream();
            InputStream in_ext = channel.getErrStream();

            channel.connect();

            StringBuilder builder = new StringBuilder();
            StringBuilder errBuilder = new StringBuilder();

            byte[] tmp = new byte[1024];
            while (true) {
                if (in.available() > 0) {
                    int len = in.read(tmp, 0, 1024);
                    if (len > 0) {
                        builder.append(new String(tmp, 0, len));
                    }
                }
                if (in_ext.available() > 0) {
                    int len = in_ext.read(tmp, 0, 1024);
                    if (len > 0) {
                        errBuilder.append(new String(tmp, 0, len));
                    }
                }
                if (channel.isClosed()) {
                    if(in.available() > 0) {// The remaining bytes should be read even channel is closed .
                        continue;
                    }
                    status.setValue(channel.getExitStatus());
                    break;
                }
                sleepWithoutException(500);
            }
            channel.disconnect();
            if (errBuilder.length() > 0) {
                LOGGER.error("Execute cmd {} on host {}, output error:{}", cmd, sshConfig.getHost(), errBuilder.toString());
            }
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException("Execute command failed.", e);
        } finally {
            sshManager.returnSession(sshConfig.getHost(), session);
        }
    }

    private static void sleepWithoutException(int millis) {
        try{
            Thread.sleep(millis);
        } catch (Exception ee) {
            //ignore.
        }
    }
}
