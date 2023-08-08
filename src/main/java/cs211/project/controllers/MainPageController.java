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
        FXRouterPane.when("event-management", viewPath + "event-management.fxml");
        FXRouterPane.when("event-participant-management", viewPath + "event-participant-management.fxml");
        FXRouter.when("user-information", viewPath + "user-information.fxml");
        FXRouter.when("create-event", viewPath + "create-event.fxml");
    }
    @FXML
    public void goToEventManagement()  {
        try {
            FXRouterPane.goTo("event-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}