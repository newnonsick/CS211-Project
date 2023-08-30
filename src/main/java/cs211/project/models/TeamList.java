package cs211.project.models;

import java.time.LocalDate;
import java.util.ArrayList;


public class TeamList {
    private ArrayList<Team> teams;
    private ArrayList<Event> Events;

    public TeamList() {
        teams = new ArrayList<>();
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }
    public void addNewTeam(String eventOfTeamName, String teamName, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate) {
        //เหลือ read event มาเช็คว่ามี event นี้อยู่จริงไหม
        eventOfTeamName = eventOfTeamName.trim();
        teamName = teamName.trim();
        if (!eventOfTeamName.equals("") && !teamName.equals("") && maxParticipants > 0 && startJoinDate != null && closingJoinDate != null) {
            Team exist = findTeamByNameAndEvent(eventOfTeamName, teamName);
            if (exist == null) {
                teams.add(new Team(eventOfTeamName, teamName, maxParticipants, startJoinDate, closingJoinDate));
            }
        }
    }

    public Team findTeamByNameAndEvent(String eventOfTeamName, String teamName) {
        boolean isEventFound = false;
        for (Event event : Events) {
            if (event.isEvent(eventOfTeamName)) {
                isEventFound = true;
            }
        }
        if (isEventFound) {
            for (Team team : teams) {
                if (team.getTeamName().equals(teamName) && team.getEventOfTeamName().equals(eventOfTeamName)) {
                    return team;
                }
            }
        }
        return null;
    }
}
