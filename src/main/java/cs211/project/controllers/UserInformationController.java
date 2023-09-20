package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinEventFileDataSource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class UserInformationController {
    @FXML private Label usernameLabel;
    @FXML private Label nameLabel;
    @FXML private TableView<Event> activeEventTableView;
    @FXML private TableView<Event> eventHistoryTableView;
    @FXML private ImageView profileImageView;
    private User currentUser;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private Datasource<List<String[]>> joinEventDataSource; // Changed variable name
    private List<String[]> joinEventData;

    public void initialize() {
        currentUser = (User) FXRouter.getData();

        eventListDatasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = eventListDatasource.readData();

        joinEventDataSource = new JoinEventFileDataSource("data", "joinEventData.csv");
        joinEventData = joinEventDataSource.readData();

        checkFileIsExisted("userData.csv");
        showUser();
        showTable(eventList);
    }

    private void checkFileIsExisted(String fileName) {
        File file = new File("data");
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = "data" + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showUser() {
        nameLabel.setText(currentUser.getName());
        usernameLabel.setText(currentUser.getUsername());

        String filePath = "data/profile_picture/" + currentUser.getProfilePicture();
        File file = new File(filePath);
        profileImageView.setImage(new Image(file.toURI().toString()));
    }

    private void showTable(EventList eventList) {
        TableColumn<Event, String> eventNameColumn = new TableColumn<>("Name");
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));

        TableColumn<Event, String> eventCategoryColumn = new TableColumn<>("Category");
        eventCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("eventCategory"));

        TableColumn<Event, String> eventStartDateColumn = new TableColumn<>("Start Date");
        eventStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventStartDate"));

        TableColumn<Event, String> eventEndDateColumn = new TableColumn<>("End Date");
        eventEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventEndDate"));

        activeEventTableView.getColumns().clear();
        activeEventTableView.getColumns().addAll(eventNameColumn, eventCategoryColumn, eventStartDateColumn, eventEndDateColumn);
        activeEventTableView.getItems().clear();


        eventHistoryTableView.getColumns().clear();
        eventHistoryTableView.getColumns().addAll(eventNameColumn, eventCategoryColumn, eventStartDateColumn, eventEndDateColumn);
        eventHistoryTableView.getItems().clear();

        ZoneId thaiTimeZone = ZoneId.of("Asia/Bangkok");
        LocalDate currentDate = LocalDate.now(thaiTimeZone);

        for (Event event : eventList.getEvents()) {
            for (String[] data : joinEventData) {
                String username = data[0];
                String eventName = data[1];

                if (username.equals(currentUser.getUsername()) && eventName.equals(event.getEventName())) {
                    if (event.getEventEndDate().isBefore(currentDate)) {
                        eventHistoryTableView.getItems().add(event);
                    } else {
                        activeEventTableView.getItems().add(event);
                    }
                }
            }
        }
    }
}
