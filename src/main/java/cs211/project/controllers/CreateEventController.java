package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.models.TeamList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    @FXML private Label eventImageErrorLabel;
    @FXML private ImageView eventImageView;
    @FXML private ChoiceBox<String> eventChoiceBox;
    @FXML private Button upLoadImageButton;

    private Datasource<EventList> datasource;
    private Event event;
    private EventList eventList;

    @FXML
    public void initialize() {
        datasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = datasource.readData();
    }

    @FXML
    public void uploadImage() {

    }

    @FXML
    public void createEventButton() throws IOException {
        String eventName = eventNameTextField.getText().trim();
        String eventImage = "";
        String eventInfo = eventInfoTextField.getText().trim();
        String place = placeTextField.getText().trim();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        String filePath = "data/eventList.csv";
        File file = new File(filePath);
        FileInputStream fileInputStream = null;

        if(eventName.isEmpty() || eventInfo.isEmpty() || place.isEmpty() || startDate==null || endDate==null) {
            errorLabel.setText("Please fill in the required information.");
            return;
        }

    }
}
