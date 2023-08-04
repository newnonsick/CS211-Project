package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class LoginController {
    private String filePath = "data/username_password.csv";
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML
    Label errorLabel;

    @FXML
    public void login() {
        File file = new File(filePath);
        FileInputStream fileInputStream = null;
        String user = usernameTextField.getText();
        String pass = passwordTextField.getText();
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

        String line = "";
        try {
            while ( (line = buffer.readLine()) != null ){
                if (line.equals("")) continue;
                String[] data = line.split(",");

                String username = data[0].trim();
                String password = data[1].trim();
                if(user.equals(username) && pass.equals(password)) {
                    FXRouter.goTo("mainPage");
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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