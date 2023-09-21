package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
    @FXML
    AnchorPane selectTeamAnchorPane;
    private boolean isOwner;

    public void setPage(String eventName, String teamName, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate, boolean isOwner) {
        eventNameLabel.setText(eventName);
        teamNameLabel.setText(teamName);
        maxParticipantsLabel.setText(String.valueOf(maxParticipants));
        remaindaysLabel.setText(startJoinDate.toString() + " - " + closingJoinDate.toString());
    }



}
