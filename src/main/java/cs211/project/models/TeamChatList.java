package cs211.project.models;

import java.util.ArrayList;

public class TeamChatList {
    ArrayList<TeamChat> teamChats;

    public TeamChatList() {
        teamChats = new ArrayList<>();
    }

    public void addNewChat(String eventUUID, String teamName, String username, String message, String activityUUID) {
        teamChats.add(new TeamChat(eventUUID, teamName, username, message, activityUUID));
    }

    public void deleteChatOfActivity(String activityUUID) {
        for (int i = 0; i < this.teamChats.size(); i++) {
            if (this.teamChats.get(i).getActivityUUID().equals(activityUUID)) {
                this.teamChats.remove(i);
                i--;
            }
        }
    }

    public ArrayList<TeamChat> getTeamChats() {
        return teamChats;
    }

}
