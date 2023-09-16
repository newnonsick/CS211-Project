package cs211.project.models;

import cs211.project.services.Datasource;
import cs211.project.services.UserListFileDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class User {
    private String username;
    private String name;
    private String profilePic;
    private String password;

    public User(String username) {
        Datasource<UserList> userListDatasource = new UserListFileDataSource("data", "userData.csv");
        UserList userList = userListDatasource.readData();
        for(User user : userList.getUsers()) {
            if(user.getUsername().equals(username)) {
                this.username = user.getUsername();
                this.password = user.getPassword();
                this.name = user.getName();
                this.profilePic = user.profilePic;
                return;
            }
        }
    }
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

    public String getProfilePicture() { return this.profilePic;}

}
