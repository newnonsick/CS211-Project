package cs211.project.models;

public class TeamChat {
    private String teamName;
    private String eventUUID;
    private String username;
    private String message;

    public TeamChat(String eventUUID, String teamName, String username, String message) {
        this.teamName = teamName;
        this.eventUUID = eventUUID;
        this.username = username;
        this.message = message;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getEventUUID() {
        return eventUUID;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
