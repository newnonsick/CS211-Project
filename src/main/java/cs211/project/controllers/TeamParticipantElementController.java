package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class TeamParticipantElementController {
    private Datasource<TeamParticipantList> teamParticipantListDatasource;
    private TeamParticipantList teamParticipantList;
    private TeamParticipant teamParticipant;

    private Datasource<TeamList> teamListDatasource;
    private TeamList teamList;
    private Team team;

    private Datasource<UserList> userListDatasource;
    private UserList userList;
    private User user;
    private String currentUsername;

    @FXML
    ImageView profileImageView;
    @FXML
    ImageView leaderImageView;
    @FXML
    Button removeParticipantButton;
    @FXML
    Button setLeaderButton;
    @FXML
    Label nameLabel;

    public void initialize(){
        teamParticipantListDatasource = new TeamParticipantListFileDataSource("data", "team_participant_list.csv");
        teamParticipantList = teamParticipantListDatasource.readData();
        userListDatasource = new UserListFileDataSource("data", "userData.csv");
        userList = userListDatasource.readData();
        teamListDatasource = new TeamListFileDatasource("data", "team_list.csv");
        teamList = teamListDatasource.readData();
        leaderImageView.setVisible(false);
    }

    public void setTeamParticipant(String username, String eventOfTeamName, String teamName, String currentUsername){
        teamParticipant = teamParticipantList.findTeamParticipantByUsernameAndEventAndTeam(username, eventOfTeamName, teamName);
        user = userList.findUserByUsername(username);
        team = teamList.findTeamByNameAndEvent(eventOfTeamName, teamName);
        this.currentUsername = currentUsername;
        //profile size 40 x 40
        Circle img = new Circle(20, 20, 20);
        profileImageView.setClip(img);
        File file = new File("data" + File.separator + "profile_picture" + File.separator + user.getProfilePicture());
        profileImageView.setImage(new Image(file.toURI().toString(), 40, 40, false, false));
        nameLabel.setText(user.getName());
        if (team.getHeadOfTeamUsername().equals(teamParticipant.getUsername())){
            setLeaderButton.setDisable(true);
            leaderImageView.setVisible(true);
        }
        if (!team.getTeamOwnerUsername().equals(currentUsername)){
            setLeaderButton.setDisable(true);
        }
    }

    @FXML
    public void handleRemoveParticipantButton(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Participant");
        alert.setHeaderText("Are you sure to remove this participant?");
        alert.setContentText("This action cannot be undone.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if (currentUsername.equals(teamParticipant.getUsername())){
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("You cannot remove yourself from the team.");
                alert2.showAndWait();
                return;
            }
            teamParticipantList.getTeamParticipants().remove(teamParticipant);
            teamParticipantListDatasource.writeData(teamParticipantList);
            if (teamParticipantList.getTeamParticipantCountByEventAndTeamName(teamParticipant.getEventName(), teamParticipant.getTeamName()) == 0 || teamParticipant.getUsername().equals(team.getHeadOfTeamUsername())){
                team.setHeadOfTeamUsername(currentUsername);
                teamListDatasource.writeData(teamList);
            }
            try {
                FXRouterPane.goTo("team-management", new String[]{teamParticipant.getEventName(), teamParticipant.getTeamName(), currentUsername});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    public void handleSetLeaderButton(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Set Leader");
        alert.setHeaderText("Are you sure to set this participant as leader?");
        alert.setContentText("This action cannot be undone.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            team.setHeadOfTeamUsername(teamParticipant.getUsername());
            teamListDatasource.writeData(teamList);
            try {
                FXRouterPane.goTo("team-management", new String[]{teamParticipant.getEventName(), teamParticipant.getTeamName(), currentUsername});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }






}
