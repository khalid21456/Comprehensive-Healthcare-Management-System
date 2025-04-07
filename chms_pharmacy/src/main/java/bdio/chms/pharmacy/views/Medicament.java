package bdio.chms.pharmacy.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Medicament extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bdio/chms/pharmacy/fxml/Medicament.fxml"));
        BorderPane xA = fxmlLoader.load();

        Scene scene = new Scene(xA, 1090, 700);

        // scene.getStylesheets().add(getClass().getResource("/bdio/chms/chms/fxml/styles.css").toExternalForm());

        Image icon = new Image(getClass().getResourceAsStream("/bdio/chms/pharmacy/images/heartbeats_logo.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("CarePoint");
        primaryStage.setScene(scene);


        // Afficher la fenêtre
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}