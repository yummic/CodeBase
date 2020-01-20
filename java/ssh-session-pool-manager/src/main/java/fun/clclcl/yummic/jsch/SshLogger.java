package fun.clclcl.yummic.jsch;

import com.jcraft.jsch.Logger;

public abstract class SshLogger implements Logger {

    public static SshLogger DEFAULT = new SshLogger() {

        @Override
        public void log(int level, String message, Exception e) {

        }

        @Override
        public boolean isEnabled(int level) {
            return false;
        }

        @Override
        public void log(int level, String message) {

        }

    };

    public abstract void log(int level, String message, Exception e);

}
