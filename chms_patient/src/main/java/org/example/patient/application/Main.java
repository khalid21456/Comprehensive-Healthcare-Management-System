package org.example.patient.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the dashboard FXML file

            URL fxmlUrl = getClass().getResource("/org/example/patient/views/login.fxml");
            if (fxmlUrl == null) {
                throw new IllegalStateException("FXML file 'dashboard.fxml' not found in '/org/example/patient/views/'");
            }
            Parent root = FXMLLoader.load(fxmlUrl);

            Scene scene = new Scene(root);

            // Load and apply CSS styling
            URL cssUrl = getClass().getResource("/org/example/patient/styles/application.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("CSS file 'application.css' not found. Proceeding without styles.");
            }

            // Configure the primary stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("CHMS - Patient Management System");
            primaryStage.setResizable(false); // Disable resizing
            primaryStage.show();

        } catch (Exception e) {
            // Log detailed error information
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}