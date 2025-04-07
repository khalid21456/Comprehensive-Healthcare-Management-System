package bdio.chms.pharmacy.controllers;

import bdio.chms.pharmacy.dao.OrdonnanceDao;
import bdio.chms.pharmacy.models.Ordonnance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class OrdonnanceController {

    @FXML
    private TableView<Ordonnance> ordonnanceTable;

    @FXML
    private TableColumn<Ordonnance, Integer> colIdOrdonnance;

    @FXML
    private TableColumn<Ordonnance, String> colDateOrdonnance;

    @FXML
    private TableColumn<Ordonnance, String> colPatientName;

    @FXML
    private TableColumn<Ordonnance, String> colDoctorName;

    @FXML
    private TableColumn<Ordonnance, String> colStatus;

    private ObservableList<Ordonnance> ordonnanceList;

    @FXML
    private TableColumn<Ordonnance, Void> colDetails;

    @FXML
    public void initialize() {
        colIdOrdonnance.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        colStatus.setCellFactory(column -> new TableCell<Ordonnance, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (status != null && !empty) {
                    Label label = new Label(status);
                    if ("Active".equals(status)) {
                        label.setTextFill(Color.WHITE);
                        label.setMaxHeight(15);
                        label.setStyle("-fx-background-color: #FED440; -fx-padding: 5px;-fx-font-weight: bold;-fx-background-radius: 20px; ");
                    } else if ("Delivred".equals(status)) {
                        label.setTextFill(Color.WHITE);
                        label.setStyle("-fx-background-color: #59D189; -fx-padding: 5px;-fx-font-weight: bold;-fx-pref-height:12px;-fx-background-radius: 20px; ");
                    } else {
                        label.setTextFill(Color.BLACK);
                        label.setStyle("-fx-background-color: white; -fx-padding: 5px;fx-font-weight: bold;");
                    }
                    setGraphic(label);
                } else {
                    setGraphic(null);
                }
            }
        });


        colDateOrdonnance.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("Patient"));
        colDoctorName.setCellValueFactory(new PropertyValueFactory<>("Doctor"));

        loadOrdonnances();
        addDetailsButtonToTable();

    }

    private void loadOrdonnances() {
        OrdonnanceDao dao = new OrdonnanceDao();
        ordonnanceList = FXCollections.observableArrayList(dao.getAll());
        ordonnanceTable.setItems(ordonnanceList);
    }

    private void addDetailsButtonToTable() {
        TableColumn<Ordonnance, Void> colDetails = new TableColumn<>("Actions");

        colDetails.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button();
            private final ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/bdio/chms/pharmacy/images/play.png")));
            {
                icon.setFitHeight(15);
                icon.setFitWidth(15);
                btn.setText("Details");
                btn.setContentDisplay(ContentDisplay.RIGHT);
                btn.setAlignment(Pos.CENTER);
                btn.setPrefWidth(80);
                btn.setMaxHeight(10);
                btn.setStyle(
                        "-fx-background-color:#5A9BD5FF;-fx-text-fill:white;-fx-font-weight:bold;-fx-font-size:11px;"+
                                "-fx-background-radius: 10px;-fx-alignment:center;-fx-pref-height: 10px;"
                );
                btn.setGraphic(icon);
                btn.setOnAction(event -> {
                    Ordonnance ordonnance = getTableView().getItems().get(getIndex());
                    System.out.println(ordonnance);
                    showDetailsWindow(ordonnance);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        ordonnanceTable.getColumns().add(colDetails);
    }

    private void showDetailsWindow(Ordonnance ordonnance) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bdio/chms/pharmacy/fxml/Details.fxml"));
            Parent root = loader.load();
            DetailsController detailsController = loader.getController();
            detailsController.setOrdonnance(ordonnance);
            Stage stage = new Stage();
            stage.setTitle(" Prescription Details");
            stage.setScene(new Scene(root,450,500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private Node sidebar;

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Method to load views dynamically into the dashboard content area
    private void loadView(String fxmlFile, ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            root = loader.load();

            // Get the current stage and set the new scene
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            // Manage sidebar visibility based on the view being loaded
            manageSidebarVisibility(fxmlFile);

            // Set the scene to the stage and show it
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading view: " + fxmlFile);
            e.printStackTrace();
        }
    }


    private void manageSidebarVisibility(String fxmlFile) {
        if (sidebar != null) {
            boolean isSidebarVisible = fxmlFile.equals("/bdio/chms/pharmacy/fxml/Dashboard.fxml") ||
                    fxmlFile.equals("/bdio/chms/pharmacy/fxml/Medicament.fxml");
            sidebar.setVisible(isSidebarVisible);
        }
    }

    @FXML
    private void goToProfile(ActionEvent event) {
        loadView("/bdio/chms/pharmacy/fxml/Profile.fxml", event);
    }

    @FXML
    private void goToDashboard(ActionEvent event) {
        loadView("/bdio/chms/pharmacy/fxml/Dashboard.fxml", event);
    }

    @FXML
    private void showViewMedication(ActionEvent event) {
        loadView("/bdio/chms/pharmacy/fxml/Medicament.fxml", event);
    }

    @FXML
    private void goToOrdonnance(ActionEvent event) {
        loadView("/bdio/chms/pharmacy/fxml/Ordonnance.fxml", event);
    }
    @FXML
    private void goToFournisseur(ActionEvent event) {
        loadView("/bdio/chms/pharmacy/fxml/Fournisseur.fxml", event);
    }

}
