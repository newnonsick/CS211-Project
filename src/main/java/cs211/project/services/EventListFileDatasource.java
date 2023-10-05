package cs211.project.services;

import cs211.project.models.Event;
import cs211.project.models.EventList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collections;

public class EventListFileDatasource implements Datasource<EventList> {
    private String directoryName;
    private String fileName;

    public EventListFileDatasource(String directoryName, String fileName) {
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
    public EventList readData() {
        EventList events = new EventList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        // เตรียม object ที่ใช้ในการอ่านไฟล์
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
        try {  //more information hasn't been added yet
            while ( (line = buffer.readLine()) != null ){
                if (line.equals("")) continue;
                String[] data = line.split(",");
                String eventName = data[0].trim();
                String eventPicture = data[1].trim();
                String eventInformation = data[2].trim();
                String eventCategory = data[3].trim();
                String placeEvent = data[4].trim();
                LocalDate eventStartDate = LocalDate.parse(data[5].trim());
                LocalDate eventEndDate = LocalDate.parse(data[6].trim());
                String eventOwnerUsername = data[7].trim();
                int maxParticipants = data.length > 8 && !data[8].trim().equals("-1") ? Integer.parseInt(data[8].trim()) : -1;
                LocalDate startJoinDate = null;
                if (data.length > 9 && !data[9].trim().isEmpty() && !data[9].trim().equalsIgnoreCase("null")) {
                    startJoinDate = LocalDate.parse(data[9].trim());
                }

                LocalDate closingJoinDate = null;
                if (data.length > 10 && !data[10].trim().isEmpty() && !data[10].trim().equalsIgnoreCase("null")) {
                    closingJoinDate = LocalDate.parse(data[10].trim());
                }
                String eventUUID = data.length > 11 ? data[11].trim() : null;

                events.addEvent(eventName, eventPicture, eventInformation, eventCategory, placeEvent, eventStartDate, eventEndDate, eventOwnerUsername, maxParticipants, startJoinDate, closingJoinDate, eventUUID);
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
                String line = event.getName() + "," + event.getPicture() + ","
                        + event.getInfo() + "," + event.getCategory()  + "," +
                        event.getPlace() + "," + event.getStartDate() + "," +
                        event.getEndDate() + "," + event.getOwnerUsername() + "," + event.getMaxParticipants() + "," + event.getStartJoinDate() + "," + event.getCloseJoinDate() + "," + event.getEventUUID();
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
