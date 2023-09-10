package cs211.project.services;

import cs211.project.models.Activity;
import cs211.project.models.ActivityList;
import cs211.project.models.TeamChat;
import cs211.project.models.TeamChatList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TeamActivityListFileDatasource implements Datasource<ActivityList>{
    private String directoryName;
    private String fileName;

    public TeamActivityListFileDatasource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }
    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public ActivityList readData() {
        ActivityList activities = new ActivityList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );
        BufferedReader buffer = new BufferedReader(inputStreamReader);

        String line = "";
        try {
            while ( (line = buffer.readLine()) != null ){

                if (line.equals("")) continue;

                String[] data = line.split(",");

                String eventName = data[0].trim();
                String teamName = data[1].trim();
                String activityName = data[2].trim();
                String activityInfo = data[3].trim();
                String activityStatus = data[4].trim();

                activities.addNewActivityTeam(eventName, teamName, activityName, activityInfo, activityStatus);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return activities;
    }
    //addNewActivityTeam(String eventOfActivityName, String teamOfActivityName, String activityName, String activityInformation)
    @Override
    public void writeData(ActivityList activityList) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                fileOutputStream,
                StandardCharsets.UTF_8
        );
        BufferedWriter buffer = new BufferedWriter(outputStreamWriter);

        try {
            for (Activity activity : activityList.getActivities()) {
                String line = activity.getEventOfActivityName() + "," + activity.getTeamOfActivityName() + "," + activity.getActivityName() + "," + activity.getActivityInformation() + "," + activity.getActivityStatus();
                buffer.append(line);
                buffer.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.flush();
                buffer.close();
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}
