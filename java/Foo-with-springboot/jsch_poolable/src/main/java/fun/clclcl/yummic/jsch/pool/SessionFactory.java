package fun.clclcl.yummic.jsch.pool;

import com.jcraft.jsch.*;
import fun.clclcl.yummic.jsch.SshLogger;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.Objects;

public class SessionFactory extends BasePooledObjectFactory<Session> {

    private static SshLogger logger = SshLogger.DEFAULT;

    private SshConfig sshConfig;

    private JSch jSch;

    public SessionFactory(SshConfig sshConfig) throws JSchException {
        this.sshConfig = new SshConfig(sshConfig.getHost());
        this.sshConfig.setUser(sshConfig.getUser());
        this.sshConfig.setPassword(sshConfig.getPassword());
        this.sshConfig.setPort(sshConfig.getPort());
        this.sshConfig.setTimeout(sshConfig.getTimeout());
        this.sshConfig.setAuthType(sshConfig.getAuthType());
        this.sshConfig.setPassphrase(sshConfig.getPassphrase());
        this.sshConfig.setPubKey(sshConfig.getPubKey());

        jSch = new JSch();
        if (this.sshConfig.getAuthType() == SshConfig.AUTH_KEY) {
            jSch.addIdentity(sshConfig.getPubKey(), sshConfig.getPassphrase());
        }
    }

    @Override
    public Session create() throws Exception {
        Session session = jSch.getSession(sshConfig.getUser(), sshConfig.getHost(), sshConfig.getPort());
        if (sshConfig.getAuthType() == SshConfig.AUTH_PWD) {
            session.setPassword(sshConfig.getPassword());
        }
        UserInfo userinfo = new DefaultUserInfo();
        session.setUserInfo(userinfo);
        session.connect(sshConfig.getTimeout());
        return session;
    }

    @Override
    public PooledObject<Session> wrap(Session session) {
        return new DefaultPooledObject(session);
    }

    @Override
    public void destroyObject(PooledObject<Session> p) throws Exception {
        p.getObject().disconnect();
    }

    @Override
    public boolean validateObject(PooledObject<Session> p) {
        try {
            Session session = p.getObject();
            if (session.isConnected()) {
                session.sendKeepAliveMsg();
                return true;
            }
        } catch (Exception e) {
            logger.log(Logger.ERROR,"Validate failed.", e);
        }
        return false;
    }

    @Override
    public void activateObject(PooledObject<Session> p) throws Exception {
        super.activateObject(p);
    }

    @Override
    public void passivateObject(PooledObject<Session> p) throws Exception {
        super.passivateObject(p);
    }

    public void setExceptionConsumer(SshLogger logger) {
        Objects.requireNonNull(logger, "log comsumer cannot be null!");
        this.logger = logger;
        jSch.setLogger(logger);
    }

    public static class DefaultUserInfo implements UserInfo {

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public boolean promptPassword(String message) {
            logger.log(Logger.INFO, "No password should be input here.");
            return false;
        }

        @Override
        public boolean promptPassphrase(String message) {
            logger.log(Logger.INFO, "No passphrase should be input here.");
            return false;
        }

        @Override
        public boolean promptYesNo(String message) {
            logger.log(Logger.INFO, "[Jsch Ask message] : " + message);
            if (message == null) {
                return false;
            }
            if (message.contains("REMOTE HOST IDENTIFICATION HAS CHANGED")) {
                return true;
            }
            if (message.contains("Are you sure you want to continue connecting")) {
                return true;
            }
            if (message.contains("does not exist") && message.contains("Are you sure you want to create it")) {
                return true;
            }
            return false;
        }

        @Override
        public void showMessage(String message) {
            logger.log(Logger.INFO, "[Jsch show message] : " + message);
        }
    }
}
