package cs211.project.services;

import cs211.project.models.Team;
import cs211.project.models.TeamChat;
import cs211.project.models.TeamChatList;
import cs211.project.models.TeamList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class TeamChatListFileDatasource implements Datasource<TeamChatList>{
    private String directoryName;
    private String fileName;

    public TeamChatListFileDatasource(String directoryName, String fileName) {
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
    public TeamChatList readData() {
        TeamChatList teamChats = new TeamChatList();
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
                String username = data[2].trim();
                String message = data[3].trim();

                teamChats.addNewChat(eventName, teamName, username, message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return teamChats;
    }

    @Override
    public void writeData(TeamChatList teamChatList) {
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
            for (TeamChat teamChat : teamChatList.getTeamChats()) {
                String line = teamChat.getEventName() + "," + teamChat.getTeamName() + "," + teamChat.getUsername() + "," + teamChat.getMessage();
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
