package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.models.TeamList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CreateEventController {
    @FXML private TextField eventNameTextField;
    @FXML private TextField eventInfoTextField;
    @FXML private TextField placeTextField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Label errorLabel;
    @FXML Button upLoadImageButton;

    private Datasource<EventList> datasource;
    private Event event;
    private EventList eventList;

    @FXML
    public void initialize() {
        datasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = datasource.readData();
    }

    @FXML
    public void createEventButton() throws IOException {

    }
}
