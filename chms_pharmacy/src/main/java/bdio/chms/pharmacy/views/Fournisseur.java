package bdio.chms.pharmacy.views;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Fournisseur extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/bdio/chms/pharmacy/fxml/Fournisseur.fxml"));
        Parent root = loader.load();  // Utilisation de Parent pour l'objet root

        // Créer la scène et définir le titre de la fenêtre
        Scene scene = new Scene(root);
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
