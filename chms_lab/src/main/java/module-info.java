module com.example.lab {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    requires java.sql;
    requires javafx.graphics;  // Add this line to import the java.sql module

    opens com.example.lab to javafx.fxml;
    exports com.example.lab;
    exports com.example.lab.controllers;
    opens com.example.lab.controllers to javafx.fxml;
    exports com.example.lab.database;
    opens com.example.lab.database to javafx.fxml;

    // Add this line to open the models package to javafx.base
    opens com.example.lab.models to javafx.base;
}
