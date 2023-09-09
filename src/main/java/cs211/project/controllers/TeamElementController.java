package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.time.LocalDate;

public class TeamElementController {
    @FXML
    Label eventNameLabel;
    @FXML
    Label teamNameLabel;
    @FXML
    Label maxParticipantsLabel;
    @FXML
    Label remaindaysLabel;

    public void setPage(String eventName, String teamName, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate) {
        eventNameLabel.setText(eventName);
        teamNameLabel.setText(teamName);
        maxParticipantsLabel.setText(String.valueOf(maxParticipants));
        remaindaysLabel.setText(startJoinDate.toString() + " - " + closingJoinDate.toString());
    }

    @FXML
    public void mouseEnter() {
        eventNameLabel.setStyle("-fx-background-color: blue;");
    }

    @FXML
    public void mouseExit() {
        eventNameLabel.setStyle("-fx-background-color: transparent;");
    }

}
