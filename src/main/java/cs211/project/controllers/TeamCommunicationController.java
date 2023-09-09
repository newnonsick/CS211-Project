package cs211.project.controllers;

import cs211.project.models.Team;
import cs211.project.models.TeamList;
import cs211.project.services.CurrentUser;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouterPane;
import cs211.project.services.TeamListFileDatasource;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TeamCommunicationController {
    @FXML Label avtivityNameLabel;
    @FXML Label activityDescriptionLabel;
    @FXML Label teamNameLabel;
    @FXML TableView activityTableView;
    @FXML TextArea messageTextArea;
    @FXML TextField sendMessageTextField;
    @FXML Button manageTeamButton;

    private TeamList teamList;
    private Datasource<TeamList> datasource;
    private Team team;
    @FXML
    public void initialize(){
        team = (Team) FXRouterPane.getData();
        messageTextArea.setEditable(false);
        manageTeamButton.setVisible(true);
//        datasource = new TeamListFileDatasource("data", "team_list.csv");
//        teamList = datasource.readData();
        try {
            if (team.getHeadOfTeamUsername().equals(CurrentUser.getUser().getName())){
                manageTeamButton.setVisible(true);
            }
        } catch (NullPointerException e){
            manageTeamButton.setVisible(false);
        }
        teamNameLabel.setText(team.getTeamName());
        avtivityNameLabel.setText("");
        activityDescriptionLabel.setText("");
    }

    @FXML
    public void handleSendMessageButton() {
        String text = sendMessageTextField.getText();
        if (!text.isEmpty()){
            messageTextArea.appendText(CurrentUser.getUser().getUsername() + " : " + sendMessageTextField.getText() + "\n");
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

    @FXML
    public void handleManageTeamButton(){
        try {
            FXRouterPane.goTo("team-management", team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
