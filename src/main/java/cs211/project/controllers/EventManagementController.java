package cs211.project.controllers;

import cs211.project.models.ActivityList;
import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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
    @FXML
    private Button startTimeEditButton;
    @FXML
    private Button endTimeEditButton;
    @FXML private Button startJoinTimeButton;
    @FXML private Button closeJoinTimeButton;
    private LocalTime editedStartTime;
    private LocalTime editedEndTime;
    private LocalTime editedStartJoinTime;
    private LocalTime editedCloseJoinTime;
    private LocalTime oldStartJoinTime;
    private LocalTime oldCloseJoinTime;


    private Datasource<EventList> eventListDatasource;
    private JoinEventFileDataSource joinEventDatasource;
    private ParticipantActivityListFileDatasource participantActivityListDatasource;



    private Event event;
    private EventList eventList;
    private String[] eventCategories = {"งานแสดงสินค้า", "เทศกาล", "อบรมสัมนา", "บ้านและของแต่งบ้าน"
            , "อาหารและเครื่องดื่ม", "บันเทิง", "คอนเสิร์ต/แฟนมีตติ้ง", "ท่องเที่ยว", "ศิลปะ/นิทรรศการ/ถ่ายภาพ", "กีฬา"
            , "ศาสนา", "สัตว์เลี้ยง", "ธุรกิจ/อาชีพ/การศึกษา", "อื่น ๆ"};

    private String eventUUID;
    private String[] componentData;
    private String currentUsername;
    private String oldEventName;
    private LocalTime oldStartTime;
    private LocalTime oldEndTime;
    private ActivityList activityList;

    @FXML
    public void initialize() {
        eventListDatasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = eventListDatasource.readData();
        componentData = (String[]) FXRouterPane.getData();
        eventUUID = componentData[0];
        currentUsername = componentData[1];
        event = eventList.findEventByUUID(eventUUID);
        eventChoiceBox.getItems().addAll(eventCategories);
        errorLabel.setVisible(false);
        joinEventDatasource = new JoinEventFileDataSource("data", "joinEventData.csv");
        participantActivityListDatasource = new ParticipantActivityListFileDatasource("data", "participant_activity_list.csv");
        activityList = participantActivityListDatasource.readData();

        showInformation();
    }
    public void showInformation(){
        eventNameTextField.setText(event.getName());
        eventInfoTextField.setText(event.getInfo());
        placeTextField.setText(event.getPlace());
        startDatePicker.setValue(event.getStartDate());
        endDatePicker.setValue(event.getEndDate());
        eventChoiceBox.setValue(event.getCategory());
        if (event.getMaxParticipants() != -1) {
            maxParticipantTextField.setText(String.valueOf(event.getMaxParticipants()));
        } else {
            maxParticipantTextField.setText("");
        }
        startJoinDatePicker.setValue(event.getStartJoinDate());
        closingJoinDatePicker.setValue(event.getCloseJoinDate());
        editedStartTime = event.getStartTime();
        editedEndTime = event.getEndTime();
        oldStartTime = event.getStartTime();
        oldEndTime = event.getEndTime();
        if (oldStartTime != null) {
            startTimeEditButton.setText(oldStartTime.toString());
        }
        if (oldEndTime != null) {
            endTimeEditButton.setText(oldEndTime.toString());
        }
        editedStartJoinTime = event.getStartJoinTime();
        editedCloseJoinTime = event.getCloseJoinTime();
        oldStartJoinTime = event.getStartJoinTime();
        oldCloseJoinTime = event.getCloseJoinTime();
        if (oldStartJoinTime != null) {
            startJoinTimeButton.setText(oldStartJoinTime.toString());
        }
        if (oldCloseJoinTime != null) {
            closeJoinTimeButton.setText(oldCloseJoinTime.toString());
        }
        oldEventName = event.getName();
        Image image = new Image("file:data/eventPicture/" + event.getPicture());
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

            Path targetFilePath = targetDirectory.resolve(event.getName() + "." + fileType);
            try {
                Files.copy(selectedImage.toPath(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            event.setPicture(event.getName() + "." + fileType);
            eventListDatasource.writeData(eventList);
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
        if ((startJoin == null && closingJoin != null) || (startJoin != null && closingJoin == null)) {
            errorLabel.setText("You must fill in both application\nopening day and application closing day.");
            errorLabel.setVisible(true);
            return;
        }
        if ((editedStartJoinTime != null && startJoin == null) || (editedCloseJoinTime != null && closingJoin == null)) {
            errorLabel.setText("application opening day and application closing day\nmust be provided to set times.");
            errorLabel.setVisible(true);
            return;
        }

        String newEventName = eventNameTextField.getText();
        event.setName(newEventName);
        event.setInfo(eventInfoTextField.getText());
        event.setCategory(eventChoiceBox.getValue());
        event.setPlace(placeTextField.getText());
        event.setStartDate(startDatePicker.getValue());
        event.setEndDate(endDatePicker.getValue());
        if (editedStartTime != null) {
            event.setStartTime(editedStartTime);
        }
        if (editedEndTime != null) {
            event.setEndTime(editedEndTime);
        }

        if (!oldEventName.equals(newEventName)) {
            String oldImageFileName = event.getPicture();
            String fileType = oldImageFileName.substring(oldImageFileName.lastIndexOf(".") + 1);
            File oldImageFile = new File("data/eventPicture/" + oldImageFileName);
            File newImageFile = new File("data/eventPicture/" + newEventName + "." + fileType);
            if (oldImageFile.renameTo(newImageFile)) {
                event.setPicture(newEventName + "." + fileType);
            }
        }

        if (eventNameTextField.getText().isEmpty() ||
                eventInfoTextField.getText().isEmpty() ||
                placeTextField.getText().isEmpty() ||
                eventChoiceBox.getValue() == null ||
                startDatePicker.getValue() == null ||
                endDatePicker.getValue() == null ||
                editedStartTime == null ||
                editedEndTime == null
        ) {
            errorLabel.setText("Please fill in all required fields.");
            errorLabel.setVisible(true);
            return;
        }


        String maxParticipantsText = maxParticipantTextField.getText();
        int currentParticipants = joinEventDatasource.countParticipantsForEvent(event.getEventUUID());

        if (maxParticipantsText.isEmpty()) {
            event.setMaxParticipant(-1);
        } else if (!maxParticipantsText.matches("\\d+")) {
            errorLabel.setText("Max participants must be a non-negative integer.");
            errorLabel.setVisible(true);
            return;
        } else {
            int newMaxParticipants = Integer.parseInt(maxParticipantsText);
            if (newMaxParticipants < currentParticipants) {
                errorLabel.setText("You cannot set max participants less than\nthe number of participants that have already joined the event.");
                errorLabel.setVisible(true);
                return;
            }
            event.setMaxParticipant(newMaxParticipants);
        }

        if (startJoin != null) {
            event.setStartJoinDate(startJoin);
        }
        if (closingJoin != null) {
            event.setCloseJoinDate(closingJoin);
        }
        if (editedStartJoinTime != null) {
            event.setStartJoinTime(editedStartJoinTime);
        }
        if (editedCloseJoinTime != null) {
            event.setCloseJoinTime(editedCloseJoinTime);
        }
        eventListDatasource.writeData(eventList);

        backToYourCreatedEvents();
    }
    @FXML
    public void handleStartTimeButton() {
        LocalTime newStartTime = showCustomTimePickerDialog();
        if (newStartTime != null) {
            editedStartTime = newStartTime;
            startTimeEditButton.setText(editedStartTime.toString());
        }
    }

    @FXML
    public void handleEndTimeButton() {
        LocalTime newEndTime = showCustomTimePickerDialog();
        if (newEndTime != null) {
            editedEndTime = newEndTime;
            endTimeEditButton.setText(editedEndTime.toString());
        }
    }
    @FXML
    public void handleStartJoinTimeButton() {
        LocalTime newStartJoinTime = showCustomTimePickerDialog();
        if (newStartJoinTime != null) {
            editedStartJoinTime = newStartJoinTime;
            startJoinTimeButton.setText(editedStartJoinTime.toString());
        }
    }

    @FXML
    public void handleCloseJoinTimeButton() {
        LocalTime newCloseJoinTime = showCustomTimePickerDialog();
        if (newCloseJoinTime != null) {
            editedCloseJoinTime = newCloseJoinTime;
            closeJoinTimeButton.setText(editedCloseJoinTime.toString());
        }
    }


    private LocalTime showCustomTimePickerDialog() {
        Dialog<LocalTime> dialog = new Dialog<>();
        dialog.setTitle("Select Time");

        ComboBox<Integer> hoursBox = new ComboBox<>();
        ComboBox<Integer> minutesBox = new ComboBox<>();
        for (int i = 0; i < 24; i++) {
            hoursBox.getItems().add(i);
        }
        for (int i = 0; i < 60; i+=5) {
            minutesBox.getItems().add(i);
        }

        hoursBox.getSelectionModel().select(LocalTime.now().getHour());
        minutesBox.getSelectionModel().select(LocalTime.now().getMinute());

        HBox timePickerLayout = new HBox(5);
        timePickerLayout.getChildren().addAll(hoursBox, new Label(":"), minutesBox);
        dialog.getDialogPane().setContent(timePickerLayout);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                return LocalTime.of(hoursBox.getValue(), minutesBox.getValue());
            }
            return null;
        });

        Optional<LocalTime> result = dialog.showAndWait();
        return result.orElse(null);
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


