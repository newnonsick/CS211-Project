package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.services.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

public class TeamCommunicationController {
    @FXML Label avtivityNameLabel;
    @FXML Label activityDescriptionLabel;
    @FXML Label teamNameLabel;
    @FXML TableView activityTableView;
    @FXML TextArea messageTextArea;
    @FXML TextField sendMessageTextField;
    @FXML Button manageTeamButton;
    @FXML GridPane teamChatGridPane;
    @FXML ScrollPane messageScrollPane;

    private TeamChatList teamChatList;
    private Team team;
    private Datasource<TeamChatList> teamChatListDatasource;
    private Datasource<ActivityList> activityListDatasource;
    private ActivityList activityList;
    @FXML
    public void initialize(){
        team = (Team) FXRouterPane.getData();
        manageTeamButton.setVisible(false);
        if (team.getHeadOfTeamUsername().equals(CurrentUser.getUser().getUsername())){
            manageTeamButton.setVisible(true);
        }
        teamNameLabel.setText(team.getTeamName());
        avtivityNameLabel.setText("");
        activityDescriptionLabel.setText("");
        teamChatListDatasource = new TeamChatListFileDatasource("data", "team_chat_list.csv");
        teamChatList = teamChatListDatasource.readData();
        activityListDatasource = new TeamActivityListFileDatasource("data", "team_activity_list.csv");
        activityList = activityListDatasource.readData();
        showActivity(activityList);
        showChat(true);

        activityTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Activity>() {
            @Override
            public void changed(ObservableValue observable, Activity oldValue, Activity newValue) {
                if (newValue != null) {
                    if (newValue.getActivityStatus().equals("Ended")){
                        avtivityNameLabel.setText("");
                        activityDescriptionLabel.setText("");
                        return;
                    }
                    avtivityNameLabel.setText(newValue.getActivityName());
                    activityDescriptionLabel.setText(newValue.getActivityInformation());
                }
                else{
                    avtivityNameLabel.setText("");
                    activityDescriptionLabel.setText("");
                }
            }
        });
    }

    @FXML
    public void handleSendMessageButton() {
        String text = sendMessageTextField.getText();
        if (!text.isEmpty()){
            sendMessageTextField.clear();
            teamChatList.addNewChat(team.getEventOfTeamName(), team.getTeamName(), CurrentUser.getUser().getUsername(), text);
            teamChatListDatasource.writeData(teamChatList);
            showChat(false);
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


    public void showChat(boolean isShowAll) {
        int row = 0;
        if (isShowAll){
            teamChatGridPane.getChildren().clear();
            for (TeamChat teamChat : teamChatList.getTeamChats()) {
                if (!teamChat.getEventName().equals(team.getEventOfTeamName()) || !teamChat.getTeamName().equals(team.getTeamName())) {
                    continue;
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                if (teamChat.getUsername().equals(CurrentUser.getUser().getUsername())) {
                    fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/my-message.fxml"));
                    AnchorPane anchorPane = null;
                    try {
                        anchorPane = fxmlLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MyMessageController messageController = fxmlLoader.getController();
                    messageController.setPage(teamChat.getMessage(), teamChat.getUsername());
                    teamChatGridPane.add(anchorPane, 0, row);
                    row++;

                    GridPane.setMargin(anchorPane, new Insets(10));
                } else {
                    fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/other-message.fxml"));
                    AnchorPane anchorPane = null;
                    try {
                        anchorPane = fxmlLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    OtherMessageController messageController = fxmlLoader.getController();
                    messageController.setPage(teamChat.getMessage(), teamChat.getUsername());
                    teamChatGridPane.add(anchorPane, 0, row);
                    row++;

                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            }
            messageScrollPane.setVvalue(1.0);
        }
        else {
            TeamChat teamChat = teamChatList.getTeamChats().get(teamChatList.getTeamChats().size() - 1);
            row = teamChatGridPane.getRowCount();
            if (!teamChat.getEventName().equals(team.getEventOfTeamName()) || !teamChat.getTeamName().equals(team.getTeamName())) {
                return;
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            if (teamChat.getUsername().equals(CurrentUser.getUser().getUsername())) {
                fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/my-message.fxml"));
                AnchorPane anchorPane = null;
                try {
                    anchorPane = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                MyMessageController messageController = fxmlLoader.getController();
                messageController.setPage(teamChat.getMessage(), teamChat.getUsername());
                teamChatGridPane.add(anchorPane, 0, row);

                GridPane.setMargin(anchorPane, new Insets(10));
            } else {
                fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/other-message.fxml"));
                AnchorPane anchorPane = null;
                try {
                    anchorPane = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                OtherMessageController messageController = fxmlLoader.getController();
                messageController.setPage(teamChat.getMessage(), teamChat.getUsername());
                teamChatGridPane.add(anchorPane, 0, row);

                GridPane.setMargin(anchorPane, new Insets(10));

            }
            Platform.runLater(() -> messageScrollPane.setVvalue(1.0));
        }
    }




    public void showActivity(ActivityList activityList){
        TableColumn<Activity, String> activityNameColumn = new TableColumn<>("Name");
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));

        TableColumn<Activity, String> activityInformationColumn = new TableColumn<>("Description");
        activityInformationColumn.setCellValueFactory(new PropertyValueFactory<>("activityInformation"));

        TableColumn<Activity, String> activityStatusColumn = new TableColumn<>("Status");
        activityStatusColumn.setCellValueFactory(new PropertyValueFactory<>("activityStatus"));

        activityTableView.getColumns().clear();
        activityTableView.getColumns().add(activityStatusColumn);
        activityTableView.getColumns().add(activityNameColumn);
        activityTableView.getColumns().add(activityInformationColumn);
        activityTableView.getItems().clear();

        if (activityList.getActivities().size() == 0){
            activityInformationColumn.setPrefWidth(activityTableView.getPrefWidth() - activityNameColumn.getPrefWidth() - activityStatusColumn.getPrefWidth());
            return;
        }


        for (Activity activity : activityList.getActivities()) {
            if (activity.getTeamOfActivityName().equals(team.getTeamName()) && activity.getEventOfActivityName().equals(team.getEventOfTeamName())) {
                activityTableView.getItems().add(activity);
            }
        }
    }

}
