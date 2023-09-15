package cs211.project.controllers;
import cs211.project.services.FXRouter;
import cs211.project.services.FXRouterPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {
    @FXML
    BorderPane window;
    @FXML
    StackPane content;
    @FXML
    Button eventButton;
    @FXML
    Button createEventButton;
    @FXML
    Button myEventButton;
    @FXML
    Button myTeamButton;
    @FXML
    Button userInfoButton;



    @FXML
    public void initialize() {
        changeStyleClassButton(eventButton);
        FXRouterPane.bind(this, content, "Event Manager");
        configRoute();
        try {
            FXRouterPane.goTo("event-list","First Run");
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
        FXRouterPane.when("my-events", viewPath + "my-events.fxml");
        FXRouterPane.when("event-team-management", viewPath + "event-team-management.fxml");
        FXRouterPane.when("create-team", viewPath + "create-team.fxml");
        FXRouterPane.when("teamofevent-list", viewPath + "teamofevent-list.fxml");
        FXRouterPane.when("myteam-list", viewPath + "myteam-list.fxml");
        FXRouterPane.when("team-communication", viewPath + "team-communication.fxml");
        FXRouterPane.when("team-management", viewPath + "team-management.fxml");
        FXRouterPane.when("edit-participant", viewPath + "edit-participant.fxml");
    }
    @FXML
    public void goToEventList()  {
        try {
            changeStyleClassButton(eventButton);
            FXRouterPane.goTo("event-list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToCreateEvent()  {
        try {
            changeStyleClassButton(createEventButton);
            FXRouterPane.goTo("create-event");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToYourCreatedEvents()  {
        try {
            changeStyleClassButton(myEventButton);
            FXRouterPane.goTo("your-created-events");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToUserInformation()  {
        try {
            changeStyleClassButton(userInfoButton);
            FXRouterPane.goTo("user-information");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleMyTeamButton(){
        try {
            changeStyleClassButton(myTeamButton);
            FXRouterPane.goTo("myteam-list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeStyleClassButton(Button button){
        eventButton.getStyleClass().remove("selected_navigation-button");
        createEventButton.getStyleClass().remove("selected_navigation-button");
        myEventButton.getStyleClass().remove("selected_navigation-button");
        myTeamButton.getStyleClass().remove("selected_navigation-button");
        userInfoButton.getStyleClass().remove("selected_navigation-button");
        button.getStyleClass().add("selected_navigation-button");
    }
}