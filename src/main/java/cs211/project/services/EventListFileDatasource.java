package cs211.project.services;

import cs211.project.models.Event;
import cs211.project.models.EventList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

public class EventListFileDatasource implements Datasource<EventList> {
    private String directoryName;
    private String fileName;

    public EventListFileDatasource(String directoryName, String fileName) {
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
    public EventList readData() {
        EventList events = new EventList();
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
                String name = data[0].trim();
                String picture = data[1].trim();
                String info = data[2].trim();
                String category = data[3].trim();
                String place = data[4].trim();
                LocalDate startDate = LocalDate.parse(data[5].trim());
                LocalDate endDate = LocalDate.parse(data[6].trim());
                LocalTime startTime = LocalTime.parse(data[7].trim());
                LocalTime endTime = LocalTime.parse(data[8].trim());
                String ownerUsername = data[9].trim();
                int maxParticipants = data.length > 10 && !data[10].trim().equals("-1") ? Integer.parseInt(data[10].trim()) : -1;
                LocalDate startJoinDate = null;
                if (data.length > 11 && !data[11].trim().isEmpty() && !data[11].trim().equalsIgnoreCase("null")) {
                    startJoinDate = LocalDate.parse(data[11].trim());
                }

                LocalDate closeJoinDate = null;
                if (data.length > 12 && !data[12].trim().isEmpty() && !data[12].trim().equalsIgnoreCase("null")) {
                    closeJoinDate = LocalDate.parse(data[12].trim());
                }
                String eventUUID = data.length > 13 ? data[13].trim() : null;

                events.addEvent(name, picture, info, category, place, startDate, endDate, startTime, endTime,
                        ownerUsername, maxParticipants, startJoinDate, closeJoinDate, eventUUID);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(events.getEvents());
        return events;
    }

    @Override
    public void writeData(EventList eventList) {
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
            for (Event event : eventList.getEvents()) {
                String line = event.getName() + "," + event.getPicture() + "," + event.getInfo() + ","
                        + event.getCategory()  + "," + event.getPlace() + "," + event.getStartDate() + ","
                        + event.getEndDate() + "," + event.getStartTime() + "," + event.getEndTime() + ","
                        + event.getOwnerUsername() + "," + event.getMaxParticipants() + ","
                        + event.getStartJoinDate() + "," + event.getCloseJoinDate() + "," + event.getEventUUID();
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
