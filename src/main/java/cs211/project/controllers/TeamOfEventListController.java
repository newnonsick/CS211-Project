package cs211.project.controllers;

import cs211.project.models.Team;
import cs211.project.models.TeamList;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouterPane;
import cs211.project.services.TeamListFileDatasource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class TeamOfEventListController {
    @FXML Label teamNameLabel;
    @FXML Label numPeopleLabel;
    @FXML Label startDateLabel;
    @FXML Label endDateLabel;
    @FXML TableView teamListTableView;

    private Datasource<TeamList> datasource;
    private TeamList teamList;
    private String eventName;

    @FXML
    public void initialize(){
        eventName = (String) FXRouterPane.getData();
        datasource = new TeamListFileDatasource("data", "team_list.csv");
        teamList = datasource.readData();
        showTeam(teamList);
    }

    public void handleJoinTeamButton(){
        ;
    }

    private void showTeam(TeamList teamList){
        TableColumn<Team, String> eventNameColum = new TableColumn<>("Event Name");
        eventNameColum.setCellValueFactory(new PropertyValueFactory<>("eventOfTeamName"));

        TableColumn<Team, String> teamNameColum = new TableColumn<>("Team Name");
        teamNameColum.setCellValueFactory(new PropertyValueFactory<>("teamName"));

        TableColumn<Team, Integer> maxParticipantsColumn = new TableColumn<>("Max Participants");
        maxParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("maxParticipants"));

        TableColumn<Team, LocalDate> startJoinDateColumn = new TableColumn<>("Start Join Date");
        startJoinDateColumn.setCellValueFactory(new PropertyValueFactory<>("startJoinDate"));

        TableColumn<Team, LocalDate> closingJoinDateColumn = new TableColumn<>("End Join Date");
        closingJoinDateColumn.setCellValueFactory(new PropertyValueFactory<>("closingJoinDate"));


        teamListTableView.getColumns().clear();
        teamListTableView.getColumns().add(eventNameColum);
        teamListTableView.getColumns().add(teamNameColum);
        teamListTableView.getColumns().add(maxParticipantsColumn);
        teamListTableView.getColumns().add(startJoinDateColumn);
        teamListTableView.getColumns().add(closingJoinDateColumn);

        teamListTableView.getItems().clear();

        for (Team team : teamList.getTeams()) {
            if (team.getEventOfTeamName().equals(eventName)) {
                teamListTableView.getItems().add(team);
            }
        }


    }
}
