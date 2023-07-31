package cs211.project.cs211661project;

import cs211.project.services.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class EventManagerApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Event Manager");
        configRoute();
        FXRouter.goTo("register");
    }

    public static void configRoute()
    {
        String viewPath = "cs211/project/views/";
        FXRouter.when("register", viewPath + "register.fxml");

    }

    public static void main(String[] args) {
        launch();
    }
}
