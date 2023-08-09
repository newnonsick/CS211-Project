package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import cs211.project.services.FXRouterPane;

import java.io.IOException;

public class EventParticipantManagementController {

    @FXML private TableView eventParticipantTableView;


    @FXML
    public void handleBackToEventManagementBotton() {
        try {
            FXRouterPane.goTo("event-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
