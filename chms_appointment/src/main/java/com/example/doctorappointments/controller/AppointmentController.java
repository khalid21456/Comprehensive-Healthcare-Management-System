package com.example.doctorappointments.controller;

import com.example.doctorappointments.model.Appointment;
import com.example.doctorappointments.model.AppointmentDetails;
import com.example.doctorappointments.service.AppointmentService;
import com.example.doctorappointments.service.DatabaseConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AppointmentController {

    @FXML
    private TableView<AppointmentDetails> AppointmentTableView;

    @FXML
    private TableColumn<AppointmentDetails, String> PatientNameTableColumn;
    @FXML
    private TableColumn<AppointmentDetails, String> DoctorNameTableColumn;
    @FXML
    private TableColumn<AppointmentDetails, String> AppointmentDateTableColumn;
    @FXML
    private TableColumn<AppointmentDetails, String> AppointmentTimeTableColumn;
    @FXML
    private TableColumn<AppointmentDetails, String> ServiceTableColumn;
    @FXML
    private TableColumn<AppointmentDetails, String> StatusTableColumn;
    @FXML
    private TableColumn<AppointmentDetails, String> PayeTableColumn;

    @FXML
    private TableColumn<AppointmentDetails, Void> ActionsTableColumn;



    @FXML
    private TextField SearchFieldText;

    @FXML
    private DatePicker datePicker;


    @FXML
    private Pagination pagination;

    private ObservableList<AppointmentDetails> appointments;
    private int rowsPerPage = 14;


    @FXML
    private void initialize() {

        List<AppointmentDetails> appointmentsFromDB = AppointmentService.getAllAppointments();
        appointments = FXCollections.observableArrayList(appointmentsFromDB);





        DoctorNameTableColumn.setCellValueFactory(cellData -> {
            AppointmentDetails appointment = cellData.getValue();
            return new SimpleStringProperty(appointment.getDoctorFullName());
        });
        PatientNameTableColumn.setCellValueFactory(cellData -> {
            AppointmentDetails appointment = cellData.getValue();
            return new SimpleStringProperty(appointment.getPatientFullName());
        });
        AppointmentDateTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedDate()));
        AppointmentTimeTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedTime()));
        ServiceTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));
        StatusTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        PayeTableColumn.setCellFactory(column -> new TableCell<AppointmentDetails, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    AppointmentDetails appointment = getTableRow().getItem();
                    Integer paye = appointment.getPaye();
                    if (paye != null) {
                        setText(paye == 1 ? "✓" : "X");
                        setStyle("-fx-alignment: CENTER;"); // Center-align the text
                        setTextFill(paye == 1 ? Color.GREEN : Color.RED); // Optional: Use color to differentiate
                    } else {
                        setText(null);
                    }
                }
            }
        });


        ActionsTableColumn.setCellFactory(param -> new TableCell<AppointmentDetails, Void>() {
            private final Button ordonnanceTestButton = new Button("Test prescription");
            private final Button ordonnanceVisiteButton = new Button("Medication prescription");
            private final Button reporterButton = new Button("Reschedule");
            private final Button annulerButton = new Button("Cancel");

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    AppointmentDetails appointment = getTableRow().getItem();
                    if (appointment != null) {
                        checkAndUpdateStatus(appointment);

                        ordonnanceTestButton.getStyleClass().add("button-action");
                        ordonnanceVisiteButton.getStyleClass().add("button-action");
                        reporterButton.getStyleClass().add("button-action");
                        annulerButton.getStyleClass().add("button-action");

                        ordonnanceTestButton.getStyleClass().add("button-ordonnance");
                        ordonnanceVisiteButton.getStyleClass().add("button-ordonnance");
                        reporterButton.getStyleClass().add("button-reporter");
                        annulerButton.getStyleClass().add("button-annuler");

                        ordonnanceTestButton.setOnAction(event -> handleOrdonnanceTest(appointment));
                        ordonnanceVisiteButton.setOnAction(event -> handleOrdonnanceVisite(appointment));
                        reporterButton.setOnAction(event -> handleReporter(appointment));
                        annulerButton.setOnAction(event -> handleAnnuler(appointment,ordonnanceTestButton,ordonnanceVisiteButton,reporterButton,annulerButton));

                        updateButtonStates(appointment, ordonnanceTestButton, ordonnanceVisiteButton, reporterButton, annulerButton);

                        HBox hBox = new HBox(10);
//                        hBox.setAlignment(Pos.CENTER);
                        hBox.getChildren().addAll(ordonnanceTestButton, ordonnanceVisiteButton, reporterButton, annulerButton);

                        setGraphic(hBox);
                    }
                }
            }
        });


        FilteredList<AppointmentDetails> filteredAppointments = new FilteredList<>(appointments,b->true);
        SearchFieldText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAppointments.setPredicate(appointment -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;  // No filter applied when search field is empty
                }
                String searchKeyword = newValue.toLowerCase();

                // Safe null check for ServiceName
                String serviceName = appointment.getServiceName() != null ? appointment.getServiceName().toLowerCase() : "";
                return appointment.getPatientFullName().toLowerCase().contains(searchKeyword) ||
                        appointment.getDoctorFullName().toLowerCase().contains(searchKeyword) ||
                        appointment.getStatus().toLowerCase().contains(searchKeyword) ||
                        serviceName.contains(searchKeyword);
            });

            AppointmentTableView.refresh();
            updatePagination(filteredAppointments);
        });

        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredAppointments.setPredicate(appointment -> {
                if (newValue == null || newValue.toString().isEmpty()) {
                    return true;
                }
                LocalDate selectedDate = newValue;
                return appointment.getFormattedDate().equals(selectedDate.toString());
            });
            AppointmentTableView.refresh();
            updatePagination(filteredAppointments);
        });

        SortedList<AppointmentDetails> sortedAppointments = new SortedList<>(filteredAppointments);
        sortedAppointments.comparatorProperty().bind(AppointmentTableView.comparatorProperty());

        // Set up pagination
        pagination.setPageFactory(pageIndex -> {
            updateTableView(sortedAppointments, pageIndex);
            return new VBox(); // Dummy node, required by Pagination
        });

        updatePagination(filteredAppointments);

    }

    private void updateTableView(SortedList<AppointmentDetails> sortedAppointments, int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, sortedAppointments.size());
        ObservableList<AppointmentDetails> pageData = FXCollections.observableArrayList(sortedAppointments.subList(fromIndex, toIndex));
        AppointmentTableView.setItems(pageData);
    }

