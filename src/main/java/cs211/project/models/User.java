package cs211.project.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class User {
    private String username;
    private String name;

    //We will later make it using only username and then read data from csv file to fill the rest
    public User(String username) {
        if(checkUserExistence(username)) {
            this.username = username;
        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public static boolean checkUserExistence(String username) {
        try {
            FileReader fileReader = new FileReader("data/userData.csv");
            BufferedReader buffer = new BufferedReader(fileReader);
            String line = "";
            try {
                while ((line = buffer.readLine()) != null) {
                    if (line.isEmpty()) continue;
                    String[] data = line.split(",");
                    String usernameInData = data[0].trim();
                    if (username.equals(usernameInData)) {
                        return true;
                    }
                }
                return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
