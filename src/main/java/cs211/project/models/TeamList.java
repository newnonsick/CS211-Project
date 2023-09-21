package cs211.project.models;

import java.time.LocalDate;
import java.util.ArrayList;


public class TeamList {
    private ArrayList<Team> teams;

    public TeamList() {
        teams = new ArrayList<>();
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }
    public boolean addNewTeam(String eventUUID, String teamName, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate, String teamOwnerUsername,String headOfTeamUsername) {
        eventUUID = eventUUID.trim();
        teamName = teamName.trim();
        if (!eventUUID.equals("") && !teamName.equals("") && maxParticipants > 0 && startJoinDate != null && closingJoinDate != null && !headOfTeamUsername.equals("")) {
            Team exist = findEventByEventUUIDAndTeamName(eventUUID, teamName);
            if (exist == null) {
                teams.add(new Team(eventUUID, teamName, maxParticipants, startJoinDate, closingJoinDate, teamOwnerUsername,headOfTeamUsername));
                return true;
            }
            return false;
        }
        return false;
    }

//    public Team findTeamByNameAndEvent(String eventOfTeamName, String teamName) {
//        for (Team team : teams) {
//            if (team.getTeamName().equals(teamName) && team.getEventOfTeamName().equals(eventOfTeamName)) {
//                return team;
//            }
//        }
//        return null;
//    }

    public Team findEventByEventUUIDAndTeamName(String eventUUID, String teamName){
        for (Team team : this.teams) {
            if (eventUUID.equals(team.getEventUUID()) && teamName.equals(team.getTeamName())) {
                return team;
            }
        }
        return null;
    }

    public Team findTeamByObject(Team team) {
        for (Team team1 : teams) {
            if (team1.equals(team)) {
                return team1;
            }
        }
        return null;
    }
}
