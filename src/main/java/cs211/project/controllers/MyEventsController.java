package cs211.project.controllers;

import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.IOException;

public class MyEventsController {
    @FXML private TableView eventsYouCreatedTableView;

    @FXML
    public void goToEventManagement()  {
        try {
            FXRouterPane.goTo("event-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
