package controller;

import datastorage.DAOFactory;
import datastorage.TreatmentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Caregiver;
import model.Patient;
import model.Treatment;
import utils.DateConverter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class NewTreatmentController {
    @FXML
    private Label patientSurname;
    @FXML
    private Label patientFirstname;
    @FXML
    private Label caregiverSurname;
    @FXML
    private Label caregiverFirstname;
    @FXML
    private Label caregiverPhone;
    @FXML
    private TextField txtBegin;
    @FXML
    private TextField txtEnd;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextArea taRemarks;
    @FXML
    private DatePicker datepicker;

    @FXML
    Button btnAdd;
    @FXML
    Button cancelButton;

    private AllTreatmentController controller;
    private Patient patient;
    private Caregiver caregiver;
    private Stage stage;

    public void initialize(AllTreatmentController controller, Stage stage, Patient patient, Caregiver caregiver) {
        this.controller = controller;
        this.patient = patient;
        this.caregiver = caregiver;
        this.stage = stage;
        showPatientData();
        showCaregiverData();
    }

    private void showPatientData(){
        this.patientFirstname.setText(patient.getFirstName());
        this.patientSurname.setText(patient.getSurname());
    }

    private void showCaregiverData(){
        this.caregiverFirstname.setText(caregiver.getFirstName());
        this.caregiverSurname.setText(caregiver.getSurname());
        this.caregiverPhone.setText(caregiver.getPhone());
    }

    @FXML
    public void handleAdd(){
        LocalDate date = this.datepicker.getValue();
        String s_begin = txtBegin.getText();
        LocalTime begin = DateConverter.convertStringToLocalTime(txtBegin.getText());
        LocalTime end = DateConverter.convertStringToLocalTime(txtEnd.getText());
        String description = txtDescription.getText();
        String remarks = taRemarks.getText();
        Treatment treatment = new Treatment(patient.getPid(), caregiver.getCid(), date,
                begin, end, description, remarks);
        createTreatment(treatment);
        controller.readAllAndShowInTableView();
        stage.close();
    }

    private void createTreatment(Treatment treatment) {
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.create(treatment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancel(){
        stage.close();
    }
}