//    private void updatePagination(FilteredList<AppointmentDetails> filteredAppointments) {
//        int totalItems = filteredAppointments.size();
//        int totalPages = (int) Math.ceil((double) totalItems / rowsPerPage);
//
//        pagination.setPageCount(totalPages > 0 ? totalPages : 1);
//        pagination.setCurrentPageIndex(0);
//        updateTableView(new SortedList<>(filteredAppointments), pagination.getCurrentPageIndex());
//    }

    private void updatePagination(FilteredList<AppointmentDetails> filteredAppointments) {
        int totalItems = filteredAppointments.size();
        int totalPages = (int) Math.ceil((double) totalItems / rowsPerPage);

        pagination.setPageCount(totalPages > 0 ? totalPages : 1);
        int currentPageIndex = pagination.getCurrentPageIndex();
        updateTableView(new SortedList<>(filteredAppointments), currentPageIndex);
        pagination.setCurrentPageIndex(currentPageIndex);
    }





    private void handleOrdonnanceTest(AppointmentDetails appointment) {
        System.out.println("Ordonnance Test clicked for: " + appointment.getIDPatient());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/doctorappointments/prescription-test-form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Test Prescription Form" + appointment.getIDPatient());
            stage.setScene(new Scene(root, 1100, 700));
            stage.show();

            TestFormController controller = fxmlLoader.getController();
            controller.setAppointmentID(appointment.getIDAppointment());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleOrdonnanceVisite(AppointmentDetails appointment) {
        System.out.println("Ordonnance Visite clicked for: " + appointment.getIDPatient());

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/doctorappointments/prescription-medical-form.fxml"));

            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Medical Prescription Form" + appointment.getIDPatient());
            stage.setScene(new Scene(root, 1100, 700));
            stage.show();

            MedicationFormController controller = fxmlLoader.getController();
            controller.setAppointmentID(appointment.getIDAppointment());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleReporter(AppointmentDetails appointment) {
        System.out.println("Reporter clicked for: " + appointment.getIDPatient());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/doctorappointments/Postpone-Appointment.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Postpone appointment " + appointment.getIDPatient());
            stage.setScene(new Scene(root, 1100, 700));
            stage.show();

            PostponeAppointmentController controller = fxmlLoader.getController();
            controller.setInterfaceDetails(
                    appointment.getIDAppointment(),
                    appointment.getAppointmentDate(),
                    appointment.getDoctorFullName(),
                    appointment.getPatientFullName()
            );
            controller.setCallback(() -> {
                refreshAppointments();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handleAnnuler(AppointmentDetails appointment, Button ordonnanceTestButton, Button ordonnanceVisiteButton, Button reporterButton, Button annulerButton) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel Appointment");
        alert.setContentText("Are you sure you would like to cancel this appointment?");

        ButtonType confirmButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == confirmButton) {
                appointment.setStatus("canceled");
                AppointmentService.cancelAppointment(appointment.getIDAppointment());
                int doctorId = appointment.getIDDoctor();
                String formattedDate = appointment.getFormattedDate();
                LocalDate appointmentDate = LocalDate.parse(formattedDate);
                String appointmentTime = appointment.getFormattedTime();
                AppointmentService.updateDoctorAvailability(doctorId, appointmentDate, appointmentTime, true);
                refreshTable();
                updateButtonStates(appointment, ordonnanceTestButton, ordonnanceVisiteButton, reporterButton, annulerButton);
            }
        });
    }


    public void handleCreateMedicalPrescription() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("prescription-medical-form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Medical Prescription Form");
            stage.setScene(new Scene(root, 1100, 700));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCreateTestPrescription() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("prescription-test-form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Test Prescription Form");
            stage.setScene(new Scene(root, 1100, 700));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void refreshTable() {
        AppointmentTableView.refresh();
    }


    private void checkAndUpdateStatus(AppointmentDetails appointment) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime appointmentDateTime = appointment.getAppointmentDate().toLocalDateTime();
        LocalDateTime oneHourAfterAppointment = appointmentDateTime.plusHours(1);

        if ("canceled".equalsIgnoreCase(appointment.getStatus())) {
            return;
        }
        if (currentDateTime.isAfter(oneHourAfterAppointment)) {
            appointment.setStatus("completed");
        } else if (currentDateTime.isBefore(appointmentDateTime) || currentDateTime.equals(appointmentDateTime)) {
            appointment.setStatus("scheduled");
        } else if (currentDateTime.isAfter(appointmentDateTime) && currentDateTime.isBefore(oneHourAfterAppointment)) {
            appointment.setStatus("pending");
        }
    }



    private void updateButtonStates(AppointmentDetails appointment, Button ordonnanceTestButton, Button ordonnanceVisiteButton, Button reporterButton, Button annulerButton) {
        String status = appointment.getStatus();
        switch (status) {
            case "completed":
                ordonnanceTestButton.setDisable(false);
                ordonnanceVisiteButton.setDisable(false);
                reporterButton.setDisable(true);
                annulerButton.setDisable(true);
                break;
            case "pending":
                ordonnanceTestButton.setDisable(false);
                ordonnanceVisiteButton.setDisable(false);
                reporterButton.setDisable(true);
                annulerButton.setDisable(true);
                break;
            case "scheduled":
                ordonnanceTestButton.setDisable(true);
                ordonnanceVisiteButton.setDisable(true);
                reporterButton.setDisable(false);
                annulerButton.setDisable(false);
                break;
            case "canceled":
                ordonnanceTestButton.setDisable(true);
                ordonnanceVisiteButton.setDisable(true);
                reporterButton.setDisable(true);
                annulerButton.setDisable(true);
                break;
            default:
                ordonnanceTestButton.setDisable(true);
                ordonnanceVisiteButton.setDisable(true);
                reporterButton.setDisable(true);
                annulerButton.setDisable(true);
        }
    }





//    private void refreshAppointments() {
//        // Get the updated list of appointments from the database or service
//        List<AppointmentDetails> appointmentsFromDB = AppointmentService.getAllAppointments();
//        appointments.clear(); // Clear current appointments
//        appointments.addAll(appointmentsFromDB); // Add the updated appointments
//    }


    private void refreshAppointments() {
        List<AppointmentDetails> appointmentsFromDB = AppointmentService.getAllAppointments();
        appointments.clear();
        appointments.addAll(appointmentsFromDB);
        updatePagination(new FilteredList<>(appointments, b -> true));
    }
}


