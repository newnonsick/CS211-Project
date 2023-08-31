package cs211.project.services;

import cs211.project.models.Events;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class EventListFileDatasource implements Datasource<Events> {
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
    public Events readData() {
        Events events = new Events();
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
                String eventInformation = data[1].trim();
                String eventCategory = data[2].trim();
                String placeEvent = data[3].trim();
                LocalDate eventStartDate = LocalDate.parse(data[4].trim());
                LocalDate eventEndDate = LocalDate.parse(data[5].trim());

                // add data to the list
                events.addEvent(eventName, eventInformation, eventCategory, placeEvent, eventStartDate, eventEndDate);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return events;
    }

    @Override
    public void writeData(Events events) {

    }
}
