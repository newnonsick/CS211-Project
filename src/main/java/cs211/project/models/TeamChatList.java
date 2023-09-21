package cs211.project.models;

import java.util.ArrayList;

public class TeamChatList {
    ArrayList<TeamChat> teamChats;

    public TeamChatList() {
        teamChats = new ArrayList<>();
    }

    public void addNewChat(String eventUUID, String teamName, String username, String message) {
        teamChats.add(new TeamChat(eventUUID, teamName, username, message));
    }

    public ArrayList<TeamChat> getTeamChats() {
        return teamChats;
    }

}
