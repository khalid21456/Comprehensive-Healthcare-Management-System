package com.pack.billingsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class BillingApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BillsList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 700);
        //String css = getClass().getResource("stylesheets/style.css").toExternalForm();
        primaryStage.setResizable(false);
        //scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Billing System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}