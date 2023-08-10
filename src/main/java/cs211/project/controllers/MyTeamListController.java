package cs211.project.controllers;

import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.io.IOException;

public class MyTeamListController {

    @FXML Label teamNameLabel;
    @FXML Label numPeopleLabel;
    @FXML TableView myTeamListTableView;

    @FXML
    public void handleViewTeamButton(){
        try {
            FXRouterPane.goTo("team-communication");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
