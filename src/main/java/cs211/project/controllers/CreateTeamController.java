package cs211.project.controllers;

import cs211.project.models.Team;
import cs211.project.models.TeamList;
import cs211.project.models.TeamParticipantList;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CreateTeamController {
    @FXML DatePicker startDateDatePicker;
    @FXML  DatePicker endDateDatePicker;
    @FXML TextField teamNameTextField;
    @FXML TextField numPeopleTextField;
    @FXML MenuButton startHourMenuButton;
    @FXML MenuButton startMinuteMenuButton;
    @FXML MenuButton endHourMenuButton;
    @FXML MenuButton endMinuteMenuButton;
    private TeamList teamList;
    private Datasource<TeamList> datasource;
    private String eventUUID;
    private String currentUsername;

    @FXML
    public void initialize() {
        datasource = new TeamListFileDatasource("data", "team_list.csv");
        teamList = datasource.readData();
        for (int i = 0; i < 24; i++) {
            MenuItem startHourMenuItem = new MenuItem(String.valueOf(i).length() == 2 ? String.valueOf(i) : "0" + String.valueOf(i));
            startHourMenuItem.setOnAction(event -> {
                startHourMenuButton.setText(startHourMenuItem.getText().length() == 2 ? startHourMenuItem.getText() : "0" + startHourMenuItem.getText());
            });
            startHourMenuButton.getItems().add(startHourMenuItem);
            MenuItem endHourMenuItem = new MenuItem(String.valueOf(i).length() == 2 ? String.valueOf(i) : "0" + String.valueOf(i));
            endHourMenuItem.setOnAction(event -> {
                endHourMenuButton.setText(endHourMenuItem.getText().length() == 2 ? endHourMenuItem.getText() : "0" + endHourMenuItem.getText());
            });
            endHourMenuButton.getItems().add(endHourMenuItem);
        }
        for (int i = 0; i < 60; i++) {
            MenuItem startMinuteMenuItem = new MenuItem(String.valueOf(i).length() == 2 ? String.valueOf(i) : "0" + String.valueOf(i));
            startMinuteMenuItem.setOnAction(event -> {
                startMinuteMenuButton.setText(startMinuteMenuItem.getText().length() == 2 ? startMinuteMenuItem.getText() : "0" + startMinuteMenuItem.getText());
            });
            startMinuteMenuButton.getItems().add(startMinuteMenuItem);
            MenuItem endMinuteMenuItem = new MenuItem(String.valueOf(i).length() == 2 ? String.valueOf(i) : "0" + String.valueOf(i));
            endMinuteMenuItem.setOnAction(event -> {
                endMinuteMenuButton.setText(endMinuteMenuItem.getText().length() == 2 ? endMinuteMenuItem.getText() : "0" + endMinuteMenuItem.getText());
            });
            endMinuteMenuButton.getItems().add(endMinuteMenuItem);
        }
        startHourMenuButton.setText(LocalTime.now().getHour() < 10 ? "0" + LocalTime.now().getHour() : String.valueOf(LocalTime.now().getHour()));
        startMinuteMenuButton.setText(LocalTime.now().getMinute() < 10 ? "0" + LocalTime.now().getMinute() : String.valueOf(LocalTime.now().getMinute()));
    }

    public void createTeam() {
        if (teamNameTextField.getText().isEmpty() || numPeopleTextField.getText().isEmpty() || startDateDatePicker.getValue() == null || endDateDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill all the information");
            alert.showAndWait();
            return;
        }
        try {
            LocalTime startJoinTime = LocalTime.of(Integer.parseInt(startHourMenuButton.getText()), Integer.parseInt(startMinuteMenuButton.getText()));
            LocalTime endJoinTime = LocalTime.of(Integer.parseInt(endHourMenuButton.getText()), Integer.parseInt(endMinuteMenuButton.getText()));
            boolean isExist = teamList.addNewTeam(eventUUID, teamNameTextField.getText(), Integer.parseInt(numPeopleTextField.getText()), startDateDatePicker.getValue(), startJoinTime, endDateDatePicker.getValue(), endJoinTime, currentUsername, currentUsername);
            if (!isExist) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("This Team is already exist");
                alert.showAndWait();
                return ;
            }
            datasource.writeData(teamList);
            Datasource<TeamParticipantList> datasourceParticipant = new TeamParticipantListFileDataSource("data", "team_participant_list.csv");
            TeamParticipantList teamParticipantList = datasourceParticipant.readData();
            teamParticipantList.addNewTeamParticipant(currentUsername, eventUUID, teamNameTextField.getText());
            datasourceParticipant.writeData(teamParticipantList);
            FXRouterPane.goTo("event-team-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a number for number of people");
            alert.showAndWait();
        }
    }

    public void setEventUUID(String eventUUID) {
        this.eventUUID = eventUUID;
    }


    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }
}
