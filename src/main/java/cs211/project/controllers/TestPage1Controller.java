package cs211.project.controllers;

import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class TestPage1Controller {
    @FXML
    TextField sendToPage2TextField;
    @FXML
    public void goToTestPage2() {
        try {
            String text = sendToPage2TextField.getText();
            FXRouterPane.goTo("testpage2", text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
