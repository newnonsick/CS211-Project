package cs211.project.controllers;

import cs211.project.models.Team;
import cs211.project.models.TeamChatList;
import cs211.project.models.TeamList;
import cs211.project.services.*;
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
    private TeamChatList teamChatList;
    private Team team;
    private Datasource<TeamChatList> teamChatListDatasource;
    @FXML
    public void initialize(){
        team = (Team) FXRouterPane.getData();
        messageTextArea.setEditable(false);
        manageTeamButton.setVisible(false);
//        datasource = new TeamListFileDatasource("data", "team_list.csv");
//        teamList = datasource.readData();
        if (team.getHeadOfTeamUsername().equals(CurrentUser.getUser().getUsername())){
            manageTeamButton.setVisible(true);
        }
        teamNameLabel.setText(team.getTeamName());
        avtivityNameLabel.setText("");
        activityDescriptionLabel.setText("");
        teamChatListDatasource = new TeamChatListFileDatasource("data", "team_chat_list.csv");
        teamChatList = teamChatListDatasource.readData();
        showChat();
    }

    @FXML
    public void handleSendMessageButton() {
        String text = sendMessageTextField.getText();
        if (!text.isEmpty()){
            messageTextArea.appendText(CurrentUser.getUser().getUsername() + " : " + text + "\n");
            sendMessageTextField.clear();
            teamChatList.addNewChat(team.getEventOfTeamName(), team.getTeamName(), CurrentUser.getUser().getUsername(), text);
            teamChatListDatasource.writeData(teamChatList);
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

    public void showChat(){
        messageTextArea.clear();
        ArrayList<String> chat = teamChatList.getChats(team.getEventOfTeamName(), team.getTeamName());
        for (String message : chat){
            messageTextArea.appendText(message + "\n");
        }
    }

}
