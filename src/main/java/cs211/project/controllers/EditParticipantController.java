package cs211.project.controllers;

import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;

import java.io.IOException;

public class EditParticipantController {

    @FXML
    public void backToEventParticipantManagement(){
        try {
            FXRouterPane.goTo("event-participant-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
