package session;

public class UserSession {
    private String username;
    private boolean loggedIn;

    public UserSession() {
        // コンストラクタで初期化
        this.loggedIn = false;
    }

    public void login(String username) {
        this.username = username;
        this.loggedIn = true;
    }

    public void logout() {
        this.username = null;
        this.loggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
