package cs211.project.controllers;

import cs211.project.models.LogUser;
import cs211.project.models.User;
import cs211.project.models.UserList;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouterPane;
import cs211.project.services.UserLogFileDataSource;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class AdminPageController {
    @FXML
    private TableView usersLogTableView;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label nameLabel;
    private Datasource<UserList> datasource;
    private UserList usersLogList;
    @FXML
    private ImageView profileImageView;
    @FXML
    Label errorLabel;
    @FXML
    Label usernameTagLabel;
    @FXML
    Label nameTagLabel;

    @FXML
    public void initialize() {
        datasource = new UserLogFileDataSource("data", "logInfo.csv");
        usersLogList = datasource.readData();
        showTable(usersLogList);

        usersLogTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LogUser>() {
            @Override
            public void changed(ObservableValue observable, LogUser oldValue, LogUser newValue) {
                if (newValue != null) {
                    profileImageView.setImage(newValue.getProfilePicture());
                    usernameLabel.setText(newValue.getUsername());
                    nameLabel.setText(newValue.getName());
                    usernameTagLabel.setVisible(true);
                    nameTagLabel.setVisible(true);
                }
                else {
                    profileImageView.setImage(null);
                    usernameLabel.setText("");
                    nameLabel.setText("");
                    usernameTagLabel.setVisible(false);
                    nameTagLabel.setVisible(false);
                }
            }
        });
    }

    private void showTable(UserList usersLogList) {
        TableColumn<LogUser, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<LogUser, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<LogUser, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("logDate"));

        TableColumn<LogUser, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("logTime"));

        usersLogTableView.getColumns().clear();
        usersLogTableView.getColumns().add(usernameColumn);
        usersLogTableView.getColumns().add(nameColumn);
        usersLogTableView.getColumns().add(dateColumn);
        usersLogTableView.getColumns().add(timeColumn);

        usersLogTableView.getItems().clear();
        for (User user: usersLogList.getUsers()) {
            usersLogTableView.getItems().add(user);
        }

    }
}
