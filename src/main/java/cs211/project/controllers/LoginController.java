package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LoginController {
    private String filePath = "src/main/resources/data/username_password.csv";
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML
    Label errorLabel;

    @FXML
    //In the future, we will make it be able to return a user object to identify who is currently using the app
    public void login() {
        String USERNAME;
        String PASS;
        try {
            Scanner x = new Scanner(new File(filePath));
            x.useDelimiter("[,\n]");
            //Traversing through csv to find the right username and password
            while (x.hasNext()) {
                USERNAME = x.next();
                PASS = x.next();
                if(USERNAME.trim().equals(usernameTextField.getText()) && PASS.trim().equals(passwordTextField.getText())) {
                    try {
                        FXRouter.goTo("mainPage");
                        return;
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            errorLabel.setText("Wrong ID or password!");
            usernameTextField.setText("");
            passwordTextField.setText("");
        } catch (Exception e) {
        }
    }
    @FXML
    private void goToRegister() {
        try {
            FXRouter.goTo("register");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
