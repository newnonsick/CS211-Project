package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.models.User;
import cs211.project.services.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;

public class MyEventsController {
    @FXML private TableView<Event> myEventsTableView;
    private EventList eventList;
    private Datasource<EventList> datasource;
    private User currentUser;

    @FXML
    public void initialize() {
        currentUser = (User) FXRouter.getData();
        datasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = datasource.readData();
        showTable(eventList);

        myEventsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue observable, Event oldValue, Event newValue) {
                if (newValue != null) {
                    try {
                        FXRouterPane.goTo("event-management", new String[] {newValue.getEventUUID(), currentUser.getUsername()});
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void showTable(EventList eventList) {
        TableColumn<Event, String> eventNameColumn = new TableColumn<>("Name");
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Event, String> eventCategoryColumn = new TableColumn<>("Category");
        eventCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Event, String> placeColumn = new TableColumn<>("Place");
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("place"));

        TableColumn<Event, String> eventStartDateColumn = new TableColumn<>("Start Date");
        eventStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<Event, String> eventEndDateColumn = new TableColumn<>("End Date");
        eventEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        TableColumn<Event, String> participantsColumn = new TableColumn<>("Max Participants");
        participantsColumn.setCellValueFactory(cellData -> {
            int maxParticipants = cellData.getValue().getMaxParticipants();
            if (maxParticipants == -1) {
                return new SimpleStringProperty("ไม่จำกัดจำนวนผู้เข้าร่วม");
            } else {
                return new SimpleStringProperty(String.valueOf(maxParticipants));
            }
        });

        myEventsTableView.getColumns().clear();
        myEventsTableView.getColumns().add(eventNameColumn);
        myEventsTableView.getColumns().add(eventCategoryColumn);
        myEventsTableView.getColumns().add(placeColumn);
        myEventsTableView.getColumns().add(eventStartDateColumn);
        myEventsTableView.getColumns().add(eventEndDateColumn);
        myEventsTableView.getColumns().add(participantsColumn);

        myEventsTableView.getItems().clear();

        for (Event event: eventList.getEvents()) {
            if (event.getOwnerUsername().equals(currentUser.getUsername()))
                myEventsTableView.getItems().add(event);
        }

    }
}
