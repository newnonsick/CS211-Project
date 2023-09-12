package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.services.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.File;
import java.io.IOException;

public class TeamCommunicationController {
    @FXML Label avtivityNameLabel;
    @FXML Label activityDescriptionLabel;
    @FXML Label teamNameLabel;
    @FXML TableView activityTableView;
    @FXML TextField sendMessageTextField;
    @FXML Button manageTeamButton;
    @FXML VBox chatBoxVBox;
    @FXML ScrollPane chatBoxScrollPane;

    private TeamChatList teamChatList;
    private Team team;
    private Datasource<TeamChatList> teamChatListDatasource;
    private Datasource<ActivityList> activityListDatasource;
    private ActivityList activityList;
    @FXML
    public void initialize(){
        team = (Team) FXRouterPane.getData();
        manageTeamButton.setVisible(false);
        if (team.getHeadOfTeamUsername().equals(CurrentUser.getUser().getUsername())){
            manageTeamButton.setVisible(true);
        }
        teamNameLabel.setText(team.getTeamName());
        avtivityNameLabel.setText("");
        activityDescriptionLabel.setText("");
        teamChatListDatasource = new TeamChatListFileDatasource("data", "team_chat_list.csv");
        teamChatList = teamChatListDatasource.readData();
        activityListDatasource = new TeamActivityListFileDatasource("data", "team_activity_list.csv");
        activityList = activityListDatasource.readData();
        showActivity(activityList);
        newShowChat();

        activityTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Activity>() {
            @Override
            public void changed(ObservableValue observable, Activity oldValue, Activity newValue) {
                if (newValue != null) {
                    if (newValue.getActivityStatus().equals("Ended")){
                        avtivityNameLabel.setText("");
                        activityDescriptionLabel.setText("");
                        return;
                    }
                    avtivityNameLabel.setText(newValue.getActivityName());
                    activityDescriptionLabel.setText(newValue.getActivityInformation());
                }
                else{
                    avtivityNameLabel.setText("");
                    activityDescriptionLabel.setText("");
                }
            }
        });
    }

    @FXML
    public void handleSendMessageButton() {
        String text = sendMessageTextField.getText();
        if (!text.isEmpty()){
            sendMessageTextField.clear();
            teamChatList.addNewChat(team.getEventOfTeamName(), team.getTeamName(), CurrentUser.getUser().getUsername(), text);
            teamChatListDatasource.writeData(teamChatList);
            update(CurrentUser.getUser().getUsername(),text);
        }
        Platform.runLater(() -> chatBoxScrollPane.setVvalue(1.0));
    }

    @FXML
    public void handleManageTeamButton(){
        try {
            FXRouterPane.goTo("team-management", team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void newShowChat(){
        chatBoxVBox.getChildren().clear();
        for (TeamChat teamChat : teamChatList.getTeamChats()) {
            if (!teamChat.getEventName().equals(team.getEventOfTeamName()) || !teamChat.getTeamName().equals(team.getTeamName())) {
                continue;
            }
            update(teamChat.getUsername(),teamChat.getMessage());
        }
    }
    public void update(String username,String message){
        Text text = new Text(message);
        text.getStyleClass().add("message");
        TextFlow flow = new TextFlow();
        Circle img = new Circle(32,32,16);

        if (!CurrentUser.getUser().getUsername().equals(username)){
            Text usernameText = new Text(username + "\n");
            usernameText.getStyleClass().add("usernameText");
            flow.getChildren().add(usernameText);

            String path = new File("data/profile_picture/default.png").toURI().toString();
            img.setFill(new ImagePattern(new Image(path)));
        }

        flow.getChildren().add(text);
        flow.setMaxWidth(280);

        HBox hbox = new HBox(20);

        if(!CurrentUser.getUser().getUsername().equals(username)){
            flow.getStyleClass().add("textFlowFlipped");
            hbox.setAlignment(Pos.TOP_LEFT);
            hbox.getChildren().add(img);
            hbox.getChildren().add(flow);

        }else{
            Pane pane = new Pane();
            flow.getStyleClass().add("textFlow");
            hbox.setAlignment(Pos.TOP_RIGHT);
            hbox.getChildren().add(flow);
            hbox.getChildren().add(pane);
        }

        hbox.getStyleClass().add("hbox");
        chatBoxVBox.getChildren().addAll(hbox);
        Platform.runLater(() -> {
            chatBoxScrollPane.setVvalue(1.0);
            chatBoxScrollPane.setVvalue(1.0);
            chatBoxScrollPane.setVvalue(1.0);
            chatBoxScrollPane.setVvalue(1.0);
            chatBoxScrollPane.setVvalue(1.0);
        });
    }




    public void showActivity(ActivityList activityList){
        TableColumn<Activity, String> activityNameColumn = new TableColumn<>("Name");
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));

        TableColumn<Activity, String> activityInformationColumn = new TableColumn<>("Description");
        activityInformationColumn.setCellValueFactory(new PropertyValueFactory<>("activityInformation"));

        TableColumn<Activity, String> activityStatusColumn = new TableColumn<>("Status");
        activityStatusColumn.setCellValueFactory(new PropertyValueFactory<>("activityStatus"));

        activityTableView.getColumns().clear();
        activityTableView.getColumns().add(activityStatusColumn);
        activityTableView.getColumns().add(activityNameColumn);
        activityTableView.getColumns().add(activityInformationColumn);
        activityTableView.getItems().clear();

        if (activityList.getActivities().size() == 0){
            activityInformationColumn.setPrefWidth(activityTableView.getPrefWidth() - activityNameColumn.getPrefWidth() - activityStatusColumn.getPrefWidth());
            return;
        }


        for (Activity activity : activityList.getActivities()) {
            if (activity.getTeamOfActivityName().equals(team.getTeamName()) && activity.getEventOfActivityName().equals(team.getEventOfTeamName())) {
                activityTableView.getItems().add(activity);
            }
        }
    }

}
