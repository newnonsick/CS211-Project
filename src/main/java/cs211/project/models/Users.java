package cs211.project.models;

import java.util.ArrayList;

public class Users {
    private ArrayList<User> Users;

    public void addUser(User user) {
        Users.add(user);
    }
    public void addUser(String username) {
        if(User.checkUserExistence(username)) {
            User user = new User(username);
            Users.add(user);
        }
    }

    public boolean findUser(User user) {
        for(User aUser : this.Users) {
            if(user.getUsername().equals(aUser.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean findUser(String username) {
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
