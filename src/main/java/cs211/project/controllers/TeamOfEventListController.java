package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.TeamList;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouterPane;
import cs211.project.services.TeamListFileDatasource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class TeamOfEventListController {

    @FXML
    ScrollPane teamScrollPane;
    @FXML
    GridPane teamGridPane;
    @FXML
    Label eventNameLabel;

    private Datasource<TeamList> datasource;
    private TeamList teamList;
    private String eventName;
    private LocalDate currentDate;

    @FXML
    public void initialize(){
        eventName = (String) FXRouterPane.getData();
        eventNameLabel.setText(eventName);
        ZoneId thaiTimeZone = ZoneId.of("Asia/Bangkok");
        currentDate = LocalDate.now(thaiTimeZone);
        datasource = new TeamListFileDatasource("data", "team_list.csv");
        teamList = datasource.readData();
        //showTeam(teamList);
        showTeam();
    }


    public void showTeam(){
        int row = 0;
        int column = 0;
        for (Team team : teamList.getTeams()) {
            if (!team.getEventOfTeamName().equals(eventName)) {
                continue;
            }
            long daysBetween = ChronoUnit.DAYS.between(currentDate, team.getClosingJoinDate());
            if (daysBetween < 0) {
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
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Do you want to join this team?");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.isEmpty()){
                    System.out.println("Alert closed");
                } else if(result.get() == ButtonType.OK){
                    System.out.println("OK!");
                } else if(result.get() == ButtonType.CANCEL){
                    System.out.println("Never!");
                }
            });
            teamGridPane.add(anchorPane, column, row);
            column++;

            GridPane.setMargin(anchorPane, new Insets(10));
        }
    }
}
