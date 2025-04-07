module com.example.doctorappointments {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.doctorappointments to javafx.fxml;
    exports com.example.doctorappointments;
    exports com.example.doctorappointments.model;
    opens com.example.doctorappointments.model to javafx.fxml;
    exports com.example.doctorappointments.controller;
    opens com.example.doctorappointments.controller to javafx.fxml;
    exports com.example.doctorappointments.service;
    opens com.example.doctorappointments.service to javafx.fxml;
}