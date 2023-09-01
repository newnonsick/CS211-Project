package cs211.project.controllers;

import cs211.project.services.FXRouter;
import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CreateTeamController {
    @FXML DatePicker startDateDatePicker;
    @FXML  DatePicker endDateDatePicker;
    @FXML TextField teamNameTextField;
    @FXML TextField numPeopleTextField;

    @FXML
    public void handleCreateTeamButton() {
        ;
    }
}
