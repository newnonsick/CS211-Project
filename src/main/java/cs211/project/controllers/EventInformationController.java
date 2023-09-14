package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouter;
import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class EventInformationController {
    @FXML private Label eventNameLabel;
    @FXML private ImageView eventImageView;
    @FXML private Label eventInfoLabel;
    @FXML private Label placeLabel;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;
    @FXML private Label maxParticipantsLabel;
    @FXML private Label closingJoinDateLabel;
    @FXML private Label categoryLabel;

    private Datasource<EventList> datasource;
    private EventList eventList;
    private Event event;
    private String eventName;

    @FXML
    public void initialize() {
        datasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = datasource.readData();
        eventName = (String) FXRouterPane.getData();
        event = eventList.findEventByEventName(eventName);
        eventNameLabel.setText(event.getEventName());
        eventInfoLabel.setText(event.getEventInformation());
        placeLabel.setText(event.getPlaceEvent());
        startDateLabel.setText("" + event.getEventStartDate());
        endDateLabel.setText("" + event.getEventEndDate());
        maxParticipantsLabel.setText("" + event.getMaxParticipants());
        closingJoinDateLabel.setText("" + event.getClosingJoinDate());
        categoryLabel.setText(event.getEventCategory());
        String filePath = "data/eventPicture/" + event.getEventPicture();
        File file = new File(filePath);
        eventImageView.setImage(new Image(file.toURI().toString()));
    }

    @FXML
    public void handleBackToEventPage() {
        // ไปหน้ารวมอีเวนต์
        try {
            FXRouterPane.goTo("event-list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void joinEventButton() {
        // เข้าร่วมอีเวนต์
    }

    @FXML
    public void handleVisitTeamButton(){
        try {
            FXRouterPane.goTo("teamofevent-list", eventName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
