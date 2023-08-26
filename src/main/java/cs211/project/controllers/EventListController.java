package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import cs211.project.controllers.EventElementController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class EventListController {
    private static String[] SUGGESTIONS_ARRAY = {
            "New", "Tarn", "Ice", "Nutt"
    };
    @FXML
    TableView eventListTableView;
    @FXML
    TextField searchTextField;
    @FXML
    Button searchButton;
    @FXML
    GridPane eventGrid;

    public void initialize() {
        showList();
        searchTextField.setOnKeyReleased(this::handleAutoComplete);
        Arrays.sort(SUGGESTIONS_ARRAY); //Array must be sorted
    }
    //Auto completion
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

    public void showList() {
        File file = new File("data/eventList.csv");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );
        BufferedReader buffer = new BufferedReader(inputStreamReader);
        int row = 0;
        int column = 0;
        String line = "";
        try {
            while ( (line = buffer.readLine()) != null ){
                if (line.isEmpty()) continue;
                String[] data = line.split(",");
                if(column == 3) {
                    row++;
                    column = 0;
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/eventElement.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                EventElementController event = fxmlLoader.getController();
                event.setPage(data[0], data[1].trim());
                eventGrid.add(anchorPane, column, row);
                column++;

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
