package cs211.project.models;

import java.util.ArrayList;

public class ActivityList {
    ArrayList<Activity> activities;

    public ActivityList() {
        activities = new ArrayList<>();
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void addNewActivity(String activityName, String activityInformation) {
        activityName = activityName.trim();
        activityInformation = activityInformation.trim();
        if (!activityName.equals("") && !activityInformation.equals("")) {
            Activity exist = findActivityByName(activityName);
            if (exist == null) {
                activities.add(new Activity(activityName, activityInformation));
            }
        }
    }

    public Activity findActivityByName(String activityName) {
        for (Activity activity : activities) {
            if (activity.getActivityName().equals(activityName)) {
                return activity;
            }
        }
        return null;
    }
}
