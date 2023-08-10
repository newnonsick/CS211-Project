package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.ArrayList;

public class RegisterController {
    private String filePath = "data/username_password.csv";
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
    public void initialize(){
        errorLabel.setText("");
    }

    @FXML
    private void signUp() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String password2 = confirmPasswordTextField.getText();
        String fullName = nameTextField.getText();
        File file = new File(filePath);
        FileInputStream fileInputStream = null;

        if (!password.equals(password2)) {
            errorLabel.setText("Please make sure to type the correct passwords");
            passwordTextField.setText("");
            confirmPasswordTextField.setText("");
            return;
        }
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );
        BufferedReader bufferRead = new BufferedReader(inputStreamReader);
        ArrayList<String> userPass = new ArrayList<String>();
        String line = "";
        try {
            while ((line = bufferRead.readLine()) != null) {
                if (line.equals("")) continue;

                String[] data = line.split(",");

                String id = data[0].trim();
                if (id.equals(username)) {
                    errorLabel.setText("Username already exist!");
                    usernameTextField.setText("");
                    return;
                }
                userPass.add(line);
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
        String newAcc = username + "," + password;
        try {
            for(String acc : userPass) {
                bufferWrite.append(acc);
                bufferWrite.append('\n');
            }
            bufferWrite.append(newAcc);
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
        goToLogin();
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

