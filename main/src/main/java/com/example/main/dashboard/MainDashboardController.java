package com.example.main.dashboard;

import bdio.chms.pharmacy.views.Main;
import com.example.doctorappointments.MainApplication;
import com.pack.billingsystem.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainDashboardController {
    @FXML
    public ImageView bannerImage;

    @FXML
    public ImageView logoImage;

    @FXML
    public ImageView icon1;

    @FXML
    public ImageView icon2;

    @FXML
    public ImageView icon3;

    @FXML
    public ImageView icon4;

    @FXML
    public ImageView icon5;

    @FXML
    public ImageView icon6;


    public void initialize() {
        Image image1 = new Image(getClass().getResource("/com/example/main/main/images/doc_img.png").toExternalForm());
        bannerImage.setImage(image1);

        Image image2 = new Image(getClass().getResource("/com/example/main/main/images/heartbeats_logo.png").toExternalForm());
        logoImage.setImage(image2);

        Image image3 = new Image(getClass().getResource("/com/example/main/main/images/doctor.png").toExternalForm());
        icon1.setImage(image3);

        Image image4 = new Image(getClass().getResource("/com/example/main/main/images/pharmacy.png").toExternalForm());
        icon2.setImage(image4);

        Image image5 = new Image(getClass().getResource("/com/example/main/main/images/test-tubes.png").toExternalForm());
        icon3.setImage(image5);

        Image image6 = new Image(getClass().getResource("/com/example/main/main/images/medical.png").toExternalForm());
        icon4.setImage(image6);

        Image image7 = new Image(getClass().getResource("/com/example/main/main/images/analysis.png").toExternalForm());
        icon5.setImage(image7);

        Image image8 = new Image(getClass().getResource("/com/example/main/main/images/bill.png").toExternalForm());
        icon6.setImage(image8);

    }

    @FXML
    public void handleMainApplicationButton() throws IOException {
        MainApplication mainApplication = new MainApplication();
        Stage stage = new Stage();
        mainApplication.start(stage);
    }

    @FXML
    public void handleMainPharmacyButton() throws IOException {
        Main main = new Main();
        Stage stage = new Stage();
        main.start(stage);
    }

    @FXML
    public void handleMainLaboratoryButton() throws IOException {
        com.example.lab.Main main = new com.example.lab.Main();
        Stage stage = new Stage();
        main.start(stage);
    }

    @FXML
    public void handleMainPatientButton() throws IOException {
        org.example.patient.application.Main main = new org.example.patient.application.Main();
        Stage stage = new Stage();
        main.start(stage);
    }

    @FXML
    public void handleMainAnalysisButton() throws IOException {
        com.example.analyse.HelloApplication main = new com.example.analyse.HelloApplication();
        Stage stage = new Stage();
        main.start(stage);
    }

    @FXML
    public void handleMainBillButton() throws Exception {
        com.pack.billingsystem.BillingApp main = new com.pack.billingsystem.BillingApp();
        Stage stage = new Stage();
        main.start(stage);
    }

    private void loadNewModule(String fxmlPath, String title) {
        try {
            // Load the new FXML using ClassLoader
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, e.g., show an alert dialog
        }
    }
}
