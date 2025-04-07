package com.example.main.dashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainDashboard extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println(MainDashboard.class.getResource("/com/example/main/main/dashboard/MainDashboard.fxml"));
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/main/main/dashboard/MainDashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Dashboard");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
