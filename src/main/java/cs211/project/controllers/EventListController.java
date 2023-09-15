package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouterPane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.*;
import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class EventListController {
    private String[] eventList;
    private int maxRow = 4;
    private Datasource<EventList> datasource;
    private EventList eventListData;
    private LocalDate currentDate;
    private boolean isSearch;
    @FXML
    Pane categoryPane;
    @FXML
    TextField searchTextField;
    @FXML
    Button searchButton;
    @FXML
    GridPane eventGrid;
    @FXML
    ScrollPane eventScrollPane;
    @FXML
    Label logLabel;
    boolean categoryOn = false;

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

                if (currentVvalue >= maxVvalue * 0.95 && !isSearch) {
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
        eventScrollPane.setVvalue(0);
        if (!searchTextField.getText().isEmpty()) {
            eventGrid.getChildren().clear();
            showList(searchTextField.getText());
            isSearch = true;
        } else if (isSearch){
            maxRow = 4;
            eventScrollPane.setVvalue(0);
            eventGrid.getChildren().clear();
            showList();
            isSearch = false;
        }
        searchTextField.clear();
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
            if (event.getEventEndDate().isBefore(currentDate)) {
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
            event_.setPage(event.getEventName(), event.getEventPicture(), event.getEventCategory());
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
            event_.setPage(event.getEventName(), event.getEventPicture(), event.getEventCategory());
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
    @FXML
    public void categoryPaneOpen() {
        Timeline timeline = new Timeline();
        if(!categoryOn) {
            double targetHeight = 50;

            KeyValue keyValue = new KeyValue(categoryPane.prefHeightProperty(), targetHeight);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.3), keyValue);

            timeline.getKeyFrames().add(keyFrame);
            categoryPane.setVisible(true);
            timeline.play();
            categoryOn = true;
        }
        else {
            double targetHeight = 0;

            KeyValue keyValue = new KeyValue(categoryPane.prefHeightProperty(), targetHeight);
            KeyValue keyValue2 = new KeyValue(categoryPane.visibleProperty(), false);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.3), keyValue);
            KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.3), keyValue2);

            timeline.getKeyFrames().add(keyFrame);
            timeline.getKeyFrames().add(keyFrame2);
            timeline.play();

            categoryPane.setVisible(false);
            categoryOn = false;
        }

    }
    @FXML
    public void searchByCategoryOne() {searchByCategory("งานแสดงสินค้า");}
    @FXML
    public void searchByCategoryTwo() {
        searchByCategory("เทศกาล");
    }
    @FXML
    public void searchByCategoryThree() {
        searchByCategory("อบรมสัมนา");
    }
    @FXML
    public void searchByCategoryFour() {
        searchByCategory("บ้านและของแต่งบ้าน");
    }
    @FXML
    public void searchByCategoryFive() {
        searchByCategory("อาหารและเครื่องดื่ม");
    }
    @FXML
    public void searchByCategorySix() {
        searchByCategory("บันเทิง");
    }
    @FXML
    public void searchByCategorySeven() {
        searchByCategory("คอนเสิร์ต/แฟนมีตติ้ง");
    }
    @FXML
    public void searchByCategoryEight() {
        searchByCategory("ท่องเที่ยว");
    }
    @FXML
    public void searchByCategoryNine() {
        searchByCategory("ศิลปะ/นิทรรศการ/ถ่ายภาพ");
    }
    @FXML
    public void searchByCategoryTen() {
        searchByCategory("กีฬา");
    }
    @FXML
    public void searchByCategoryEleven() {
        searchByCategory("ศาสนา");
    }
    @FXML
    public void searchByCategoryTwelve() {
        searchByCategory("สัตว์เลี้ยง");
    }
    @FXML
    public void searchByCategoryThirteen() {
        searchByCategory("ธุรกิจ/อาชีพ/การศึกษา");
    }
    @FXML
    public void searchByCategoryFourteen() {
        searchByCategory("อื่น ๆ");
    }

    public void searchByCategory(String category) {
        eventGrid.getChildren().clear();
        int row = 0;
        int column = 0;
        for (Event event : eventListData.getEvents()) {
            if (!event.getEventCategory().equals(category)) {
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
            event_.setPage(event.getEventName(), event.getEventPicture(), event.getEventCategory());
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
