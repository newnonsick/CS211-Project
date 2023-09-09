package cs211.project.controllers;

import cs211.project.models.Team;
import cs211.project.models.TeamList;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.FXRouterPane;
import cs211.project.services.TeamListFileDatasource;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CreateTeamController {
    @FXML DatePicker startDateDatePicker;
    @FXML  DatePicker endDateDatePicker;
    @FXML TextField teamNameTextField;
    @FXML TextField numPeopleTextField;

    private Team team;
    private TeamList teamList;
    private Datasource<TeamList> datasource;
    private String eventName;

    @FXML
    public void initialize() {
        datasource = new TeamListFileDatasource("data", "team_list.csv");
        teamList = datasource.readData();
        eventName = (String) FXRouterPane.getData();
    }

    @FXML
    public void handleCreateTeamButton() {
        if (teamNameTextField.getText().isEmpty() || numPeopleTextField.getText().isEmpty() || startDateDatePicker.getValue() == null || endDateDatePicker.getValue() == null) {
            return;
        }
        try {
            //teamList.addNewTeam(eventName, teamNameTextField.getText(), Integer.parseInt(numPeopleTextField.getText()), startDateDatePicker.getValue(), endDateDatePicker.getValue());
            teamList.addNewTeam("test", teamNameTextField.getText(), Integer.parseInt(numPeopleTextField.getText()), startDateDatePicker.getValue(), endDateDatePicker.getValue(), "null");
            datasource.writeData(teamList);
            FXRouterPane.goTo("event-team-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

    }
}
