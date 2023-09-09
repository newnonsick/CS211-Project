package cs211.project.models;

import java.util.ArrayList;

public class TeamParticipantList{
    ArrayList<TeamParticipant> teamParticipants;

    public TeamParticipantList() {
        teamParticipants = new ArrayList<>();
    }


    public void addNewTeamParticipant(String username, String eventName, String teamName) {
        username = username.trim();
        eventName = eventName.trim();
        teamName = teamName.trim();
        if (!username.equals("") && !eventName.equals("") && !teamName.equals("")) {
            TeamParticipant exist = findTeamParticipantByUsernameAndEventAndTeam(username, eventName, teamName);
            if (exist == null) {
                teamParticipants.add(new TeamParticipant(username, eventName, teamName));
            }
        }
    }

    public TeamParticipant findTeamParticipantByUsernameAndEventAndTeam(String username, String eventName, String teamName) {
        for (TeamParticipant teamParticipant : teamParticipants) {
            if (teamParticipant.getUsername().equals(username) && teamParticipant.getTeamName().equals(teamName) && teamParticipant.getEventName().equals(eventName)) {
                return teamParticipant;
            }
        }
        return null;
    }

    public ArrayList<TeamParticipant> getTeamParticipants() {
        return teamParticipants;
    }
}
