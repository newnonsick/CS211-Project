package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class TeamElementController {
    @FXML
    Label eventNameLabel;
    @FXML
    Label teamNameLabel;
    @FXML
    Label maxParticipantsLabel;
    @FXML
    Label remaindaysLabel;
    @FXML
    AnchorPane selectTeamAnchorPane;
    private boolean isOwner;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private Event event;

    public void initialize() {
        eventListDatasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = eventListDatasource.readData();
    }

    public void setPage(String eventUUID, String teamName, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate, boolean isOwner) {
        event = eventList.findEventByUUID(eventUUID);
        eventNameLabel.setText(event.getName());
        teamNameLabel.setText(teamName);
        maxParticipantsLabel.setText(String.valueOf(maxParticipants));
        remaindaysLabel.setText(startJoinDate.toString() + " - " + closingJoinDate.toString());
    }



}
