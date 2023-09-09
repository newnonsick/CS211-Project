package cs211.project.controllers;

import cs211.project.models.Team;
import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TeamManagementController {
    @FXML TextField avtivityNameTextField;
    @FXML TextField activityDescriptionTextField;
    @FXML TableView teamMemberTableView;
    @FXML TableView activityTableView;

    private Team team;
    @FXML
    public void initialize(){
        team = (Team) FXRouterPane.getData();
    }

    @FXML
    public void handleBanMemberButton(){
        ;
    }

    @FXML
    public void handleEndActivityButton(){
        ;
    }

    @FXML
    public void handleAddActivityButton(){
        ;
    }

    @FXML
    public void handleRemoveActivityButton(){
        ;
    }
}
