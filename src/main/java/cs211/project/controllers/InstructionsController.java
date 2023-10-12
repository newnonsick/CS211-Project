package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InstructionsController extends Application {

    @FXML
    private Text instructionText;
    @FXML Hyperlink hyperlink;

    @FXML
    public void initialize() {
        HostServices hostServices = getHostServices();
        try {
            Path path = Paths.get("data" + File.separator + "instruction.txt");
            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            instructionText.setText(content);
        } catch (IOException e) {
            instructionText.setText("Error reading the instruction file.");
        }
        hyperlink.setOnAction(e -> {
            //รอเอา link มาใส่
            hostServices.showDocument("");
        });

    }

    @FXML
    private void goToLogin() {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        ;
    }
}
