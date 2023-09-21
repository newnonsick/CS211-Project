package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventInformationController {
    @FXML private Label eventNameLabel;
    @FXML private ImageView eventImageView;
    @FXML private Label eventInfoLabel;
    @FXML private Label placeLabel;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;
    @FXML private Label maxParticipantsLabel;
    @FXML private Label startJoinDateLabel;
    @FXML private Label closingJoinDateLabel;
    @FXML private Label categoryLabel;
    @FXML private Label errorLabel;

    private Datasource<EventList> datasource;
    private EventList eventList;
    private Event event;
    private String eventName;
    private String [] componentData;
    private String currentUserName;

    @FXML
    public void initialize() {
        datasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = datasource.readData();
        componentData = (String[]) FXRouterPane.getData();
        eventName = componentData[0];
        currentUserName = componentData[1];
        event = eventList.findEventByEventName(eventName);
        eventNameLabel.setText(event.getEventName());
        eventInfoLabel.setText(event.getEventInformation());
        placeLabel.setText(event.getPlaceEvent());

        String pattern = "MMMM dd, yyyy";
        startDateLabel.setText("" + event.getEventStartDate().format(DateTimeFormatter.ofPattern(pattern)));
        endDateLabel.setText("" + event.getEventEndDate().format(DateTimeFormatter.ofPattern(pattern)));

        if (event.getMaxParticipants() == -1) {
            maxParticipantsLabel.setText("ไม่จำกัดจำนวนผู้เข้าร่วม");
        } else {
            maxParticipantsLabel.setText("" + event.getMaxParticipants());
        }

        if (event.getStartJoinDate() == null) {
            startJoinDateLabel.setText("N/A");
        } else {
            startJoinDateLabel.setText("" + event.getStartJoinDate().format(DateTimeFormatter.ofPattern(pattern)));
        }
        if (event.getClosingJoinDate() == null) {
            closingJoinDateLabel.setText("N/A");
        } else {
            closingJoinDateLabel.setText("" + event.getClosingJoinDate().format(DateTimeFormatter.ofPattern(pattern)));
        }
        categoryLabel.setText(event.getEventCategory());
        String filePath = "data/eventPicture/" + event.getEventPicture();
        File file = new File(filePath);
        eventImageView.setImage(new Image(file.toURI().toString()));

        errorLabel.setText("");
    }

    @FXML
    public void handleBackToEventPage() {
        try {
            FXRouterPane.goTo("event-list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void joinEventButton() {
        if (currentUserName.equals(event.getEventOwnerUsername())){
            errorLabel.setText("You can not join your own event.");
            return;
        }

        String filePath = "data/joinEventData.csv";
        File file = new File(filePath);
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );
        BufferedReader buffer = new BufferedReader(inputStreamReader);
        ArrayList<String> allJoinEventData = new ArrayList<String>();
        String line = "";

        try {
            while ((line = buffer.readLine()) != null) {
                if (line.equals(""))
                    continue;

                String[] data = line.split(",");
                String userName = data[0].trim();
                String eventName = data[1].trim();

                if (event.getEventName().equals(eventName)) {
                    if (currentUserName.equals(userName)) {
                        errorLabel.setText("You have already join this event.");
                        return;
                    }
                }
                allJoinEventData.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                fileOutputStream,
                StandardCharsets.UTF_8
        );
        BufferedWriter bufferWrite = new BufferedWriter(outputStreamWriter);

        String newJoinEvent = currentUserName + "," + event.getEventName();
        try {
            for(String joinEventData : allJoinEventData) {
                bufferWrite.append(joinEventData);
                bufferWrite.append('\n');
            }
            bufferWrite.append(newJoinEvent);
            bufferWrite.append('\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                bufferWrite.flush();
                bufferWrite.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        goToParticipantActivity();

    }

    @FXML
    private void goToParticipantActivity() {
        try {
            FXRouterPane.goTo("participant-activity", new String[] { event.getEventName(), currentUserName});
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        }

    @FXML
    public void handleVisitTeamButton(){
        try {
            FXRouterPane.goTo("teamofevent-list", new String[] { event.getEventName(), currentUserName });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

