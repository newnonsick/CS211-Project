package cs211.project.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import cs211.project.models.User;
import cs211.project.models.UserList;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.nio.charset.StandardCharsets;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class LoginController {
    private String directoryName = "data";
    private String fileName = "userData.csv";
    private String filePath = directoryName + File.separator + fileName;
    private String loginFilePath = directoryName + File.separator + "logInfo.csv";
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML
    Label errorLabel;

    public void initialize() {
    }


    @FXML
    public void login() throws IOException {
        String username = usernameTextField.getText();
        String inputPassword = passwordTextField.getText();
        String destination = "mainPage";

        Datasource<UserList> userListDatasource = new UserListFileDataSource("data", "userData.csv");
        UserList userList = userListDatasource.readData();
        Datasource<UserList> userListUserLogFileDataSource = new UserLogFileDataSource("data", "logInfo.csv");
        UserList userLogList = new UserList();
        if (username.equals("admin211")) {
            destination = "adminPage";
        }
        for(User user : userList.getUsers()) {
            if(user.getUsername().equals(username)) {
                BCrypt.Result result = BCrypt.verifyer().verify(inputPassword.toCharArray(), user.getPassword());
                if(result.verified) {
                    userLogList.addUser(user);
                    userListUserLogFileDataSource.writeData(userLogList);
                    FXRouter.goTo(destination, user);
                }
            }
        }
        errorLabel.setText("username or password is incorrect!");
        usernameTextField.setText("");
        passwordTextField.setText("");
    }

    @FXML
    private void goToRegister() {
        try {
            FXRouter.goTo("register");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}