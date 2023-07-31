package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainPageController {
    @FXML
    BorderPane window;
    @FXML
    StackPane content;
    @FXML
    public void goToTestPage1() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/cs211/project/views/testpage1.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(fxml);
    }

    @FXML
    public void goToTestPage2() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/cs211/project/views/testpage2.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(fxml);
    }
}
