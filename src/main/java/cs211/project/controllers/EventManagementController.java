package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import cs211.project.services.FXRouterPane;

import java.io.IOException;

public class EventManagementController {
    @FXML
    private DatePicker editStartDatePicker;
    @FXML
    private DatePicker editEndDatePicker;
    @FXML
    private TextField numberOfParticipantTextField;
    @FXML
    private DatePicker starJoinDatePicker;
    @FXML
    private DatePicker closingJoinDatePicker;


    @FXML
    public void EventPartiManagementButton() {
        try {
            FXRouterPane.goTo("event-participant-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void backToYourCreatedEvents() {
        try {
            FXRouterPane.goTo("your-created-events");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleManageTeamButton(){
        try {
            FXRouterPane.goTo("event-team-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

