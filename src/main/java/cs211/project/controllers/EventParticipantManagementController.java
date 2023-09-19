package cs211.project.controllers;

import cs211.project.models.Activity;
import cs211.project.models.ActivityList;
import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.ParticipantActivityListFileDatasource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import cs211.project.services.FXRouterPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EventParticipantManagementController {

    @FXML private TableView eventParticipantTableView;
    @FXML private Button goToEditParticipant;
    @FXML private Button removeActivityButton;
    @FXML private Button startTimePicker;
    @FXML private Button endTimePicker;
    @FXML private Label eventName;
    @FXML private Label eventName2;
    @FXML private TableView activityParticipantTableView;
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
    private String eventOfParticipantName;

    public void initialize(){
        removeActivityButton.setDisable(true);
        componentData = (String[]) FXRouterPane.getData();
        eventOfParticipantName = componentData[0];
        currentUsername = componentData[1];
        eventListDatasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = eventListDatasource.readData();
        event = eventList.findEventByEventName(eventOfParticipantName);
        eventName.setText(event.getEventName());
        eventName2.setText(event.getEventName());
        activityListDatasource = new ParticipantActivityListFileDatasource("data", "participant_activity_list.csv");
        activityList = activityListDatasource.readData();
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
        try {
            FXRouterPane.goTo("edit-participant");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        for (int i = 0; i < 60; i++) {
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
            activityList.addNewActivityParticipant(eventOfParticipantName, activityName, activityInfo, startTime, endTime, activityDate);
            activityListDatasource.writeData(activityList);
            activityNameTextField.clear();
            activityInfoTextField.clear();
            selectedStartTime = null;
            selectedEndTime = null;
            activityDatePicker.setValue(null);
            startTimePicker.setText("Select Start Time");
            endTimePicker.setText("Select End Time");
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
        activityParticipantTableView.getColumns().add(activityNameColumn);
        activityParticipantTableView.getColumns().add(activityInfoColumn);
        activityParticipantTableView.getColumns().add(activityStartColumn);
        activityParticipantTableView.getColumns().add(activityEndColumn);
        activityParticipantTableView.getColumns().add(activityDateColumn);
        activityParticipantTableView.getItems().clear();

        for (Activity activity : activityList.getActivities()) {
            if (activity.getEventOfActivityName().equals(eventOfParticipantName)) {
                activityParticipantTableView.getItems().add(activity);
            }
        }
    }
}
