package cs211.project.controllers;

import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class TestPage2Controller {
    @FXML
    Label textLabel;
    @FXML
    public void initialize() {
        String text = (String) FXRouterPane.getData();
        textLabel.setText(text);
    }
    @FXML
    public void goToTestPage1() {
        try {
            FXRouterPane.goTo("testpage1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
