package cs211.project.models;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class Team {
    private String headOfTeamUsername;
    private String eventOfTeamName;
    private String teamName;
    private int maxParticipants;
    private LocalDate startJoinDate;
    private LocalDate closingJoinDate;
    private ActivityList teamActivities;
    private UserList teamMembers;
    private ArrayList<String> teamChat;

    public Team(String eventOfTeamName, String teamName, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate, ActivityList teamActivities, UserList teamMembers, ArrayList<String> teamChat) {
        this.eventOfTeamName = eventOfTeamName;
        this.teamName = teamName;
        this.maxParticipants = maxParticipants;
        this.startJoinDate = startJoinDate;
        this.closingJoinDate = closingJoinDate;
        this.teamActivities = teamActivities;
        this.teamMembers = teamMembers;
        this.teamChat = teamChat;
    }

    public String getEventOfTeamName() {
        return eventOfTeamName;
    }
    public String getTeamName() {
        return teamName;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public LocalDate getStartJoinDate() {
        return startJoinDate;
    }

    public LocalDate getClosingJoinDate() {
        return closingJoinDate;
    }

    public ActivityList getTeamActivities() {
        return teamActivities;
    }
    public UserList getTeamMembers() {
        return teamMembers;
    }
    public String getHeadOfTeamUsername() {
        return headOfTeamUsername;
    }

    public ArrayList<String> getTeamChat() {
        return teamChat;
    }

    public void setHeadOfTeamUsername(String headOfTeamUsername) {
        this.headOfTeamUsername = headOfTeamUsername;
    }
}
