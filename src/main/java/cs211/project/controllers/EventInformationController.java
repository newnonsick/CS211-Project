package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
//        try {
//            FXRouterPane.goTo("");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @FXML
    public void joinEventButton() {
        // เข้าร่วมอีเวนต์
    }
}
