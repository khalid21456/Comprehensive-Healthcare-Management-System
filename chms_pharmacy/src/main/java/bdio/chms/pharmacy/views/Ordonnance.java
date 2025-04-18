package bdio.chms.pharmacy.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Ordonnance extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bdio/chms/pharmacy/fxml/Ordonnance.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        Scene scene = new Scene(root);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bdio/chms/pharmacy/images/heartbeats_logo.png")));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("CarePoint");
        primaryStage.setScene(scene);


        // Afficher la fenêtre
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}