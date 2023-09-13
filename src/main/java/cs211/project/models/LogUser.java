package cs211.project.models;

public class LogUser extends User {
    private String logTime;
    public LogUser(String username, String logTime) {
        super(username);
        this.logTime = logTime;
    }

    public String getLogTime() {
        return this.logTime;
    }
}
