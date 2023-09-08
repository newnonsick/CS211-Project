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

    public Team(String eventOfTeamName, String teamName, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate) {
        this.eventOfTeamName = eventOfTeamName;
        this.teamName = teamName;
        this.maxParticipants = maxParticipants;
        this.startJoinDate = startJoinDate;
        this.closingJoinDate = closingJoinDate;
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

    public String getHeadOfTeamUsername() {
        return headOfTeamUsername;
    }


    public void setHeadOfTeamUsername(String headOfTeamUsername) {
        this.headOfTeamUsername = headOfTeamUsername;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Team) {
            Team team = (Team) obj;
            return team.getTeamName().equals(this.getTeamName()) && team.getEventOfTeamName().equals(this.getEventOfTeamName());
        }
        return false;
    }
}
