package cs211.project.models;

public class User {
    private String username;
    private String name;
    public User(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }
}
