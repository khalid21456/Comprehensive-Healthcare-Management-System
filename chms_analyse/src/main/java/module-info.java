module com.example.analyse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.analyse to javafx.fxml;
    opens com.example.analyse.Controller to javafx.fxml;  // add this line

    exports com.example.analyse;
    exports com.example.analyse.Controller;
}