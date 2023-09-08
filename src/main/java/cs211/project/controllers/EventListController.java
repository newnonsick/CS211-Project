package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouter;
import cs211.project.services.FXRouterPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import cs211.project.controllers.EventElementController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class EventListController {
    private String[] eventList;
    private int maxRow = 4;
    private Datasource<EventList> datasource;
    private EventList eventListData;
    private LocalDate currentDate;
    private boolean isSearch;

    @FXML
    TextField searchTextField;
    @FXML
    Button searchButton;
    @FXML
    GridPane eventGrid;
    @FXML
    ScrollPane eventScrollPane;

    public void initialize() {
        isSearch = false;
        ZoneId thaiTimeZone = ZoneId.of("Asia/Bangkok");
        currentDate = LocalDate.now(thaiTimeZone);
        datasource = new EventListFileDatasource("data", "eventList.csv");
        eventListData = datasource.readData();
        searchTextField.setOnKeyReleased(this::handleAutoComplete);
        eventList = eventListData.getEvents().stream()
                .map(Event::getEventName)
                .toArray(String[]::new);
        Arrays.sort(eventList);
        showList();
        eventScrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Calculate the maximum Vvalue (maximum scroll position)
                double maxVvalue = eventScrollPane.getVmax();

                // Calculate the current Vvalue (current scroll position)
                double currentVvalue = newValue.doubleValue();

                if (currentVvalue >= maxVvalue && !isSearch) {
                    if (maxRow > (int) Math.round(eventListData.getEvents().size() / 3.0 )) {
                        maxRow = (int) Math.round(eventListData.getEvents().size() / 3.0);
                    }
                    else if (maxRow < (int) Math.round(eventListData.getEvents().size() / 3.0 )){
                        maxRow += 4;
                        showList();
                    }
                }
            }
        });
    }

    @FXML
    public void handleSearchButton(){
        searchTextField.clear();
        eventGrid.getChildren().clear();
        eventScrollPane.setVvalue(0);
        if (!searchTextField.getText().isEmpty()) {
            showList(searchTextField.getText());
            isSearch = true;
        } else {
            eventScrollPane.setVvalue(0);
            showList();
            isSearch = false;
        }
    }

    //Auto completion
    private void handleAutoComplete(KeyEvent event) {
        String enteredText = searchTextField.getText();
        if (event.getCode() == KeyCode.BACK_SPACE || enteredText.isEmpty() ) {
            return;
        }

        String matchedSuggestion = null;
        int totalMatched = 0;
        for (String suggestion : eventList) {
            if (suggestion.toLowerCase().startsWith(enteredText.toLowerCase())) {
                matchedSuggestion = suggestion;
                break;
            }
         }

        if (matchedSuggestion != null && !matchedSuggestion.equals(enteredText)) {
            searchTextField.setText(matchedSuggestion);
            searchTextField.selectRange(enteredText.length(), matchedSuggestion.length());
        }


        for (String suggestion : eventList) {
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

    public void showList() {
        int row = maxRow - 4;
        int column = 0;
        int i = 0;
        for (Event event : eventListData.getEvents()) {
            if (i < (maxRow - 4) * 3) {
                i++;
                continue;
            }
            long daysBetween = ChronoUnit.DAYS.between(currentDate, event.getEventEndDate());
            if (daysBetween < 0) {
                continue;
            }
            if(column == 3) {
                row++;
                column = 0;
            }
            if (row == maxRow) {
                break;
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/eventElement.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            EventElementController event_ = fxmlLoader.getController();
            event_.setPage(event.getEventName(), event.getEventPicture());
            anchorPane.setOnMouseClicked(event1 -> {
                try {
                    FXRouterPane.goTo("event-information", event.getEventName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            eventGrid.add(anchorPane, column, row);
            column++;

            GridPane.setMargin(anchorPane, new Insets(10));
        }

    }

    public void showList(String eventName) {
        int row = 0;
        int column = 0;
        for (Event event : eventListData.getEvents()) {
            if (!event.getEventName().toLowerCase().contains(eventName.toLowerCase())) {
                continue;
            }
            if(column == 3) {
                row++;
                column = 0;
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/eventElement.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            EventElementController event_ = fxmlLoader.getController();
            event_.setPage(event.getEventName(), event.getEventPicture());
            anchorPane.setOnMouseClicked(event1 -> {
                try {
                    FXRouterPane.goTo("event-information", event.getEventName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            eventGrid.add(anchorPane, column, row);
            column++;

            GridPane.setMargin(anchorPane, new Insets(10));
        }
    }


}
