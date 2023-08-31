package cs211.project.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Activity {
    private String activityName;
    private String activityInformation;
    private boolean isActivityAvailable;
    private LocalTime activityStartTime;
    private LocalTime activityEndTime;

    public Activity(String activityName, String activityInformation) {
        this.activityName = activityName;
        this.activityInformation = activityInformation;
        this.isActivityAvailable = true;
    }

    public Activity(String activityName, String activityInformation, boolean isActivityAvailable, LocalTime activityStartTime, LocalTime activityEndTime) {
        this.activityName = activityName;
        this.activityInformation = activityInformation;
        this.isActivityAvailable = isActivityAvailable;
        this.activityStartTime = activityStartTime;
        this.activityEndTime = activityEndTime;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityInformation() {
        return activityInformation;
    }

    public boolean isTeamAvailable() {
        return isActivityAvailable;
    }

    public void setTeamAvailable(boolean isTeamAvailable) {
        this.isActivityAvailable = isTeamAvailable;
    }
}
