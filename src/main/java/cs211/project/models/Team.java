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
    private ArrayList<Activity> teamActivities;
    private ArrayList<String> teamMembers;
    private ArrayList<String> teamChat;
    private String teamChatPath;
    private String teamParticipantsPath;
    private String teamActivityPath;

    public Team(String eventOfTeamName, String teamName, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate) {
        this.eventOfTeamName = eventOfTeamName;
        this.teamName = teamName;
        this.maxParticipants = maxParticipants;
        this.startJoinDate = startJoinDate;
        this.closingJoinDate = closingJoinDate;
        this.teamActivities = new ArrayList<>();
        this.teamMembers = new ArrayList<>();
        this.teamChat = new ArrayList<>();
        this.teamChatPath = "data" + File.separator + eventOfTeamName + File.separator + teamName + File.separator + "teamChat.csv";
        this.teamParticipantsPath = "data" + File.separator + eventOfTeamName + File.separator + teamName + File.separator + "teamParticipants.csv";
        this.teamActivityPath = "data" + File.separator + eventOfTeamName + File.separator + teamName + File.separator + "teamActivity.csv";
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

    public ArrayList<Activity> getTeamActivities() {
        return teamActivities;
    }
    public ArrayList<String> getTeamMembers() {
        return teamMembers;
    }
    public String getHeadOfTeamName() {
        return headOfTeamName;
    }

    public String getTeamChatPath() {
        return teamChatPath;
    }

    public String getTeamParticipantsPath() {
        return teamParticipantsPath;
    }
    public String getTeamActivityPath() {
        return teamActivityPath;
    }

    public ArrayList<String> getTeamChat() {
        return teamChat;
    }

    public void setHeadOfTeamName(String headOfTeamName) {
        this.headOfTeamName = headOfTeamName;
    }
}
