package cs211.project.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Activity {
    private String eventOfActivityName;
    private String teamOfActivityName;
    private String activityName;
    private String activityInformation;
    private String activityStatus;
    private LocalTime activityStartTime;
    private LocalTime activityEndTime;

    public Activity(String eventOfActivityName, String teamOfActivityName, String activityName, String activityInformation, String activityStatus) {
        this.eventOfActivityName = eventOfActivityName;
        this.teamOfActivityName = teamOfActivityName;
        this.activityName = activityName;
        this.activityInformation = activityInformation;
        this.activityStatus = activityStatus;
    }

    public Activity(String activityName, String activityInformation, LocalTime activityStartTime, LocalTime activityEndTime) {
        this.activityName = activityName;
        this.activityInformation = activityInformation;
        this.activityStartTime = activityStartTime;
        this.activityEndTime = activityEndTime;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityInformation() {
        return activityInformation;
    }

    public String getEventOfActivityName() {
        return eventOfActivityName;
    }

    public String getTeamOfActivityName() {
        return teamOfActivityName;
    }


    public LocalTime getActivityEndTime() {
        return activityEndTime;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public LocalTime getActivityStartTime() {
        return activityStartTime;
    }


    public LocalTime activityEndTime(){
        return activityEndTime;
    }

    public boolean isActivity(Activity activity) {
        if (activity.getActivityName().equals(this.getActivityName()) && activity.getEventOfActivityName().equals(this.getEventOfActivityName()) && activity.getTeamOfActivityName().equals(this.getTeamOfActivityName())) {
            return true;
        }
        return false;
    }
}
