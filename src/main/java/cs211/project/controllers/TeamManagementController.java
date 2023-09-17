package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouterPane;
import cs211.project.services.TeamActivityListFileDatasource;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class TeamManagementController {
    @FXML TextField avtivityNameTextField;
    @FXML TextArea activityDescriptionTextArea;
    @FXML TableView teamMemberTableView;
    @FXML TableView activityTableView;
    @FXML Button deleteActivityButton;
    @FXML Button endActivityButton;

    private ActivityList activityList;
    private Datasource<ActivityList> activityListDatasource;
    private EventList eventList;
    private Event event;
    private Datasource<EventList> eventListDatasource;
    private Activity selectedActivity;
    private String[] componentData;
    private String currentUsername;
    private String eventOfTeamName;
    private String teamName;
    @FXML
    public void initialize(){
        deleteActivityButton.setDisable(true);
        endActivityButton.setDisable(true);
        componentData = (String[]) FXRouterPane.getData();
        eventOfTeamName = componentData[0];
        teamName = componentData[1];
        currentUsername = componentData[2];
        eventListDatasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = eventListDatasource.readData();
        event = eventList.findEventByEventName(eventOfTeamName);
        activityListDatasource = new TeamActivityListFileDatasource("data", "team_activity_list.csv");
        activityList = activityListDatasource.readData();
        showActivity(activityList);

        activityTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Activity>() {
            @Override
            public void changed(ObservableValue observable, Activity oldValue, Activity newValue) {
                if (newValue != null) {
                    deleteActivityButton.setDisable(false);
                    if (currentUsername.equals(event.getEventOwnerUsername())){
                        endActivityButton.setDisable(false);
                    }
                    selectedActivity = newValue;
                }
                else{
                    deleteActivityButton.setDisable(true);
                    endActivityButton.setDisable(true);
                }
            }
        });
    }

    @FXML
    public void handleBanMemberButton(){
        ;
    }

    @FXML
    public void handleEndActivityButton(){
        activityList.findActivityByObject(selectedActivity).setActivityStatus("Ended");
        activityListDatasource.writeData(activityList);
        showActivity(activityList);
        selectedActivity = null;
        deleteActivityButton.setDisable(true);
        endActivityButton.setDisable(true);
    }

    @FXML
    public void handleAddActivityButton(){
        String activityName = avtivityNameTextField.getText();
        String activityDescription = activityDescriptionTextArea.getText();
        if (!activityName.isEmpty() && !activityDescription.isEmpty()){
            activityList.addNewActivityTeam(eventOfTeamName, teamName, activityName, activityDescription, "In Progress");
            activityListDatasource.writeData(activityList);
            showActivity(activityList);
            avtivityNameTextField.clear();
            activityDescriptionTextArea.clear();
        }
    }

    @FXML
    public void handleRemoveActivityButton(){
        activityList.getActivities().remove(selectedActivity);
        activityListDatasource.writeData(activityList);
        showActivity(activityList);
        selectedActivity = null;
        deleteActivityButton.setDisable(true);
        endActivityButton.setDisable(true);
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
            if (activity.getTeamOfActivityName().equals(teamName) && activity.getEventOfActivityName().equals(eventOfTeamName)) {
                activityTableView.getItems().add(activity);
            }
        }
    }
}
