package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class EventListController {
    @FXML
    TableView eventListTableView;
    @FXML
    TextField searchTextField;
    @FXML
    Button searchButton;

    public void initialize() {
        searchTextField.setOnKeyReleased(this::handleAutoComplete);
        Arrays.sort(SUGGESTIONS_ARRAY); //Array must be sorted
    }

    private void handleAutoComplete(KeyEvent event) {
        String enteredText = searchTextField.getText();
        if (event.getCode() == KeyCode.BACK_SPACE || enteredText.isEmpty() ) {
            return;
        }

        String matchedSuggestion = null;
        int totalMatched = 0;
        for (String suggestion : SUGGESTIONS_ARRAY) {
            if (suggestion.toLowerCase().startsWith(enteredText.toLowerCase())) {
                matchedSuggestion = suggestion;
                break;
            }
         }

        if (matchedSuggestion != null && !matchedSuggestion.equals(enteredText)) {
            searchTextField.setText(matchedSuggestion);
            searchTextField.selectRange(enteredText.length(), matchedSuggestion.length());
        }

        if (totalMatched == 0){
            for (String suggestion : SUGGESTIONS_ARRAY) {
                if (suggestion.toLowerCase().contains(enteredText.toLowerCase())) {
                    if (totalMatched > 1){
                        break;
                    }
                    matchedSuggestion = suggestion;
                    totalMatched++;
                }
            }

            if (totalMatched == 1 && matchedSuggestion != null && !matchedSuggestion.equals(enteredText) && matchedSuggestion.indexOf(enteredText) != matchedSuggestion.length()-1) {
                searchTextField.setText(matchedSuggestion);
//                searchTextField.selectRange(matchedSuggestion.lastIndexOf(enteredText.charAt(enteredText.length() - 1)) + 1 ,matchedSuggestion.length());
                searchTextField.selectRange(matchedSuggestion.length() ,matchedSuggestion.length());
            }
        }


    }
    
    private static String[] SUGGESTIONS_ARRAY = {
            "Newa", "Tarn", "Ice", "Nutt"
    };
}
