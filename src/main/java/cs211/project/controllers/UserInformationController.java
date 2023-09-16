package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import cs211.project.models.User;

public class UserInformationController {
    private User currentUser;

    @FXML private Label usernameLabel;
    @FXML private Label nameLabel;
    @FXML private TableView activeEventTableView;
    @FXML private TableView eventHistoryTableview;
    @FXML private ImageView profileImageView;

    public void initialize() {
        currentUser = (User) FXRouter.getData();
        checkFileIsExisted("userData.csv");
        showUser();
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
        nameLabel.setText(currentUser.getName());
        usernameLabel.setText(currentUser.getUsername());
        // profile picture
    }
}
