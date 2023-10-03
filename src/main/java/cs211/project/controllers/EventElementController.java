package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class EventElementController {
    @FXML
    Label eventNameLabel;
    @FXML
    ImageView eventImageView;

    @FXML
    Label categoryLabel;

    public void setPage(String name, String imgName, String category) {
        String filePath = "data"+ File.separator + "eventPicture" + File.separator + imgName;
        File file = new File(filePath);
        Image eventImage = new Image(file.toURI().toString(),176,129,false,false);
        eventNameLabel.setText(name);
        eventImageView.setImage(eventImage);
        categoryLabel.setText("หมวดหมู่ : " + category);
    }
}
