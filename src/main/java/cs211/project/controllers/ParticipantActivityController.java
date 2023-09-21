package cs211.project.controllers;

import cs211.project.models.Activity;
import cs211.project.models.ActivityList;
import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouterPane;
import cs211.project.services.ParticipantActivityListFileDatasource;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ParticipantActivityController {
    @FXML
    Label eventNameLabel;

    @FXML
    TableView<Activity> activityParticipantTableView;

    private Event event;
    EventList eventList;
    private String eventName;
    private String currentUserName;
    private ParticipantActivityListFileDatasource datasource;


    @FXML
    public void initialize() {
        String[] componentData = (String[]) FXRouterPane.getData();
        eventName = componentData[0];
        currentUserName = componentData[1];
        Datasource<EventList> eventListDatasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = eventListDatasource.readData();
        datasource = new ParticipantActivityListFileDatasource("data", "participant_activity_list.csv");
        ActivityList activityList = datasource.readData();
        event = eventList.findEventByEventName(eventName);
        eventNameLabel.setText(eventName);
        showActivities(activityList);
    }


    public void showActivities(ActivityList activityList) {

        TableColumn<Activity, String> activityDateColumn = new TableColumn<>("วันที่");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        activityDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getActivityDate().format(formatter))
        );
        TableColumn<Activity, LocalTime> activityStartColumn = new TableColumn<>("เวลาเริ่ม");
        activityStartColumn.setCellValueFactory(new PropertyValueFactory<>("activityStartTime"));

        TableColumn<Activity, LocalTime> activityEndColumn = new TableColumn<>("เวลาสิ้นสุด");
        activityEndColumn.setCellValueFactory(new PropertyValueFactory<>("activityEndTime"));

        TableColumn<Activity, String> activityNameColumn = new TableColumn<>("ชื่อกิจกรรม");
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));

        TableColumn<Activity, String> activityInfoColumn = new TableColumn<>("รายละเอียดกิจกรรม");
        activityInfoColumn.setCellValueFactory(new PropertyValueFactory<>("activityInformation"));

        //Sort Date and Time
        activityList.getActivities().sort((a1, a2) -> {
            int dateComparison = a1.getActivityDate().compareTo(a2.getActivityDate());
            if (dateComparison != 0) {
                return dateComparison;
            }
            return a1.getActivityStartTime().compareTo(a2.getActivityStartTime());
        });

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
            if (activity.getEventOfActivityName().equals(eventName)) {
                activityParticipantTableView.getItems().add(activity);
            }
        }
    }

    public void backToEventInformation() {
        try {
            FXRouterPane.goTo("event-information");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
