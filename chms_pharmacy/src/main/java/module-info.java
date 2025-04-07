module bdio.chms.pharmacy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.javafx;
    requires mysql.connector.j;

    
    exports bdio.chms.pharmacy.models;
    opens bdio.chms.pharmacy.models to javafx.fxml;
    exports bdio.chms.pharmacy.dao;
    opens bdio.chms.pharmacy.dao to javafx.fxml;
    exports bdio.chms.pharmacy.controllers;
    opens bdio.chms.pharmacy.controllers to javafx.fxml;
    exports bdio.chms.pharmacy.views;
    opens bdio.chms.pharmacy.views to javafx.fxml;
}