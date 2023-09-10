package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OtherMessageController {
    @FXML Label messageLabel;
    @FXML ImageView userImage;
    @FXML HBox hBox;
    @FXML VBox vBox;
    @FXML AnchorPane anchorPane;
    @FXML Label usernameLabel;
    public void setPage(String message, String username) {
        int length = message.length();
        int theshold = length / 39;
        try {
            messageLabel.setPrefHeight(30 + theshold * 30);
            vBox.setPrefHeight(42 + theshold * 42);
            hBox.setPrefHeight(40 + theshold * 40);
            anchorPane.setPrefHeight(60 + theshold * 30);
            messageLabel.setPrefWidth(12 * (length / theshold));
        } catch (ArithmeticException e) {
            messageLabel.setPrefWidth(12 * length);

        } finally {
            messageLabel.setText(message);
            usernameLabel.setText(username);
        }
    }
}
