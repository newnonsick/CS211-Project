package cs211.project.models;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> users;

    public UserList() {
        users = new ArrayList<User>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User findUserByUsername(String username) {
        for (User user : this.users) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
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
    public User getUser(String username) {
        for (User aUser : this.users) {
            if (username.equals(aUser.getUsername())) {
                return aUser;
            }
        }
        return null;
    }


    public ArrayList<User> getUsers() {
        return this.users;
    }


}
