package cs211.project.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import cs211.project.services.FXRouterPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EventParticipantManagementController {
    @FXML private TableView eventParticipantTableView;
    @FXML private Button goToEditParticipant;
    @FXML private Label eventName;
    @FXML private TableView activityParticipantTableView;
    @FXML private TextField avtivityNameTextField;
    @FXML private TextField activityPartiDetailsTextField;



    @FXML
    public void handleBackToEventManagementButton() {
        try {
            FXRouterPane.goTo("event-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void goToEditParticipant() {
        try {
            FXRouterPane.goTo("edit-participant");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleEndActivityPartiButton(ActionEvent actionEvent) {
    }

    public void handleAddActivityPartiButton(ActionEvent actionEvent) {
    }

    public void handleRemoveActivityPartiButton(ActionEvent actionEvent) {
    }
}
