package cs211.project.controllers;
import cs211.project.services.FXRouter;
import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainPageController {
    @FXML
    BorderPane window;
    @FXML
    StackPane content;
    @FXML
    public void initialize() {
        FXRouterPane.bind(this, content, "Event Manager");
        configRoute();
        try {
            FXRouterPane.goTo("event-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void configRoute()
    {
        String viewPath = "cs211/project/views/";
        FXRouterPane.when("mainPage", viewPath + "mainPage.fxml");
        FXRouterPane.when("testpage1", viewPath + "testpage1.fxml");
        FXRouterPane.when("testpage2", viewPath + "testpage2.fxml");
        FXRouterPane.when("event-management", viewPath + "event-management.fxml");
        FXRouterPane.when("event-participant-management", viewPath + "event-participant-management.fxml");
    }
    @FXML
    public void goToTestPage1()  {
        try {
            FXRouterPane.goTo("testpage1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToTestPage2() {
        try {
            FXRouterPane.goTo("testpage2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}