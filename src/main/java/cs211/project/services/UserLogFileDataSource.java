package cs211.project.services;

import cs211.project.models.EventList;
import cs211.project.models.LogUser;
import cs211.project.models.User;
import cs211.project.models.UserList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class UserLogFileDataSource implements Datasource<UserList> {

    private String directoryName;
    private String fileName;

    public UserLogFileDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }
    @Override
    public UserList readData() {
        UserList users = new UserList();
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
                String username = data[0];
                String date = data[1];
                String time = data[2];
                users.addUser(new LogUser(username, date,time));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Collections.reverse(users.getUsers());
        UserList finalUsers = new UserList();
        ArrayList<String> pastUsers = new ArrayList<String>();
        for(User user : users.getUsers()) {
            if(!pastUsers.contains(user.getUsername())) {
                finalUsers.addUser(user);
                pastUsers.add(user.getUsername());
            }
        }
        return finalUsers;
    }

    @Override
    public void writeData(UserList userList) {

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
}
