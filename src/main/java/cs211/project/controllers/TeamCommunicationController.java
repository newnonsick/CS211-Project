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
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class TeamCommunicationController {
    @FXML Label activityNameLabel;
    @FXML Label activityDescriptionLabel;
    @FXML Label teamNameLabel;
    @FXML TableView activityTableView;
    @FXML TextField sendMessageTextField;
    @FXML Button manageTeamButton;
    @FXML VBox chatBoxVBox;
    @FXML ScrollPane chatBoxScrollPane;
    @FXML Button sendMessageButton;
    @FXML TableColumn activityNameColumn;
    @FXML TableColumn activityDescriptionColumn;
    @FXML TableColumn activityStatusColumn;

    private TeamChatList teamChatList;
    private Team team;
    private Datasource<TeamList> teamListDatasource;
    private TeamList teamList;
    private Datasource<TeamChatList> teamChatListDatasource;
    private Datasource<ActivityList> activityListDatasource;
    private ActivityList activityList;
    private String beforeSend = "";
    private String [] componentData;
    private String eventUUID;
    private String teamName;
    private String currentUsername;
    private Datasource<UserList> userListDatasource;
    private UserList userList;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private Activity selectedActivity;

    @FXML
    public void initialize(){
        eventListDatasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = eventListDatasource.readData();
        userListDatasource = new UserListFileDataSource("data", "userData.csv");
        userList = userListDatasource.readData();
        teamListDatasource = new TeamListFileDatasource("data", "team_list.csv");
        teamList = teamListDatasource.readData();
        teamChatListDatasource = new TeamChatListFileDatasource("data", "team_chat_list.csv");
        teamChatList = teamChatListDatasource.readData();
        activityListDatasource = new TeamActivityListFileDatasource("data", "team_activity_list.csv");
        activityList = activityListDatasource.readData();
        componentData = (String[]) FXRouterPane.getData();
        eventUUID = componentData[0];
        teamName = componentData[1];
        currentUsername = componentData[2];
        team = teamList.findEventByEventUUIDAndTeamName(eventUUID, teamName);
        manageTeamButton.setVisible(false);
        if (team.getHeadOfTeamUsername().equals(currentUsername) || team.getTeamOwnerUsername().equals(currentUsername)){
            manageTeamButton.setVisible(true);
        }
        teamNameLabel.setText(team.getTeamName());
        activityNameLabel.setText("");
        activityDescriptionLabel.setText("");
        sendMessageButton.setDisable(true);
        sendMessageTextField.setDisable(true);
        showActivity(activityList);
        showChat();

        activityTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Activity>() {
            @Override
            public void changed(ObservableValue observable, Activity oldValue, Activity newValue) {
                selectedActivity = newValue;
                sendMessageTextField.clear();
                showChat();
                if (newValue != null) {
                    sendMessageButton.setDisable(false);
                    sendMessageTextField.setDisable(false);
                    if (newValue.getActivityStatus().equals("Ended")){
                        activityNameLabel.setText("");
                        activityDescriptionLabel.setText("");
                        return;
                    }
                    activityNameLabel.setText(newValue.getActivityName());
                    activityDescriptionLabel.setText(newValue.getActivityInformation());
                }
                else{
                    sendMessageButton.setDisable(true);
                    sendMessageTextField.setDisable(true);
                    activityNameLabel.setText("");
                    activityDescriptionLabel.setText("");
                }
            }
        });
    }

    @FXML
    public void handleSendMessageButton() {
        String text = sendMessageTextField.getText().trim();
        sendMessageTextField.clear();
        if (!text.isEmpty()){
            teamChatList.addNewChat(team.getEventUUID(), team.getTeamName(), currentUsername, text.replace(",", "//comma//"), selectedActivity.getActivityUUID());
            teamChatListDatasource.writeData(teamChatList);
            update(currentUsername,text);
        }
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Platform.runLater(() -> {
            chatBoxScrollPane.setVvalue(1.0);
        });
    }

    @FXML
    public void handleManageTeamButton(){
        try {
            FXRouterPane.goTo("team-management", new String[] {team.getEventUUID(), team.getTeamName(), currentUsername});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void showChat(){
        chatBoxVBox.getChildren().clear();
        if (selectedActivity == null){
            return;
        }
        for (TeamChat teamChat : teamChatList.getTeamChats()) {
            if (!teamChat.getEventUUID().equals(team.getEventUUID()) || !teamChat.getTeamName().equals(team.getTeamName()) || !teamChat.getActivityUUID().equals(selectedActivity.getActivityUUID())){
                continue;
            }
            update(teamChat.getUsername(),teamChat.getMessage().replace("//comma//", ","));
        }
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Platform.runLater(() -> {
            chatBoxScrollPane.setVvalue(1.0);
        });
    }
    public void update(String username, String message){
        User user = userList.findUserByUsername(username);
        if (user == null){
            return;
        }
        chatBoxVBox.setSpacing(-5);
        Text text = new Text(message);
        text.getStyleClass().add("message");
        TextFlow flow = new TextFlow();
        Circle img = new Circle(32,32,16);

        VBox messageVBox = new VBox(-5);
        messageVBox.setAlignment(Pos.TOP_LEFT);
        Text usernameText = new Text();
        if (!currentUsername.equals(username)){
            if (team.getTeamOwnerUsername().equals(username)){
                usernameText = new Text(username + " (Owner)" + "\n");
            }
            else if (team.getHeadOfTeamUsername().equals(username)){
                usernameText = new Text(username + " (Leader)" + "\n");
            }
            else{
                usernameText = new Text(username + "\n");
            }
            usernameText.getStyleClass().add("usernameText");
            messageVBox.getChildren().add(usernameText);
            String path;
            if (user.getProfilePictureName().equals("default.png")){
                path = getClass().getResource("/cs211/project/images/default.png").toExternalForm();
            }
            else{
                path = new File("data/profile_picture/" + user.getProfilePictureName()).toURI().toString();
            }
            img.setFill(new ImagePattern(new Image(path, 32, 32, false, false)));

        }

        flow.getChildren().add(text);
        flow.setMaxWidth(280);
        messageVBox.getChildren().add(flow);

        HBox hBoxMessage = new HBox(10);

        Pane pane = new Pane();
        if(!currentUsername.equals(username)){
            if (beforeSend.equals(username)){
                messageVBox.getChildren().remove(0);
                flow.getStyleClass().add("textFlowFlipped");
                hBoxMessage.setAlignment(Pos.TOP_LEFT);
                pane.setMinWidth(42);
                hBoxMessage.getChildren().add(pane);
                hBoxMessage.getChildren().add(messageVBox);
            }
            else{
                Pane pane2 = new Pane();
                pane2.setMinWidth(10);
                pane.setMaxWidth(usernameText.getLayoutBounds().getWidth());
                beforeSend = username;
                messageVBox.getChildren().remove(1);
                HBox tempHBox = new HBox();
                tempHBox.getChildren().add(pane2);
                tempHBox.getChildren().add(flow);
                tempHBox.getChildren().add(pane);
                messageVBox.getChildren().add(tempHBox);
                flow.getStyleClass().add("textFlowFlipped");
                hBoxMessage.setAlignment(Pos.TOP_LEFT);
                hBoxMessage.getChildren().add(img);
                hBoxMessage.getChildren().add(messageVBox);
            }


        }else{
            beforeSend = "";
            flow.getStyleClass().add("textFlow");
            hBoxMessage.setAlignment(Pos.TOP_RIGHT);
            hBoxMessage.getChildren().add(flow);
            hBoxMessage.getChildren().add(pane);
        }

        hBoxMessage.getStyleClass().add("hBoxMessage");

        chatBoxVBox.getChildren().addAll(hBoxMessage);
        Platform.runLater(() -> {
            chatBoxScrollPane.setVvalue(1.0);
        });
    }




    public void showActivity(ActivityList activityList){
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));

        activityDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("activityInformation"));

        activityStatusColumn.setCellValueFactory(new PropertyValueFactory<>("activityStatus"));

        activityTableView.getColumns().clear();
        activityTableView.getColumns().add(activityStatusColumn);
        activityTableView.getColumns().add(activityNameColumn);
        activityTableView.getColumns().add(activityDescriptionColumn);
        activityTableView.getItems().clear();



        for (Activity activity : activityList.getActivities()) {
            if (activity.getTeamOfActivityName().equals(team.getTeamName()) && activity.getEventOfActivityUUID().equals(team.getEventUUID())) {
                activityTableView.getItems().add(activity);
            }
        }
    }

}
