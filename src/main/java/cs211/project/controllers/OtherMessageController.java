package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class OtherMessageController {
    @FXML Label messageLabel;
    @FXML ImageView userImage;
    @FXML HBox hBox;
    @FXML VBox vBox;
    @FXML AnchorPane anchorPane;
    @FXML Label usernameLabel;
    public void setPage(String message, String username) {
        int length = message.length();
        int theshold = length / 43;
        int messageWidth = 0;
        if (theshold == 0){
            for (int i = 0; i < length; i++) {
                if (message.charAt(i) == message.toLowerCase().charAt(i)){
                    messageWidth += 10;
                }
                else {
                    messageWidth += 12;
                }
            }
        }
        else{
            messageWidth = 400;
        }

        Circle clip = new Circle(20, 20, 20);
        userImage.setClip(clip);
        messageLabel.setPrefHeight(30 + theshold * 30);
        vBox.setPrefHeight(42 + theshold * 42);
        hBox.setPrefHeight(40 + theshold * 40);
        anchorPane.setPrefHeight(60 + theshold * 30);
        messageLabel.setPrefWidth(messageWidth);
        messageLabel.setText(message);
        usernameLabel.setText(username);
    }
}
