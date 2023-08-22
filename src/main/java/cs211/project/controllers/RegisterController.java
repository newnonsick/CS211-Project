package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class RegisterController {
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
    Button upLoadImageButton;
    @FXML
    ImageView profileImageView;
    File selectedImage = null;

    @FXML
    public void initialize(){
        errorLabel.setText("");
    }

    @FXML
    private void signUp() throws IOException {
        //Possible future bug fix : making it impossible to create an account id "Default"
        //checking and register
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String password2 = confirmPasswordTextField.getText();
        String fullName = nameTextField.getText();
        String filePath = "data/username_password.csv";
        File file = new File(filePath);
        FileInputStream fileInputStream = null;
        if(username.isEmpty() || password.isEmpty() || password2.isEmpty() || fullName.isEmpty()) {
            errorLabel.setText("Please fill in the information.");
            return;
        }
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
        //Another way to write file
        filePath = "data/userData.csv";
        FileWriter fileWriter = new FileWriter(filePath);
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
        printWriter.println(username + "," + fullName);
        printWriter.flush();
        if(selectedImage != null) {
            //upload the profile picture
            String selectedImagePath = selectedImage.getAbsolutePath();
            String targetDirectoryPath = "data/profile_picture";
            Path targetDirectory = Path.of(targetDirectoryPath);
            String fileType = Files.probeContentType(Paths.get(selectedImage.getAbsolutePath()));
            //errorLabel.setText(fileType);
            Path targetFilePath = targetDirectory.resolve(username + "." + (fileType.substring(6)));
            try {
                Files.copy(Path.of(selectedImagePath), targetFilePath, StandardCopyOption.REPLACE_EXISTING);
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
    @FXML
    public void upLoadImage() {
        choosePhotoFile();
        if(selectedImage != null) {
            Image profileImage = new Image(selectedImage.toURI().toString());
            errorLabel.setText(selectedImage.toURI().toString());
            profileImageView.setImage(profileImage);
        }
    }
    private void choosePhotoFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+ "/Desktop"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image","*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files","*.jpg","*.png"));
        Stage stage = (Stage) upLoadImageButton.getScene().getWindow();
        selectedImage = fileChooser.showOpenDialog(stage);
    }
}

