package cs211.project.controllers;

import cs211.project.models.Activity;
import cs211.project.models.ActivityList;
import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventParticipantManagementController {

    @FXML private TableView eventParticipantTableView;
    @FXML private TableView activityParticipantTableView;
    @FXML private Button goToEditParticipant;
    @FXML private Button removeActivityButton;
    @FXML private Button startTimePicker;
    @FXML private Button endTimePicker;
    @FXML private Label eventNameLabel;
    @FXML private Label eventName2Label;
    @FXML private TextField activityNameTextField;
    @FXML private TextField activityInfoTextField;
    @FXML private DatePicker activityDatePicker;
    private LocalTime selectedStartTime;
    private LocalTime selectedEndTime;
    private ActivityList activityList;
    private Datasource<ActivityList> activityListDatasource;
    private Activity selectedActivity;
    private EventList eventList;
    private Event event;
    private Datasource<EventList> eventListDatasource;
    private String[] componentData;
    private String currentUsername;
    private String eventOfParticipantUUID;
    private Datasource<List<String[]>> participantDataSource;
    private List<String[]> participantList;


    public void initialize(){
        removeActivityButton.setDisable(true);
        componentData = (String[]) FXRouterPane.getData();
        eventOfParticipantUUID = componentData[0];
        currentUsername = componentData[1];
        eventListDatasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = eventListDatasource.readData();
        event = eventList.findEventByUUID(eventOfParticipantUUID);
        eventNameLabel.setText(event.getEventName());
        eventName2Label.setText(event.getEventName());
        activityListDatasource = new ParticipantActivityListFileDatasource("data", "participant_activity_list.csv");
        activityList = activityListDatasource.readData();
        participantDataSource = new JoinEventFileDataSource("data", "joinEventData.csv");
        participantList = participantDataSource.readData();
        showParticipants(participantList);
        showActivity(activityList);
        activityParticipantTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Activity>() {
            @Override
            public void changed(ObservableValue observable, Activity oldValue, Activity newValue) {
                if (newValue != null) {
                    removeActivityButton.setDisable(false);
                    selectedActivity = newValue;
                }
                else {
                    removeActivityButton.setDisable(true);
                }
            }
        });
}


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
    }

    @FXML
    public void handleStartTimePickerButton() {
        selectedStartTime = showCustomTimePickerDialog();
        if (selectedStartTime != null) {
            System.out.println("Select Start Time: " + selectedStartTime);
            startTimePicker.setText(selectedStartTime.toString());
        }
    }

    @FXML
    public void handleEndTimePickerButton() {
        selectedEndTime = showCustomTimePickerDialog();
        if (selectedEndTime != null) {
            System.out.println("Select End Time: " + selectedEndTime);
            endTimePicker.setText(selectedEndTime.toString());
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


    public void handleAddActivityPartiButton() {
        String activityName = activityNameTextField.getText();
        String activityInfo = activityInfoTextField.getText();
        LocalTime startTime = selectedStartTime;
        LocalTime endTime = selectedEndTime;
        LocalDate activityDate = activityDatePicker.getValue();

        if (!activityName.isEmpty() || !activityInfo.isEmpty() || !(startTime == null) || !(endTime == null) || !(activityDate == null)) {
            activityList.addNewActivityParticipant(eventOfParticipantUUID, activityName, activityInfo, startTime, endTime, activityDate);
            activityListDatasource.writeData(activityList);
            activityNameTextField.clear();
            activityInfoTextField.clear();
            selectedStartTime = null;
            selectedEndTime = null;
            activityDatePicker.setValue(null);
            startTimePicker.setText("Start Time");
            endTimePicker.setText("End Time");
            showActivity(activityList);
        }
    }

    public void handleRemoveActivityPartiButton(){
        if (selectedActivity == null) {
            return;
        }
        activityList.getActivities().remove(selectedActivity);
        activityListDatasource.writeData(activityList);
        showActivity(activityList);
        selectedActivity = null;
        removeActivityButton.setDisable(true);
    }
    private void removeParticipant(String[] participantData) {
        // Remove from the TableView
        eventParticipantTableView.getItems().remove(participantData);

        // Remove from the participantList
        participantList.remove(participantData);

        // Update the CSV file
        participantDataSource.writeData(participantList);
    }

    public void showParticipants(List<String[]> participantList){
        TableColumn<String[], String> partiUsernameColumn = new TableColumn<>("รายชื่อผู้เข้าร่วม");
        partiUsernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        ObservableList<String[]> filteredParticipants = FXCollections.observableArrayList();
        for (String[] participantData : participantList) {
            if (participantData[1].equals(eventOfParticipantUUID)) {
                filteredParticipants.add(participantData);
            }
        }
        TableColumn<String[], String> removeButtonColumn = new TableColumn<>("Remove");
        removeButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button removeButton = new Button("Remove");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    removeButton.setOnAction(event -> {
                        String[] participantData = getTableView().getItems().get(getIndex());
                        removeParticipant(participantData);
                    });
                    setGraphic(removeButton);
                }
            }
        });

        eventParticipantTableView.setItems(filteredParticipants);
        eventParticipantTableView.getColumns().clear();
        eventParticipantTableView.getColumns().add(partiUsernameColumn);
        eventParticipantTableView.getColumns().add(removeButtonColumn);


        partiUsernameColumn.prefWidthProperty().bind(eventParticipantTableView.widthProperty().multiply(0.8));

    }
    public void showActivity(ActivityList activityList){

        TableColumn<Activity, String> activityNameColumn = new TableColumn<>("ชื่อกิจกรรม");
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));

        TableColumn<Activity, String> activityInfoColumn = new TableColumn<>("รายละเอียดกิจกรรม");
        activityInfoColumn.setCellValueFactory(new PropertyValueFactory<>("activityInformation"));

        TableColumn<Activity, LocalTime> activityStartColumn = new TableColumn<>("เวลาเริ่ม");
        activityStartColumn.setCellValueFactory(new PropertyValueFactory<>("activityStartTime"));

        TableColumn<Activity, LocalTime> activityEndColumn = new TableColumn<>("เวลาสิ้นสุด");
        activityEndColumn.setCellValueFactory(new PropertyValueFactory<>("activityEndTime"));

        TableColumn<Activity, String> activityDateColumn =  new TableColumn<>("วันที่");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy"); // Replace 'your-pattern-here' with the desired pattern
        activityDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getActivityDate().format(formatter))
        );
        ObservableList<Activity> observableList = FXCollections.observableArrayList(activityList.getActivities());
        activityParticipantTableView.setItems(observableList);

        activityParticipantTableView.getColumns().clear();

        activityParticipantTableView.getColumns().add(activityDateColumn);
        activityParticipantTableView.getColumns().add(activityStartColumn);
        activityParticipantTableView.getColumns().add(activityEndColumn);
        activityParticipantTableView.getColumns().add(activityNameColumn);
        activityParticipantTableView.getColumns().add(activityInfoColumn);
        activityParticipantTableView.getItems().clear();

        activityDateColumn.prefWidthProperty().bind(activityParticipantTableView.widthProperty().multiply(0.17));
        activityStartColumn.prefWidthProperty().bind(activityParticipantTableView.widthProperty().multiply(0.1));
        activityEndColumn.prefWidthProperty().bind(activityParticipantTableView.widthProperty().multiply(0.1));
        activityNameColumn.prefWidthProperty().bind(activityParticipantTableView.widthProperty().multiply(0.23));
        activityInfoColumn.prefWidthProperty().bind(activityParticipantTableView.widthProperty().multiply(0.40));

        for (Activity activity : activityList.getActivities()) {
            if (activity.getEventOfActivityUUID().equals(event.getEventUUID())) {
                activityParticipantTableView.getItems().add(activity);
            }
        }
    }
}
