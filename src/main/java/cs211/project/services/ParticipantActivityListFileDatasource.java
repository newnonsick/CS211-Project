package cs211.project.services;

import cs211.project.models.Activity;
import cs211.project.models.ActivityList;
import cs211.project.models.Event;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Collections;

public class ParticipantActivityListFileDatasource implements Datasource<ActivityList>{
    private String directoryName;
    private String fileName;

    public ParticipantActivityListFileDatasource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    // check if file exists (if not, creat a new file)
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
        ActivityList activities;
        activities = new ActivityList();
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
                String activityName = data[0].trim();
                String activityInformation = data[1].trim();
                LocalTime activityStartTime = LocalTime.parse(data[2].trim());
                LocalTime activityEndTime = LocalTime.parse(data[3].trim());

                // add data to the list
                activities.addNewActivityParticipant(activityName, activityInformation, activityStartTime, activityEndTime);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return activities;
    }

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
                String line = activity.getActivityName() + "," + activity.getActivityInformation() + "," + activity.getActivityStartTime() + "," +activity.getActivityEndTime();
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

