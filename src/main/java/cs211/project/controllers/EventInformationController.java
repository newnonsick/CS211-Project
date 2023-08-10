package cs211.project.controllers;

import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import java.io.IOException;

public class EventInformationController {
    @FXML private Label eventNameLabel;
    @FXML private ImageView eventImageView;
    @FXML private Label eventInfoLabel;
    @FXML private Label placeLabel;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;
    @FXML private Label maxParticipantsLabel;
    @FXML private Label closingJoinDateLabel;

    @FXML
    public void handleBackToEventPage() {
        // ไปหน้ารวมอีเวนต์
        try {
            FXRouterPane.goTo("event-list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void joinEventButton() {
        // เข้าร่วมอีเวนต์
    }

    @FXML
    public void handleVisitTeamButton(){
        try {
            FXRouterPane.goTo("team-list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
