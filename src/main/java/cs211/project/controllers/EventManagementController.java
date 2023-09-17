package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class EventManagementController {
    @FXML
    private TextField eventNameTextField;
    @FXML
    private TextField eventInfoTextField;
    @FXML
    private ChoiceBox<String> eventChoiceBox;
    @FXML
    private TextField placeTextField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField maxParticipantTextField;
    @FXML
    private DatePicker startJoinDatePicker;
    @FXML
    private DatePicker closingJoinDatePicker;
    @FXML
    private ImageView eventImageView;
    @FXML
    private Label dateErrorLabel;

    private Datasource<EventList> datasource;
    private Event currentEvent;
    private EventList eventList;
    private String[] eventCategories = {"งานแสดงสินค้า", "เทศกาล", "อบรมสัมนา", "บ้านและของแต่งบ้าน"
            , "อาหารและเครื่องดื่ม", "บันเทิง", "คอนเสิร์ต/แฟนมีตติ้ง", "ท่องเที่ยว", "ศิลปะ/นิทรรศการ/ถ่ายภาพ", "กีฬา"
            , "ศาสนา", "สัตว์เลี้ยง", "ธุรกิจ/อาชีพ/การศึกษา", "อื่น ๆ"};


    @FXML
    public void initialize() {
        datasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = datasource.readData();
        String eventName = (String) FXRouterPane.getData();
        currentEvent = eventList.findEventByEventName(eventName);
        eventChoiceBox.getItems().addAll(eventCategories);

        eventNameTextField.setText(currentEvent.getEventName());
        eventInfoTextField.setText(currentEvent.getEventInformation());
        placeTextField.setText(currentEvent.getPlaceEvent());
        startDatePicker.setValue(currentEvent.getEventStartDate());
        endDatePicker.setValue(currentEvent.getEventEndDate());
        eventChoiceBox.setValue(currentEvent.getEventCategory());
        maxParticipantTextField.setText(String.valueOf(currentEvent.getMaxParticipants()));
        startJoinDatePicker.setValue(currentEvent.getStartJoinDate());
        closingJoinDatePicker.setValue(currentEvent.getClosingJoinDate());

        Image image = new Image("file:data/eventPicture/" + currentEvent.getEventPicture());
        eventImageView.setImage(image);
    }

    @FXML
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedImage = fileChooser.showOpenDialog(null);
        if (selectedImage != null) {
            Image image = new Image(selectedImage.toURI().toString());
            eventImageView.setImage(image);
            currentEvent.setEventPicture(selectedImage.getPath());

            eventList.updateEvent(currentEvent);
            datasource.writeData(eventList);
        }
    }

    @FXML
    public void saveEventEditButton() {
        currentEvent.setEventName(eventNameTextField.getText());
        currentEvent.setEventInformation(eventInfoTextField.getText());
        currentEvent.setEventCategory(eventChoiceBox.getValue());
        currentEvent.setPlaceEvent(placeTextField.getText());
        currentEvent.setEventStartDate(startDatePicker.getValue());
        currentEvent.setEventEndDate(endDatePicker.getValue());

//        currentEvent.setMaxParticipant(Integer.parseInt(maxParticipantTextField.getText()));
//        currentEvent.setStartJoinDate(startJoinDatePicker.getValue());
//        currentEvent.setClosingJoinDate(closingJoinDatePicker.getValue());

        eventList.updateEvent(currentEvent);
        datasource.writeData(eventList);
        backToYourCreatedEvents ();
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
        public void backToYourCreatedEvents () {
            try {
                FXRouterPane.goTo("my-events");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        public void handleManageTeamButton () {
            try {
                FXRouterPane.goTo("event-team-management");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


