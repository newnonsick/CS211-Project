package cs211.project.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import cs211.project.models.User;
import cs211.project.models.UserList;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.UserListFileDataSource;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
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
    @FXML
    Button cancelUploadButton;
    File selectedImage = null;
    boolean upFile = false;

    @FXML
    public void initialize(){
        errorLabel.setText("");
        profileImageView.setImage(new Image(getClass().getResource("/cs211/project/images/default.png").toExternalForm()));
    }

    @FXML
    private void signUp() throws IOException {
        //checking and register
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String password2 = confirmPasswordTextField.getText();
        String fullName = nameTextField.getText();
        String profilePic = "";
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
        if (username.equals("Default")) {
            errorLabel.setText("That user name is not allowed.");
            passwordTextField.setText("");
            confirmPasswordTextField.setText("");
            return;
        }
        if (username.contains(",")) {
            errorLabel.setText("Comma \",\" is not allowed is username");
            passwordTextField.setText("");
            confirmPasswordTextField.setText("");
            return;
        }

        Datasource<UserList> userListDatasource = new UserListFileDataSource("data", "userData.csv");
        UserList userList = userListDatasource.readData();

        for(User user : userList.getUsers()) {
            if(user.getUsername().equals(username)) {
                errorLabel.setText("Username already exist!!");
                passwordTextField.setText("");
                confirmPasswordTextField.setText("");
                return;
            }
        }

        String encryptPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        if(!upFile) {
            profilePic = "default.png";
        }
        else {
            profilePic = username + "." + (Files.probeContentType(Paths.get(selectedImage.getAbsolutePath())).substring(6));
        }
        userList.addUser(new User(username, encryptPassword, fullName, profilePic));
        userListDatasource.writeData(userList);
        if(selectedImage != null) {
            //upload the profile picture
            String selectedImagePath = selectedImage.getAbsolutePath();
            String targetDirectoryPath = "data" + File.separator + "profile_picture";
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
            profileImageView.setImage(profileImage);
        }
        cancelUploadButton.setVisible(true);
        cancelUploadButton.setDisable(false);
        upFile = true;
    }
    private void choosePhotoFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+ "/Desktop"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All image files","*.jpg","*.png", "*.jpeg", "*.webp",  "*.jfif" , "*.pjpeg" , "*.pjp"));
        Stage stage = (Stage) upLoadImageButton.getScene().getWindow();
        selectedImage = fileChooser.showOpenDialog(stage);
    }
    @FXML
    private void cancelUpload() {
        selectedImage = null;
        profileImageView.setImage(new Image(getClass().getResource("/cs211/project/images/default.png").toExternalForm()));
        cancelUploadButton.setDisable(true);
        cancelUploadButton.setVisible(false);
        upFile = false;
    }
}

