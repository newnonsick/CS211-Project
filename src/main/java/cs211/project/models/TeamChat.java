package cs211.project.models;

public class TeamChat {
    private String teamName;
    private String eventName;
    private String username;
    private String message;

    public TeamChat(String eventName, String teamName, String username, String message) {
        this.teamName = teamName;
        this.eventName = eventName;
        this.username = username;
        this.message = message;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getEventName() {
        return eventName;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
