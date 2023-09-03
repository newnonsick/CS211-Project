module cs211.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires bcrypt;

    exports cs211.project.controllers;
    opens cs211.project.controllers to javafx.fxml;
    exports cs211.project;
    opens cs211.project to javafx.fxml;
    exports cs211.project.cs211661project;
    opens cs211.project.cs211661project to javafx.fxml;
    exports cs211.project.models;
    opens cs211.project.models to javafx.fxml;
}