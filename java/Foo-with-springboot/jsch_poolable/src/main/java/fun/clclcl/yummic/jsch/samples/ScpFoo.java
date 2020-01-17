package fun.clclcl.yummic.jsch.samples;

import fun.clclcl.yummic.jsch.ScpTo;
import fun.clclcl.yummic.jsch.SshLogger;
import fun.clclcl.yummic.jsch.pool.SshConfig;
import fun.clclcl.yummic.jsch.pool.SshSessionManager;

import java.io.IOException;

public class ScpFoo {
    public static void main(String[] args) {
        SshLogger logger = new SshLogger() {
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
        };
        SshSessionManager sshManager = new SshSessionManager();
        sshManager.setExceptionConsumer(logger);


        SshConfig sshconfig = new SshConfig("192.168.102.36", "root", "password");
        try {
            ScpTo.copy("D:\\tmp\\fab172.bin", "/tmp/test2.file", false, sshManager, sshconfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("success!");
    }
}
