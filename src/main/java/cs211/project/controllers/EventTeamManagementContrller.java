package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouterPane;
import cs211.project.services.TeamListFileDatasource;
import cs211.project.services.TeamParticipantListFileDataSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

public class EventTeamManagementContrller {
    @FXML GridPane teamListGridPane;
    @FXML ScrollPane teamListScrollPane;
    @FXML Label eventNameLabel;

    private String eventName;
    private Datasource<TeamList> datasourceTeam;
    private LocalDate currentDate;
    private TeamList teamList;
    private String[] componentData;
    private String currentUsername;
    private Stage createTeamStage;
    private CreateTeamController createTeamController;

    @FXML
    public void initialize(){
        componentData = (String[]) FXRouterPane.getData();
        eventName = componentData[0];
        currentUsername = componentData[1];
        eventNameLabel.setText(eventName);
        ZoneId thaiTimeZone = ZoneId.of("Asia/Bangkok");
        currentDate = LocalDate.now(thaiTimeZone);
        datasourceTeam = new TeamListFileDatasource("data", "team_list.csv");
        teamList = datasourceTeam.readData();
        showTeamList();
    }

    @FXML
    public void handleCreateTeamButton(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/create-team.fxml"));
        try {
            Parent root = loader.load();
            createTeamStage = new Stage();
            createTeamStage.initStyle(StageStyle.UTILITY);
            createTeamStage.setTitle("Create Team");
            createTeamController = loader.getController();
            createTeamController.setEventName(eventName);
            createTeamController.setCreateTeamStage(createTeamStage);
            createTeamController.setCurrentUsername(currentUsername);
            Scene scene = new Scene(root);
            createTeamStage.setScene(scene);
            createTeamStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showTeamList(){
        int row = 0;
        int column = 0;
        for (Team team : teamList.getTeams()) {
            if (!team.getEventOfTeamName().equals(eventName)) {
                continue;
            }
            if (team.getClosingJoinDate().isBefore(currentDate)) {
                continue;
            }
            if(column == 2) {
                row++;
                column = 0;
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/team-element.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            TeamElementController team_ = fxmlLoader.getController();
            team_.setPage(team.getEventOfTeamName(), team.getTeamName(), team.getMaxParticipants(), team.getStartJoinDate(), team.getClosingJoinDate());
            anchorPane.setOnMouseClicked(event1 -> {
                try {
                    FXRouterPane.goTo("team-management", new String[] {team.getEventOfTeamName(), team.getTeamName(), currentUsername});
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            teamListGridPane.add(anchorPane, column, row);
            column++;

            GridPane.setMargin(anchorPane, new Insets(10));
        }

    }


}
