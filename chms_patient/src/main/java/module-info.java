module com.example.patient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports org.example.patient.models;
    exports org.example.patient.controllers;
    exports org.example.patient.application to javafx.graphics, com.example.main.main;
    opens org.example.patient.application to javafx.fxml;
    opens org.example.patient.models to javafx.fxml;
    opens org.example.patient.controllers to javafx.fxml;
}