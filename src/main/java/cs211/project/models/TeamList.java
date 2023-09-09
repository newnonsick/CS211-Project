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
    public void addNewTeam(String eventOfTeamName, String teamName, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate, String headOfTeamUsername) {
        eventOfTeamName = eventOfTeamName.trim();
        teamName = teamName.trim();
        if (!eventOfTeamName.equals("") && !teamName.equals("") && maxParticipants > 0 && startJoinDate != null && closingJoinDate != null) {
            Team exist = findTeamByNameAndEvent(eventOfTeamName, teamName);
            if (exist == null) {
                teams.add(new Team(eventOfTeamName, teamName, maxParticipants, startJoinDate, closingJoinDate, headOfTeamUsername));
            }
        }
    }

    public Team findTeamByNameAndEvent(String eventOfTeamName, String teamName) {
        for (Team team : teams) {
            if (team.getTeamName().equals(teamName) && team.getEventOfTeamName().equals(eventOfTeamName)) {
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
