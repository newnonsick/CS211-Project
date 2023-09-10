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

    public void addNewActivityTeam(String eventOfActivityName, String teamOfActivityName, String activityName, String activityInformation, String activityStatus) {
        eventOfActivityName = eventOfActivityName.trim();
        teamOfActivityName = teamOfActivityName.trim();
        activityName = activityName.trim();
        activityInformation = activityInformation.trim();
        if (!eventOfActivityName.equals("") && !teamOfActivityName.equals("") && !activityName.equals("") && !activityInformation.equals("")) {
            Activity exist = findActivityTeamByObject(new Activity(eventOfActivityName,teamOfActivityName,activityName, activityInformation, activityStatus));
            if (exist == null) {
                activities.add(new Activity(eventOfActivityName,teamOfActivityName,activityName, activityInformation, activityStatus));
            }
        }
    }

    public Activity findActivityTeamByObject(Activity activity) {
        for (Activity activity1 : activities) {
            if (activity1.isActivityOfTeam(activity)) {
                return activity1;
            }
        }
        return null;
    }
}
