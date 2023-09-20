package cs211.project.controllers;

import cs211.project.models.Team;
import cs211.project.models.TeamList;
import cs211.project.models.TeamParticipantList;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CreateTeamController {
    @FXML DatePicker startDateDatePicker;
    @FXML  DatePicker endDateDatePicker;
    @FXML TextField teamNameTextField;
    @FXML TextField numPeopleTextField;
    private Stage createTeamStage;
    private TeamList teamList;
    private Datasource<TeamList> datasource;
    private String eventName;
    private String currentUsername;

    @FXML
    public void initialize() {
        datasource = new TeamListFileDatasource("data", "team_list.csv");
        teamList = datasource.readData();
    }

    @FXML
    public void handleCreateTeamButton() {
        if (teamNameTextField.getText().isEmpty() || numPeopleTextField.getText().isEmpty() || startDateDatePicker.getValue() == null || endDateDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill all the information");
            alert.showAndWait();
            return;
        }
        try {
            boolean canAddNewTeam = teamList.addNewTeam(eventName, teamNameTextField.getText(), Integer.parseInt(numPeopleTextField.getText()), startDateDatePicker.getValue(), endDateDatePicker.getValue(), currentUsername, currentUsername);
            if (!canAddNewTeam) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("This Team is already exist");
                alert.showAndWait();
                return;
            }
            datasource.writeData(teamList);
            Datasource<TeamParticipantList> datasourceParticipant = new TeamParticipantListFileDataSource("data", "team_participant_list.csv");
            TeamParticipantList teamParticipantList = datasourceParticipant.readData();
            teamParticipantList.addNewTeamParticipant(currentUsername, eventName, teamNameTextField.getText());
            datasourceParticipant.writeData(teamParticipantList);
            if (createTeamStage != null) {
                createTeamStage.close();
            }
            FXRouterPane.goTo("event-team-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setCreateTeamStage(Stage createTeamStage) {
        this.createTeamStage = createTeamStage;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }
}
