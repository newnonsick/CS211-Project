package cs211.project.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import cs211.project.services.FXRouterPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EventParticipantManagementController {


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
}
