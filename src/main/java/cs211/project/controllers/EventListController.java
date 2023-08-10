package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EventListController {
    @FXML
    TableView eventListTableView;
    @FXML
    TextField searchTextField;
    @FXML
    Button searchButton;

    public void initialize() {
        searchTextField.setOnKeyReleased(this::handleAutoComplete);
    }

    private void handleAutoComplete(KeyEvent event) {
        String enteredText = searchTextField.getText();
        if (event.getCode() == KeyCode.BACK_SPACE || enteredText.isEmpty()) {
            return;
        }

        String matchedSuggestion = null;
        int totalMatched = 0;
        for (String suggestion : SUGGESTIONS_ARRAY) {
            if (suggestion.toLowerCase().startsWith(enteredText.toLowerCase())) {
                matchedSuggestion = suggestion;
                totalMatched++;
                if (totalMatched > 1) {
                    break;
                }
            }
        }

        if (totalMatched == 1 && matchedSuggestion != null && !matchedSuggestion.equals(enteredText)) {
            searchTextField.setText(matchedSuggestion);
            searchTextField.selectRange(enteredText.length(), matchedSuggestion.length());
        }
    }

    private static final String[] SUGGESTIONS_ARRAY = {
            "New", "Tarn", "Ice", "Nutt"
    };
}
