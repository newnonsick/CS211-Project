package cs211.project.models;

import java.time.LocalDate;
import java.time.LocalTime;
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
            Activity exist = findActivityByObject(new Activity(eventOfActivityName, teamOfActivityName, activityName, activityInformation, activityStatus));
            if (exist == null) {
                activities.add(new Activity(eventOfActivityName, teamOfActivityName, activityName, activityInformation, activityStatus));
            }
        }
    }

    public void addNewActivityParticipant(String eventOfActivityName, String activityName, String activityInformation, LocalTime activityStartTime, LocalTime activityEndTime, LocalDate activityDate)

    {
        eventOfActivityName = eventOfActivityName.trim();
        activityName = activityName.trim();
        activityInformation = activityInformation.trim();
        if (!activityName.equals("") && !activityInformation.equals("")) {
            Activity newActivity = findActivityByObject(new Activity(eventOfActivityName, activityName, activityInformation, activityStartTime, activityEndTime, activityDate));
            if (newActivity == null) {
                activities.add(new Activity(eventOfActivityName, activityName, activityInformation, activityStartTime, activityEndTime, activityDate));

            }
        }
    }



    public Activity findActivityByObject(Activity activity) {
        for (Activity activity1 : activities) {
            if (activity1.isActivity(activity)) {
                return activity1;
            }
        }
        return null;
    }
}
