package fun.clclcl.yummic.jsch.samples;

import fun.clclcl.yummic.jsch.SshExec;
import fun.clclcl.yummic.jsch.SshLogger;
import fun.clclcl.yummic.jsch.pool.SshConfig;
import fun.clclcl.yummic.jsch.pool.SshSessionManager;
import org.apache.commons.lang.mutable.MutableInt;

import java.util.Random;

public class SshFoo {
    static SshSessionManager sshManager;

    public static void main(String[] args) {
        sshManager = new SshSessionManager();
        sshManager.setExceptionConsumer(new SshLogger() {
            @Override
            public void log(int level, String message, Exception e) {
                System.out.println("log" + message + e.getMessage());
            }

            @Override
            public boolean isEnabled(int level) {
                return true;
            }

            @Override
            public void log(int level, String message) {
                System.out.println("log : " + message);
            }
        });

        for (int i = 0; i < 3; i++) {
            String ip = "192.168.102.39";
            executetest(ip);
        }

        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static void executetest(String ip) {
        Thread t = new Thread(() -> {

            SshConfig sshconfig = new SshConfig(ip, "root", "password");
            MutableInt status = new MutableInt(-1);
            try {
                int i = 0;
                while (i < 1) {
                    try {
                        String result = SshExec.runCommand("python /opt/test.py", status, sshManager, sshconfig);//sshManager.execute(sshconfig, "hostname", status);
                        print(status.intValue(), result);
                    } catch (Exception e) {
                        System.out.println("++++++++++++++++++++++++ eerrrrroooorrr.");
                        e.printStackTrace();
                    }
                    /*
                    result = sshManager.execute(sshconfig, "python /opt/uniio/test.py --help", status);
                    print(status.intValue(), result);
                    result = sshManager.execute(sshconfig, "systemctl status objmgr-fab", status);
                    print(status.intValue(), result);
                    result = sshManager.execute(sshconfig, "systemctl status objmgr", status);
                    print(status.intValue(), result);
                    result = sshManager.execute(sshconfig, "date", status);
                    print(status.intValue(), result);
*/
                    Thread.sleep(new Random().nextInt(100) * 1000 + 5000);
                }
                sshManager.remove(ip);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        });
        t.setDaemon(true);
        t.start();

    }

    private static void print(int status, String result) {
        System.out.println("==========[ " + status + " ],  " + result);
    }
}
