package fun.clclcl.yummic.jsch.pool;

public class SshConfig {

    public static final int AUTH_PWD = 0;

    public static final int AUTH_KEY = 1;

    private String host;

    private int port = 22;

    private String user;

    private String password;

    private String pubKey;

    private String passphrase;

    private int authType;

    private int timeout;

    public SshConfig(String host) {
        this(host, null, null);
    }

    public SshConfig(String host, String user, String password) {
        this(host, user, password, 22, 0);
    }

    public SshConfig(String host, String user, String password, int port, int timeout) {
        this(host, port, user, password, null, null, AUTH_PWD, timeout);
    }

    public SshConfig(String host, String user, String pubKey, String passphrase) {
        this(host, user, pubKey, passphrase, 22, 0);
    }

    public SshConfig(String host, String user, String pubKey, String passphrase, int port, int timeout) {
        this(host, port, user, null, pubKey, passphrase, AUTH_KEY, timeout);
    }

    public SshConfig(String host, int port, String user, String password, String pubKey, String passphrase, int authType, int timeout) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.pubKey = pubKey;
        this.passphrase = passphrase;
        this.authType = authType;
        this.timeout = timeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
