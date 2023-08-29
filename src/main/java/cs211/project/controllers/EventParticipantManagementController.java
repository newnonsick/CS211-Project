package cs211.project.controllers;

import cs211.project.models.Participant;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import cs211.project.services.FXRouterPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EventParticipantManagementController {

    @FXML private TableView<Participant> eventParticipantTableView;
    @FXML
    private TableColumn<Participant, String> partiNameColumn;
    @FXML
    private TableColumn<Participant, String> partiUsernameColumn;
    @FXML
    private TableColumn<Participant, String> approvedColumn;
    @FXML
    private TableColumn<Participant, Void> approvedActionColumn;
    private String filePath = "data/participant_test.csv";


    public void initialize(){
        partiNameColumn.setCellValueFactory(new PropertyValueFactory<>("partiName"));
        partiUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("partiUsername"));
        approvedColumn.setCellValueFactory(new PropertyValueFactory<>("approved"));
        approvedActionColumn.setCellFactory(param -> new TableCell<>() {
                private final Button approvedButton = new Button("Change Status");
        {
                approvedButton.setOnAction(event -> {
                Participant participant = getTableView().getItems().get(getIndex());
                participant.setApproved(true);
//              updateParticipant(participant);  used for update approval status
                eventParticipantTableView.refresh();
                goToEditParticipant();
                });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(approvedButton);
            }
        }
    });

        Platform.runLater(() -> ParticipantList());
}

    public void ParticipantList() {
        File file = new File("data/participant_test.csv");
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
        List<Participant> participants = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // if empty line, continue
                if (line.equals("")) continue;
                //separate string
                String[] data = line.split(",");
                //read data and classify data
                String partiName = data[0].trim();
                String partiUsername = data[1].trim();
                boolean approved = Boolean.parseBoolean(data[2].trim());
                participants.add(new Participant(partiName, partiUsername, approved));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        eventParticipantTableView.getItems().clear();
        eventParticipantTableView.getItems().addAll(participants);
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
}
