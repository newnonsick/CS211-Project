package cs211.project.models;

public class TeamParticipant {
    private String eventName;
    private String teamName;
    private String username;

    public TeamParticipant(String username, String eventName, String teamName) {
        this.username = username;
        this.eventName = eventName;
        this.teamName = teamName;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEventName() {
        return this.eventName;
    }

}
