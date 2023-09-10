package cs211.project.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import cs211.project.models.User;
import cs211.project.services.CurrentUser;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
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
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        file = new File(directoryName);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(loginFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void login() {
        File file = new File(filePath);
        File logInfoFile = new File(loginFilePath);
        FileInputStream fileInputStream = null;
        String user = usernameTextField.getText();
        String inputPassword = passwordTextField.getText();
        String destination = "mainPage";
        ZoneId thaiTimeZone = ZoneId.of("Asia/Bangkok");
        if (user.equals("admin211")) {
            destination = "adminPage";
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
        BufferedReader buffer = new BufferedReader(inputStreamReader);

        String line = "";
        try {
            while ((line = buffer.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] data = line.split(",");

                String username = data[0].trim();
                String password = data[1].trim();
                if (user.equals(username)) {
                    BCrypt.Result result = BCrypt.verifyer().verify(inputPassword.toCharArray(), password);
                    if (result.verified) {
                        CurrentUser.setUser(new User(user));
                        String date = LocalDate.now(thaiTimeZone).toString();
                        String time = LocalTime.now(thaiTimeZone).toString().substring(0,8);
                        String log = user + "," + date + "," + time;
                        FileWriter fileWriter = null;
                        PrintWriter out = null;
                        try {
                            fileWriter = new FileWriter(logInfoFile,true);
                            out = new PrintWriter(new BufferedWriter(fileWriter));
                            out.println(log);
                            out.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } finally {
                            if (out != null) {
                                out.close();
                            }
                        }
                        FXRouter.goTo(destination);
                    } else {
                        break;
                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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