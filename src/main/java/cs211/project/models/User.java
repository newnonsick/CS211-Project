package cs211.project.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class User {
    private String username;
    private String name;
    private String pic;

    public User(String username) {
        String info = checkUserExistence(username);
        if(info != null) {
            this.username = username;
            this.name = info.split(":")[0];
            this.pic = info.split(":")[1];
        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public String getPic() { return this.pic;}

    public static String checkUserExistence(String username) {
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
                        return data[2].trim() + ":" + data[3].trim();
                    }
                }
                return null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
