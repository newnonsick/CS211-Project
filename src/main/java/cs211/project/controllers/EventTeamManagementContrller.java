package cs211.project.controllers;

import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.io.IOException;

public class EventTeamManagementContrller {
    @FXML TableView teamListTableView;
    @FXML Label eventNameLabel;


    @FXML
    public void handleCreateTeamButton(){
        try {
            FXRouterPane.goTo("create-team");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //ทำปุ่มไว้ส่งงาน ตอนจริงใช้คลิกใน TableView
    @FXML
    public void handleManageTeamButton(){
        try {
            FXRouterPane.goTo("team-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
