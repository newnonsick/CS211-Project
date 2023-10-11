package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InstructionsController {

    @FXML
    private Label instructionLabel;

    @FXML
    public void initialize() {
        try {
            Path path = Paths.get("data/instruction.txt");
            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            instructionLabel.setText(content);
        } catch (IOException e) {
            instructionLabel.setText("Error reading the instruction file.");
        }
    }

    @FXML
    private void goToLogin() {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
