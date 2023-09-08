package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EventElementController {
    @FXML
    Label eventNameLabel;
    @FXML
    ImageView eventImageView;

    public void setPage(String name, String imgName) {
        String filePath = "data/eventPicture/" + imgName;
        File file = new File(filePath);
        Image eventImage = new Image(file.toURI().toString(),176,129,false,false);
        eventNameLabel.setText(name);
        eventImageView.setImage(eventImage);
    }
    @FXML
    public void mouseEnter() {
        eventNameLabel.setStyle("-fx-background-color: blue;");
    }

    public void mouseExit() {
        eventNameLabel.setStyle("-fx-background-color: transparent;");
    }
}
