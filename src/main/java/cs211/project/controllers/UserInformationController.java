package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import cs211.project.models.User;
import cs211.project.services.CurrentUser;

public class UserInformationController {
    @FXML private Label usernameLabel;
    @FXML private Label nameLabel;
    @FXML private TableView<Event> activeEventTableView;
    @FXML private TableView<Event> eventHistoryTableview;
    @FXML private ImageView profileImageView;
    private EventList eventList;
    private Datasource<EventList> datasource;

    public void initialize() {
        CurrentUser.ThisUser user = CurrentUser.getUser();
        datasource = new EventListFileDatasource("data", "joinEventData.csv");
        eventList = datasource.readData();

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
        CurrentUser.ThisUser user = CurrentUser.getUser();
        nameLabel.setText(user.getName());
        usernameLabel.setText(user.getUsername());

        String filePath = "data/profile_picture/" + user.getPic();
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
        eventHistoryTableview.getColumns().clear();

        activeEventTableView.getColumns().add(eventNameColumn);
        activeEventTableView.getColumns().add(eventCategoryColumn);
        activeEventTableView.getColumns().add(eventStartDateColumn);
        activeEventTableView.getColumns().add(eventEndDateColumn);

        eventHistoryTableview.getColumns().add(eventNameColumn);
        eventHistoryTableview.getColumns().add(eventCategoryColumn);
        eventHistoryTableview.getColumns().add(eventStartDateColumn);
        eventHistoryTableview.getColumns().add(eventEndDateColumn);

        activeEventTableView.getItems().clear();
        eventHistoryTableview.getItems().clear();

    }
}
