package cs211.project.models;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class Team {
    private String headOfTeamName;
    private String eventOfTeamName;
    private String teamName;
    private int maxParticipants;
    private LocalDate startJoinDate;
    private LocalDate closingJoinDate;
    private ActivityList teamActivities;
    private Users teamMembers;
    private ArrayList<String> teamChat;

    public Team(String eventOfTeamName, String teamName, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate, ActivityList teamActivities, Users teamMembers, ArrayList<String> teamChat) {
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
    public Users getTeamMembers() {
        return teamMembers;
    }
    public String getHeadOfTeamName() {
        return headOfTeamName;
    }

    public ArrayList<String> getTeamChat() {
        return teamChat;
    }

    public void setHeadOfTeamName(String headOfTeamName) {
        this.headOfTeamName = headOfTeamName;
    }
}
