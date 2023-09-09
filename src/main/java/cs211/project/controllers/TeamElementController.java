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
    Label startJoinDateLabel;
    @FXML
    Label closingJoinDateLabel;

    public void setPage(String eventName, String teamName, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate) {
        eventNameLabel.setText(eventName);
        teamNameLabel.setText(teamName);
        maxParticipantsLabel.setText(String.valueOf(maxParticipants));
        startJoinDateLabel.setText("" + startJoinDate);
        closingJoinDateLabel.setText("" + closingJoinDate);
    }

}
