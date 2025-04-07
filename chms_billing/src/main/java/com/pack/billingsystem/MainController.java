package com.pack.billingsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("unused")
public class MainController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private BillingController billingController;

    /*public void switchToBill(MouseEvent event,int idPatient) throws IOException,SQLException {
        root = FXMLLoader.load(getClass().getResource("billing.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        billingController = new BillingController(idPatient);
        stage.setScene(scene);
        stage.show();
    }*/

    public void switchToBill(MouseEvent event, int patientId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("billing.fxml"));
            Parent root = loader.load();

            BillingController billingController = loader.getController();
            billingController.setPatientID(patientId); // Passer l'ID du patient

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
