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
import java.util.concurrent.TimeUnit;

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
    private Datasource<TeamList> teamListDatasource;
    private TeamList teamList;
    private Datasource<TeamChatList> teamChatListDatasource;
    private Datasource<ActivityList> activityListDatasource;
    private ActivityList activityList;
    private String beforeSend = "";
    private String [] componentData;
    private String eventOfTeamName;
    private String teamName;
    private String currentUserName;
    private Datasource<UserList> userListDatasource;
    private UserList userList;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;

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
        eventOfTeamName = componentData[0];
        teamName = componentData[1];
        currentUserName = componentData[2];
        team = teamList.findTeamByNameAndEvent(eventOfTeamName, teamName);
        manageTeamButton.setVisible(false);
        if (team.getHeadOfTeamUsername().equals(currentUserName) || team.getTeamOwnerUsername().equals(currentUserName)){
            manageTeamButton.setVisible(true);
        }
        teamNameLabel.setText(team.getTeamName());
        avtivityNameLabel.setText("");
        activityDescriptionLabel.setText("");
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
            teamChatList.addNewChat(team.getEventOfTeamName(), team.getTeamName(), currentUserName, text.replace(",", "//comma//"));
            teamChatListDatasource.writeData(teamChatList);
            update(currentUserName,text);
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
            FXRouterPane.goTo("team-management", new String[] {team.getEventOfTeamName(), team.getTeamName(), currentUserName});
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
    public void update(String username,String message){
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
        if (!currentUserName.equals(username)){
            if (team.getHeadOfTeamUsername().equals(username)){
                usernameText = new Text(username + " (Leader)" + "\n");
            }
            else{
                usernameText = new Text(username + "\n");
            }
            usernameText.getStyleClass().add("usernameText");
            messageVBox.getChildren().add(usernameText);

            String path = new File("data/profile_picture/" + user.getProfilePicture()).toURI().toString();
            img.setFill(new ImagePattern(new Image(path, 32, 32, false, false)));

        }

        flow.getChildren().add(text);
        flow.setMaxWidth(280);
        messageVBox.getChildren().add(flow);

        HBox hBoxMessage = new HBox(10);

        Pane pane = new Pane();
        if(!currentUserName.equals(username)){
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
