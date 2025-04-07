module com.example.main.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires bdio.chms.pharmacy;
    requires com.example.doctorappointments;
    requires com.example.lab;
    requires com.pack.billingsystem;
    requires com.example.patient;
    requires com.example.analyse;


    opens com.example.main.main to javafx.fxml;
    exports com.example.main.dashboard;
}