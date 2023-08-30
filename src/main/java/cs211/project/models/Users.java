package cs211.project.models;

import java.util.ArrayList;

public class Users {
    private ArrayList<User> users;

    public void addUser(User user) {
        users.add(user);
    }

    public void addUser(String username) {
        User user = new User(username);
        users.add(user);
    }

    public boolean findUser(User user) {
        for (User aUser : this.users) {
            if (user.getUsername().equals(aUser.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean findUser(String username) {
        for (User aUser : this.users) {
            if (username.equals(aUser.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }
}
