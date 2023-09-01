package cs211.project.services;

import cs211.project.models.TeamList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class TeamListFileDatasource implements Datasource<TeamList>{
    private String directoryName;
    private String fileName;

    public TeamListFileDatasource(String directoryName, String fileName) {
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
    public TeamList readData() {
        TeamList teams = new TeamList();
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

                String eventOfTeamName = data[0].trim();
                String teamName = data[1].trim();
                int maxParticipants = Integer.parseInt(data[2].trim());
                LocalDate startJoinDate = LocalDate.parse(data[3].trim());
                LocalDate closingJoinDate = LocalDate.parse(data[4].trim());

                //เหลือ teamActivities, teamMembers, teamChat ที่ยังไม่แน่ใตว่าเก็บไฟล์เป็นยังไง

                //teams.addNewTeam(eventOfTeamName, teamName, maxParticipants, startJoinDate, closingJoinDate, teamActivities, teamMembers, teamChat);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return teams;
    }

    @Override
    public void writeData(TeamList teamList) {
        ;
    }
}
