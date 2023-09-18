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
    private Label errorLabel;

    private Datasource<EventList> datasource;

    private Event event;
    private EventList eventList;
    private String[] eventCategories = {"งานแสดงสินค้า", "เทศกาล", "อบรมสัมนา", "บ้านและของแต่งบ้าน"
            , "อาหารและเครื่องดื่ม", "บันเทิง", "คอนเสิร์ต/แฟนมีตติ้ง", "ท่องเที่ยว", "ศิลปะ/นิทรรศการ/ถ่ายภาพ", "กีฬา"
            , "ศาสนา", "สัตว์เลี้ยง", "ธุรกิจ/อาชีพ/การศึกษา", "อื่น ๆ"};

    private String eventName;
    private String[] componentData;
    private String currentUsername;

    @FXML
    public void initialize() {
        datasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = datasource.readData();
        componentData = (String[]) FXRouterPane.getData();
        eventName = componentData[0];
        currentUsername = componentData[1];
        event = eventList.findEventByEventName(eventName);
        eventChoiceBox.getItems().addAll(eventCategories);
        errorLabel.setVisible(false);
        showInformation();
    }
    public void showInformation(){
        eventNameTextField.setText(event.getEventName());
        eventInfoTextField.setText(event.getEventInformation());
        placeTextField.setText(event.getPlaceEvent());
        startDatePicker.setValue(event.getEventStartDate());
        endDatePicker.setValue(event.getEventEndDate());
        eventChoiceBox.setValue(event.getEventCategory());
        if (event.getMaxParticipants() != -1) {
            maxParticipantTextField.setText(String.valueOf(event.getMaxParticipants()));
        } else {
            maxParticipantTextField.setText("");
        }startJoinDatePicker.setValue(event.getStartJoinDate());
        closingJoinDatePicker.setValue(event.getClosingJoinDate());

        Image image = new Image("file:data/eventPicture/" + event.getEventPicture());
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
            event.setEventPicture(selectedImage.getPath());

            eventList.updateEvent(event);
            datasource.writeData(eventList);
        }
    }

    @FXML
    public void saveEventEditButton() {
        LocalDate startJoin = startJoinDatePicker.getValue();
        LocalDate closingJoin = closingJoinDatePicker.getValue();


        if (closingJoin != null && startJoin != null && closingJoin.isBefore(startJoin)) {
            errorLabel.setText("Start date must be before closing date.");
            errorLabel.setVisible(true);
            return;
        } else {
            errorLabel.setVisible(false);
        }

        event.setEventName(eventNameTextField.getText());
        event.setEventInformation(eventInfoTextField.getText());
        event.setEventCategory(eventChoiceBox.getValue());
        event.setPlaceEvent(placeTextField.getText());
        event.setEventStartDate(startDatePicker.getValue());
        event.setEventEndDate(endDatePicker.getValue());
        event.setMaxParticipant(Integer.parseInt(maxParticipantTextField.getText()));
        event.setStartJoinDate(startJoinDatePicker.getValue());
        event.setClosingJoinDate(closingJoinDatePicker.getValue());

        event.setStartJoinDate(startJoin);
        event.setClosingJoinDate(closingJoin);

        eventList.updateEvent(event);
        datasource.writeData(eventList);
        backToYourCreatedEvents();
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
                FXRouterPane.goTo("event-team-management", new String[] {event.getEventName(), currentUsername});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


