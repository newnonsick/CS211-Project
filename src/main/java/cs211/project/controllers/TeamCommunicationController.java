package cs211.project.controllers;

import cs211.project.models.Team;
import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.io.IOException;

public class TeamCommunicationController {
    @FXML Label avtivityNameLabel;
    @FXML Label activityDescriptionLabel;
    @FXML TableView activityTableView;
    @FXML TextArea messageTextArea;
    @FXML TextField sendMessageTextField;
    @FXML Button manageTeamButton;

    private Team team;

    @FXML
    public void initialize(){
        messageTextArea.setEditable(false);
        //manageTeamButton.setVisible(false);
    }

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

    //ตอนจริงจะเห็นปุ่มแค่คนที่เป็นหัวหน้าทีม
    @FXML
    public void handleManageTeamButton(){
        try {
            FXRouterPane.goTo("team-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
