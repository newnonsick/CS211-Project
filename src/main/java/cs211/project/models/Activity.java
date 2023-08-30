package cs211.project.models;

public class Activity {
    private String activityName;
    private String activityInformation;
    private boolean isActivityAvailable;

    public Activity(String activityName, String activityInformation) {
        this.activityName = activityName;
        this.activityInformation = activityInformation;
        this.isActivityAvailable = true;
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
