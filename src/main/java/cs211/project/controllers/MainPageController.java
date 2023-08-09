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
            FXRouterPane.goTo("event-list");
        } catch (IOException e) {
        throw new RuntimeException(e);
        }
    }
    public static void configRoute()
    {
        String viewPath = "cs211/project/views/";
        FXRouterPane.when("mainPage", viewPath + "mainPage.fxml");
        FXRouterPane.when("event-management", viewPath + "event-management.fxml");
        FXRouterPane.when("event-list", viewPath + "event-list.fxml");
        FXRouterPane.when("event-participant-management", viewPath + "event-participant-management.fxml");
        FXRouterPane.when("user-information", viewPath + "user-information.fxml");
        FXRouterPane.when("create-event", viewPath + "create-event.fxml");
        FXRouterPane.when("event-information", viewPath + "event-information.fxml");
        FXRouterPane.when("your-created-events", viewPath + "your-created-events.fxml");
    }
    @FXML
    public void goToEventList()  {
        try {
            FXRouterPane.goTo("event-list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToCreateEvent()  {
        try {
            FXRouterPane.goTo("create-event");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToYourCreatedEvents()  {
        try {
            FXRouterPane.goTo("your-created-events");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToUserInformation()  {
        try {
            FXRouterPane.goTo("user-information");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}