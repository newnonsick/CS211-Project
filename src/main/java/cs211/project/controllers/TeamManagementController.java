package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.services.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class TeamManagementController {
    @FXML TextField activityNameTextField;
    @FXML TextArea activityDescriptionTextArea;
    @FXML ScrollPane participantListScrollPane;
    @FXML GridPane participantListGridPane;
    @FXML TableView activityTableView;
    @FXML Button deleteActivityButton;
    @FXML Button endActivityButton;
    @FXML TableColumn activityNameColumn;
    @FXML TableColumn activityDescriptionColumn;
    @FXML TableColumn activityStatusColumn;

    private ActivityList activityList;
    private Datasource<ActivityList> activityListDatasource;
    private Activity selectedActivity;
    private Datasource<TeamList> teamListDatasource;
    private TeamList teamList;
    private Team team;
    private Datasource<TeamParticipantList> teamParticipantListDatasource;
    private TeamParticipantList teamParticipantList;
    private String[] componentData;
    private String currentUsername;
    private String eventUUID;
    private String teamName;
    @FXML
    public void initialize(){
        deleteActivityButton.setDisable(true);
        endActivityButton.setDisable(true);
        componentData = (String[]) FXRouterPane.getData();
        eventUUID = componentData[0];
        teamName = componentData[1];
        currentUsername = componentData[2];
        teamListDatasource = new TeamListFileDatasource("data", "team_list.csv");
        teamList = teamListDatasource.readData();
        team = teamList.findTeamByObject(teamList.findEventByEventUUIDAndTeamName(eventUUID, teamName));
        activityListDatasource = new TeamActivityListFileDatasource("data", "team_activity_list.csv");
        activityList = activityListDatasource.readData();
        teamParticipantListDatasource = new TeamParticipantListFileDataSource("data", "team_participant_list.csv");
        teamParticipantList = teamParticipantListDatasource.readData();
        showActivity(activityList);
        showParticipant();

        activityTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Activity>() {
            @Override
            public void changed(ObservableValue observable, Activity oldValue, Activity newValue) {
                if (newValue != null) {
                    deleteActivityButton.setDisable(false);
                    if (currentUsername.equals(team.getTeamOwnerUsername())){
                        endActivityButton.setDisable(false);
                    }
                    selectedActivity = newValue;
                }
                else{
                    deleteActivityButton.setDisable(true);
                    endActivityButton.setDisable(true);
                }
            }
        });
    }

    @FXML
    public void handleEndActivityButton(){
        activityList.findActivityByObject(selectedActivity).setActivityStatus("Ended");
        activityListDatasource.writeData(activityList);
        showActivity(activityList);
        selectedActivity = null;
        deleteActivityButton.setDisable(true);
        endActivityButton.setDisable(true);
    }

    @FXML
    public void handleAddActivityButton(){
        String activityName = activityNameTextField.getText();
        String activityDescription = activityDescriptionTextArea.getText();
        UUID uuid = UUID.randomUUID();
        String activityUUID = uuid.toString();
        while (activityList.findActivityByUUID(activityUUID) != null){
            uuid = UUID.randomUUID();
            activityUUID = uuid.toString();
        }
        if (!activityName.isEmpty() && !activityDescription.isEmpty()){
            activityList.addNewActivityTeam(eventUUID, teamName, activityName, activityDescription, "In Progress", activityUUID);
            activityListDatasource.writeData(activityList);
            showActivity(activityList);
            activityNameTextField.clear();
            activityDescriptionTextArea.clear();
        }
    }

    @FXML
    public void handleRemoveActivityButton(){
        activityList.getActivities().remove(selectedActivity);
        activityListDatasource.writeData(activityList);
        showActivity(activityList);
        Datasource<TeamChatList> teamChatListDatasource = new TeamChatListFileDatasource("data", "team_chat_list.csv");
        TeamChatList teamChatList = teamChatListDatasource.readData();
        ArrayList<TeamChat> removeTeamChats = new ArrayList<>();
        for (TeamChat teamChat: teamChatList.getTeamChats()) {
            if (teamChat.getActivityUUID().equals(selectedActivity.getActivityUUID())){
                removeTeamChats.add(teamChat);
            }
        }
        for (TeamChat teamChat: removeTeamChats) {
            teamChatList.getTeamChats().remove(teamChat);
        }
        teamChatListDatasource.writeData(teamChatList);
        selectedActivity = null;
        deleteActivityButton.setDisable(true);
        endActivityButton.setDisable(true);
    }

    public void showActivity(ActivityList activityList){
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));

        activityDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("activityInformation"));

        activityStatusColumn.setCellValueFactory(new PropertyValueFactory<>("activityStatus"));

        activityTableView.getColumns().clear();
        activityTableView.getColumns().add(activityStatusColumn);
        activityTableView.getColumns().add(activityNameColumn);
        activityTableView.getColumns().add(activityDescriptionColumn);
        activityTableView.getItems().clear();


        for (Activity activity : activityList.getActivities()) {
            if (activity.getTeamOfActivityName().equals(teamName) && activity.getEventOfActivityUUID().equals(eventUUID)) {
                activityTableView.getItems().add(activity);
            }
        }
    }

    public void showParticipant(){
        int row = 0;
        for (TeamParticipant teamParticipant: teamParticipantList.getTeamParticipants()) {
            if (!teamParticipant.getEventUUID().equals(eventUUID) && !teamParticipant.getTeamName().equals(teamName)) {
                continue;
            }
            if (teamParticipant.getUsername().equals(team.getTeamOwnerUsername())){
                continue;
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/team-participant-element.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            TeamParticipantElementController team_ = fxmlLoader.getController();
            team_.setTeamParticipant(teamParticipant.getUsername(), teamParticipant.getEventUUID(), teamParticipant.getTeamName(), currentUsername);
            participantListGridPane.add(anchorPane, 0, row);
            row++;

            GridPane.setMargin(anchorPane, new Insets(10));
        }
    }
}
