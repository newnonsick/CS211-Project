package cs211.project.models;

import java.util.ArrayList;

public class TeamChatList {
    ArrayList<TeamChat> teamChats;

    public TeamChatList() {
        teamChats = new ArrayList<>();
    }

    public void addNewChat(String eventName, String teamName, String username, String message) {
        teamChats.add(new TeamChat(eventName, teamName, username, message));
    }

    public ArrayList<TeamChat> getTeamChats() {
        return teamChats;
    }

    public ArrayList<String> getChats(String eventName, String teamName) {
        ArrayList<String> chats = new ArrayList<>();
        for (TeamChat teamChat : teamChats) {
            if (teamChat.getEventName().equals(eventName) && teamChat.getTeamName().equals(teamName)) {
                chats.add(teamChat.getUsername() + " : " + teamChat.getMessage());
            }
        }
        return chats;
    }
}
