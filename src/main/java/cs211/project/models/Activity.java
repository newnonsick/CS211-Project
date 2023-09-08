package cs211.project.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Activity {
    private String eventOfActivityName;
    private String teamOfActivityName;
    private String activityName;
    private String activityInformation;
    private boolean isActivityAvailable;
    private LocalTime activityStartTime;
    private LocalTime activityEndTime;

    public Activity(String eventOfActivityName, String teamOfActivityName, String activityName, String activityInformation) {
        this.eventOfActivityName = eventOfActivityName;
        this.teamOfActivityName = teamOfActivityName;
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

    public String getEventOfActivityName() {
        return eventOfActivityName;
    }

    public String getTeamOfActivityName() {
        return teamOfActivityName;
    }

    public boolean isActivityAvailable() {
        return isActivityAvailable;
    }

    public LocalTime getActivityEndTime() {
        return activityEndTime;
    }

    public boolean isTeamAvailable() {
        return isActivityAvailable;
    }

    public void setTeamAvailable(boolean isTeamAvailable) {
        this.isActivityAvailable = isTeamAvailable;
    }
    public LocalTime getActivityStartTime() {
        return activityStartTime;
    }


    public Activity(LocalTime activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public boolean isActivityOfTeam(Activity activity) {
        if (activity.getActivityName().equals(this.getActivityName()) && activity.getEventOfActivityName().equals(this.getEventOfActivityName()) && activity.getTeamOfActivityName().equals(this.getTeamOfActivityName())) {
            return true;
        }
        return false;
    }
}
