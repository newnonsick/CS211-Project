package cs211.project.controllers;

import cs211.project.models.Activity;
import cs211.project.models.ActivityList;
import cs211.project.services.Datasource;
import cs211.project.services.ParticipantActivityListFileDatasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import cs211.project.services.FXRouterPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.time.LocalTime;

public class EventParticipantManagementController {
    @FXML private TableView eventParticipantTableView;
    @FXML private Button goToEditParticipant;
    @FXML private Label eventName;
    @FXML private Label eventName2;
    @FXML private TableView activityParticipantTableView;
    @FXML private TextField activityNameTextField;
    @FXML private TextField activityPartiDetailsTextField;

    private ActivityList activityList;
    private Datasource<ActivityList> activityListDatasource;

    public void initialize(){
        activityListDatasource = new ParticipantActivityListFileDatasource("data", "participant_activity_list.csv");
        activityList = activityListDatasource.readData();
        showActivity(activityList);
        ObservableList<Activity> observableActivityList = FXCollections.observableList(activityList.getActivities());
        activityParticipantTableView.setItems(observableActivityList);
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


    public void handleAddActivityPartiButton() {
    }

    public void handleRemoveActivityPartiButton() {
    }

    public void showActivity(ActivityList activityList){

        TableColumn<Activity, String> activityNameColumn = new TableColumn<>("Name");
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));

        TableColumn<Activity, String> activityInfoColumn = new TableColumn<>("Information");
        activityInfoColumn.setCellValueFactory(new PropertyValueFactory<>("activityInformation"));

        TableColumn<Activity, LocalTime> activityStartColumn = new TableColumn<>("Start Time");
        activityStartColumn.setCellValueFactory(new PropertyValueFactory<>("activityStartTime"));

        TableColumn<Activity, LocalTime> activityEndColumn = new TableColumn<>("End Time");
        activityEndColumn.setCellValueFactory(new PropertyValueFactory<>("activityEndTime"));

        eventParticipantTableView.getColumns().addAll(activityNameColumn, activityInfoColumn, activityStartColumn, activityEndColumn);

    }


}
