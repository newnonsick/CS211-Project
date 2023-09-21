package cs211.project.models;

import cs211.project.services.Datasource;
import cs211.project.services.UserListFileDataSource;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class User {
    private String username;
    private String name;
    private String profilePic;
    private String password;

    public User(String username, String password,String name, String pic) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.profilePic = pic;
    }

    public String getUsername() {
        return this.username;
    }
    public String getPassword() {return this.password;}

    public String getName() {
        return this.name;
    }

    public Image getProfilePicture() {
        if(this.profilePic.equals("default.png")) {
            return new Image(getClass().getResource("/cs211/project/images/default.png").toExternalForm());
        }

        else {
            String filePath = "data/profile_picture/" + this.profilePic;
            File file = new File(filePath);
            return (new Image(file.toURI().toString()));
        }
    }

    public String getProfilePictureName() {
        return this.profilePic;
    }

    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }
}
