package com.example.analyse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load FXML files for the three graphs
        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("VisInsurence.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("VisTestResults.fxml"));
        FXMLLoader fxmlLoader3 = new FXMLLoader(HelloApplication.class.getResource("Vis3FreqTest.fxml"));
        /// / ikrame ghraphes !!!!
        FXMLLoader fxmlLoader4 = new FXMLLoader(HelloApplication.class.getResource("NumberPatientIKR.fxml"));
        FXMLLoader fxmlLoader5 = new FXMLLoader(HelloApplication.class.getResource("AppointmentNumberIKR.fxml"));


        /// / halza graphes !!!!!
        FXMLLoader fxmlLoader6 = new FXMLLoader(HelloApplication.class.getResource("AppointmentPerYearAndServiceHMZ.fxml"));


        ///  khadija

        FXMLLoader fxmlLoader7 = new FXMLLoader(HelloApplication.class.getResource("GenderAgeKDJ.fxml"));
        FXMLLoader fxmlLoader8 = new FXMLLoader(HelloApplication.class.getResource("InsuranceClaimsKDJ.fxml"));
        FXMLLoader fxmlLoader9 = new FXMLLoader(HelloApplication.class.getResource("TestResultKDJ.fxml"));


        /// /// kEchfi

        FXMLLoader fxmlLoader10 = new FXMLLoader(HelloApplication.class.getResource("PatientsViewKFC.fxml"));
        FXMLLoader fxmlLoader11 = new FXMLLoader(HelloApplication.class.getResource("FidelityViewKFC.fxml"));





        // Create tabs for each graph
        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Insurance Graph", fxmlLoader1.load());
        tab1.setClosable(false); // Disable tab closing
        Tab tab2 = new Tab("Test Results Graph", fxmlLoader2.load());
        tab2.setClosable(false);
        Tab tab3 = new Tab("Frequency Test Graph", fxmlLoader3.load());
        tab3.setClosable(false);
        /// ///
        Tab tab4 = new Tab("Number of patient Per Year", fxmlLoader4.load());
        tab4.setClosable(false);
        Tab tab5 = new Tab("Number of Appointemtns", fxmlLoader5.load());
        tab4.setClosable(false);

        /// /////


        Tab tab6 = new Tab("Progress Bar", fxmlLoader6.load());
        tab4.setClosable(false);

        /// /////

        Tab tab7 = new Tab("Number of Appointemtns", fxmlLoader7.load());
        tab4.setClosable(false);

        Tab tab8 = new Tab("Inssurance Claim", fxmlLoader8.load());
        tab4.setClosable(false);

        Tab tab9 = new Tab("Test Result time", fxmlLoader9.load());
        tab4.setClosable(false);

        /// /////
        Tab tab10 = new Tab("NBR of patient", fxmlLoader10.load());
        tab4.setClosable(false);
        Tab tab11= new Tab("FIDELETY", fxmlLoader11.load());
        tab4.setClosable(false);



        // Add tabs to the TabPane
        tabPane.getTabs().addAll(tab1, tab2, tab3 , tab4 , tab5 ,  tab6 ,  tab7 ,  tab8 , tab9 , tab10 , tab11);

        // Set up the scene and stage
        Scene scene = new Scene(tabPane, 800, 600);
        stage.setTitle("Graphs Viewer");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        // Close the database connection when the application stops
       // DatabaseConnection.closeConnection();
    }

    public static void main(String[] args) {
        launch();
    }
}
