package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.Scanner;

public class RegisterController {
    private String filePath = "src/main/resources/data/username_password.csv";
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML
    PasswordField confirmPasswordTextField;
    @FXML
    TextField nameTextField;
    @FXML
    Label errorLabel;
    @FXML
    private void signUp() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String password2 = confirmPasswordTextField.getText();
        String fullName = nameTextField.getText();
        //Check if username already exist
        try {
            String USERNAME;
            String PASS;
            Scanner x = new Scanner(new File(filePath));
            x.useDelimiter("[,\n]");
            //Traversing through csv file
            while (x.hasNext()) {
                USERNAME = x.next();
                PASS = x.next();
                if (USERNAME.trim().equals(username)) {
                    errorLabel.setText("Username already exist!");
                    usernameTextField.setText("");
                    return;
                }
            }

        } catch (Exception e) {
        }
        //Check if password and confirm password the same or no
        if(!password.equals(password2)){
            errorLabel.setText("Please make sure you type the correct passwords.");
            passwordTextField.setText("");
            confirmPasswordTextField.setText("");
            return;
        }
        //Writing down to csv
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw =  new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(username+","+password);
            pw.flush();
            pw.close();
            goToLogin();
        } catch (IOException e) {
        }
    }
    @FXML
    private void goToLogin() {
        try {
            FXRouter.goTo("login");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

