package cs211.project.controllers;

import cs211.project.Main;
import cs211.project.models.*;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class MyTeamListController {

    @FXML
    ScrollPane teamListScrollPane;
    @FXML
    GridPane teamListGridPane;
    private User currentUser;
    private Datasource<TeamList> datasourceTeam;
    private Datasource<TeamParticipantList> datasourceParticipant;
    private LocalDate currentDate;
    private TeamList teamList;
    private TeamParticipantList teamParticipantList;

    public void initialize(){
        currentUser = (User) FXRouter.getData();
        ZoneId thaiTimeZone = ZoneId.of("Asia/Bangkok");
        currentDate = LocalDate.now(thaiTimeZone);
        datasourceTeam = new TeamListFileDatasource("data", "team_list.csv");
        teamList = datasourceTeam.readData();
        datasourceParticipant = new TeamParticipantListFileDataSource("data", "team_participant_list.csv");
        teamParticipantList = datasourceParticipant.readData();
        showTeamList();
    }

    public void showTeamList(){
        int row = 0;
        int column = 0;
        for (TeamParticipant teamParticipant : teamParticipantList.getTeamParticipants()) {
            Team team = teamList.findTeamByNameAndEvent(teamParticipant.getEventName(), teamParticipant.getTeamName());
            if (!teamParticipant.getUsername().equals(currentUser.getUsername())) {
                continue;
            }
            if (team.getClosingJoinDate().isBefore(currentDate)) {
                continue;
            }
            if(column == 2) {
                row++;
                column = 0;
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/team-element.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            TeamElementController team_ = fxmlLoader.getController();
            team_.setPage(team.getEventOfTeamName(), team.getTeamName(), team.getMaxParticipants(), team.getStartJoinDate(), team.getClosingJoinDate());
            anchorPane.setOnMouseClicked(event1 -> {
                try {
                    FXRouterPane.goTo("team-communication", new String[] {team.getEventOfTeamName(), team.getTeamName(), currentUser.getUsername()});
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            teamListGridPane.add(anchorPane, column, row);
            column++;

            GridPane.setMargin(anchorPane, new Insets(10));
        }

    }

}
