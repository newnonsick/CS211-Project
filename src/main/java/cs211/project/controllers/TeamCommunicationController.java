package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class TeamCommunicationController {
    @FXML Label avtivityNameLabel;
    @FXML Label activityDescriptionLabel;
    @FXML TableView activityTableView;
    @FXML TextArea messageTextArea;
    @FXML TextField sendMessageTextField;

    @FXML
    public void handleSendMessageButton() {
        String text = sendMessageTextField.getText();
        if (!text.isEmpty()){
            messageTextArea.appendText("%username% : " + sendMessageTextField.getText() + "\n");
            sendMessageTextField.clear();
        }
    }

    @FXML
    public void handleIncreaseFontButton(){
        Font font = messageTextArea.getFont();
        messageTextArea.setFont(new Font(font.getName(), font.getSize() + 2));
    }

    @FXML
    public void handleDecreaseFontButton(){
        Font font = messageTextArea.getFont();
        messageTextArea.setFont(new Font(font.getName(), font.getSize() - 2));
    }

}
