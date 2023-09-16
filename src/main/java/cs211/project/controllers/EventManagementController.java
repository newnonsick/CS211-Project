package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import cs211.project.services.FXRouterPane;

import java.io.IOException;

public class EventManagementController {
    @FXML
    private TextField eventNameTextField;
    @FXML
    private TextField eventInformationTextField;
    @FXML
    private ChoiceBox<String> eventCategoryChoiceBox;  //havn't do it
    @FXML
    private TextField placeEventTextField;
    @ FXML
    private DatePicker editStartDatePicker;
    @FXML
    private DatePicker editEndDatePicker;
    @FXML
    private TextField numberOfParticipantTextField;
    @FXML
    private DatePicker startJoinDatePicker;
    @FXML
    private DatePicker closingJoinDatePicker;

    private EventList eventList;
    private Event event;
    private Datasource<EventList> datasource;

    @FXML
    public void initialize() {
        datasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = datasource.readData();

        String eventName = (String) FXRouterPane.getData();
        event = eventList.findEventByEventName(eventName);
        showEvent(event);
    }

    private void showEvent(Event event) {
        // show event information
    }

    public void setEditable(boolean editable) {
        eventNameTextField.setEditable(editable);
        eventInformationTextField.setEditable(editable);
        placeEventTextField.setEditable(editable);
        editStartDatePicker.setEditable(editable);
        editEndDatePicker.setEditable(editable);
        numberOfParticipantTextField.setEditable(editable);
        startJoinDatePicker.setEditable(editable);
        closingJoinDatePicker.setEditable(editable);
    }

    @FXML
    private void onEditButtonClick() {
        setEditable(true); //เปลี่ยนเป็นค่า true เพื่อใหแก้ไขข้อมูลได้
    }
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
            FXRouterPane.goTo("my-events");
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

