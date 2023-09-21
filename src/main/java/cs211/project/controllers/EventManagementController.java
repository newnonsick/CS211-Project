package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.*;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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

    private String eventUUID;
    private String[] componentData;
    private String currentUsername;

    @FXML
    public void initialize() {
        datasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = datasource.readData();
        componentData = (String[]) FXRouterPane.getData();
        eventUUID = componentData[0];
        currentUsername = componentData[1];
        event = eventList.findEventByUUID(eventUUID);
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
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg")
        );
        File selectedImage = fileChooser.showOpenDialog(null);
        if (selectedImage != null) {
            Image image = new Image(selectedImage.toURI().toString());
            eventImageView.setImage(image);

            String targetDirectoryPath = "data/eventPicture";
            Path targetDirectory = Path.of(targetDirectoryPath);
            String fileType = selectedImage.getName().substring(selectedImage.getName().lastIndexOf(".") + 1).toLowerCase();


            if (!fileType.equals("jpeg") && !fileType.equals("jpg") && !fileType.equals("png")) {
                fileType = "jpeg";
            }

            Path targetFilePath = targetDirectory.resolve(event.getEventName() + "." + fileType);
            try {
                Files.copy(selectedImage.toPath(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            event.setEventPicture(event.getEventName() + "." + fileType);

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
        String newEventName = eventNameTextField.getText();

        if (!event.getEventName().equals(newEventName)) {
            String oldImageFileName = event.getEventPicture();
            String fileType = oldImageFileName.substring(oldImageFileName.lastIndexOf(".") + 1);
            File oldImageFile = new File("data/eventPicture/" + oldImageFileName);
            File newImageFile = new File("data/eventPicture/" + newEventName + "." + fileType);
            if (oldImageFile.renameTo(newImageFile)) {
                event.setEventPicture(newEventName + "." + fileType);
            }
        }

        event.setEventName(eventNameTextField.getText());
        event.setEventInformation(eventInfoTextField.getText());
        event.setEventCategory(eventChoiceBox.getValue());
        event.setPlaceEvent(placeTextField.getText());
        event.setEventStartDate(startDatePicker.getValue());
        event.setEventEndDate(endDatePicker.getValue());

        if (eventNameTextField.getText().isEmpty() ||
                eventInfoTextField.getText().isEmpty() ||
                placeTextField.getText().isEmpty() ||
                eventChoiceBox.getValue() == null ||
                startDatePicker.getValue() == null ||
                endDatePicker.getValue() == null ||
                eventImageView.getImage() == null) {

            errorLabel.setText("Please fill in all required fields.");
            errorLabel.setVisible(true);
            return;
        }

        try {
            int maxParticipant = Integer.parseInt(maxParticipantTextField.getText());
            event.setMaxParticipant(maxParticipant);
        } catch (NumberFormatException e) {
            event.setMaxParticipant(-1);
        }

        if (startJoin != null) {
            event.setStartJoinDate(startJoin);
        }
        if (closingJoin != null) {
            event.setClosingJoinDate(closingJoin);
        }



        eventList.updateEvent(event);
        datasource.writeData(eventList);
        backToYourCreatedEvents();
    }


    @FXML
        public void eventPartiManagementButton() {
            try {
                FXRouterPane.goTo("event-participant-management", new String[] {event.getEventUUID(), currentUsername});
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
                FXRouterPane.goTo("event-team-management", new String[] {event.getEventUUID(), currentUsername});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


