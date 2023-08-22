package cs211.project.models;
import cs211.project.models.User;

import java.util.ArrayList;

public class Users {
    private ArrayList<User> Users;

    public void addUser(User user) {
        Users.add(user);
    }

    public boolean checkUser(User user) {
        for(User aUser : this.Users) {
            if(user.getUsername().equals(aUser.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkUser(String username) {
        for(User aUser : this.Users) {
            if(username.equals(aUser.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<User> getUsers() {
        return this.Users;
    }
}
