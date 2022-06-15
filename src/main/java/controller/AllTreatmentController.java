package controller;

import datastorage.CaregiverDAO;
import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Caregiver;
import model.Patient;
import model.Treatment;
import datastorage.DAOFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllTreatmentController {
    @FXML
    private TableView<Treatment> tableView;
    @FXML
    private TableColumn<Treatment, Integer> colID;
    @FXML
    private TableColumn<Treatment, Integer> colPid;
    @FXML
    private TableColumn<Treatment, Integer> colCid;
    @FXML
    private TableColumn<Treatment, String> colDate;
    @FXML
    private TableColumn<Treatment, String> colBegin;
    @FXML
    private TableColumn<Treatment, String> colEnd;
    @FXML
    private TableColumn<Treatment, String> colDescription;
    @FXML
    private ComboBox<String> patientBox;
    @FXML
    private ComboBox<String> caregiverBox;
    @FXML
    private Button btnNewTreatment;
    @FXML
    private Button btnDelete;

    private ObservableList<Treatment> tableviewContent =
            FXCollections.observableArrayList();
    private TreatmentDAO dao;
    private ObservableList<String> myPatientBoxData =
            FXCollections.observableArrayList();
    private ObservableList<String> myCaregiverBoxData =
            FXCollections.observableArrayList();
    private ArrayList<Patient> patientList;
    private ArrayList<Caregiver> caregiverList;
    private Main main;

    public void initialize() {
        readAllAndShowInTableView();
        createPatientBoxData();
        createCaregiverBoxData();
        patientBox.setItems(myPatientBoxData);
        patientBox.getSelectionModel().select(0);
        caregiverBox.setItems(myCaregiverBoxData);
        caregiverBox.getSelectionModel().select(0);

        this.colID.setCellValueFactory(new PropertyValueFactory<Treatment, Integer>("tid"));
        this.colPid.setCellValueFactory(new PropertyValueFactory<Treatment, Integer>("pid"));
        this.colCid.setCellValueFactory(new PropertyValueFactory<Treatment, Integer>("cid"));
        this.colDate.setCellValueFactory(new PropertyValueFactory<Treatment, String>("date"));
        this.colBegin.setCellValueFactory(new PropertyValueFactory<Treatment, String>("begin"));
        this.colEnd.setCellValueFactory(new PropertyValueFactory<Treatment, String>("end"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory<Treatment, String>("description"));
        this.tableView.setItems(this.tableviewContent);
    }

    public void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        patientBox.getSelectionModel().select(0);
        caregiverBox.getSelectionModel().select(0);
        this.dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        List<Treatment> allTreatments;
        try {
            allTreatments = dao.readAll();
            this.tableviewContent.addAll(allTreatments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createPatientBoxData(){
        PatientDAO dao = DAOFactory.getDAOFactory().createPatientDAO();
        try {
            patientList = (ArrayList<Patient>) dao.readAll();
            this.myPatientBoxData.add("alle");
            for (Patient patient: patientList) {
                this.myPatientBoxData.add(patient.getSurname());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void createCaregiverBoxData(){
        CaregiverDAO dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        try {
            caregiverList = (ArrayList<Caregiver>) dao.readAll();
            this.myCaregiverBoxData.add("alle");
            for (Caregiver caregiver: caregiverList) {
                this.myCaregiverBoxData.add(caregiver.getSurname());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    @FXML
    public void handleComboBox(){
        String p = this.patientBox.getSelectionModel().getSelectedItem();
        String c = this.caregiverBox.getSelectionModel().getSelectedItem();
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        List<Treatment> allTreatments;
        if(p.equals("alle") && c.equals(("alle"))){
            try {
                allTreatments= this.dao.readAll();
                this.tableviewContent.addAll(allTreatments);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
        else if(p.equals("alle") && !c.equals("alle")){
            Caregiver caregiver = searchCaregiverInList(c);
            if(caregiver !=null){
                try {
                    allTreatments = dao.readTreatmentsByCid(caregiver.getCid());
                    this.tableviewContent.addAll(allTreatments);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(!p.equals("alle") && c.equals("alle")){
            Patient patient = searchPatientInList(p);
            if(patient !=null){
                try {
                    allTreatments = dao.readTreatmentsByPid(patient.getPid());
                    this.tableviewContent.addAll(allTreatments);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            Patient patient = searchPatientInList(p);
            Caregiver caregiver = searchCaregiverInList(c);
            if(patient !=null && caregiver !=null) {
                try {
                    allTreatments = dao.readTreatmentsByBothIds(caregiver.getCid(), patient.getPid());
                    this.tableviewContent.addAll(allTreatments);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Patient searchPatientInList(String surname){
        for (int i =0; i<this.patientList.size();i++){
            if(this.patientList.get(i).getSurname().equals(surname)){
                return this.patientList.get(i);
            }
        }
        return null;
    }

    private Caregiver searchCaregiverInList(String surname){
        for (int i =0; i<this.caregiverList.size();i++){
            if(this.caregiverList.get(i).getSurname().equals(surname)){
                return this.caregiverList.get(i);
            }
        }
        return null;
    }

    @FXML
    public void handleDelete(){
        int index = this.tableView.getSelectionModel().getSelectedIndex();
        Treatment t = this.tableviewContent.remove(index);
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.deleteById(t.getTid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleNewTreatment() {
        try{
            String p = this.patientBox.getSelectionModel().getSelectedItem();
            String c = this.caregiverBox.getSelectionModel().getSelectedItem();
            Patient patient = searchPatientInList(p);
            Caregiver caregiver = searchCaregiverInList(c);
            newTreatmentWindow(patient, caregiver);
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Patient für die Behandlung fehlt!");
            alert.setContentText("Wählen Sie über die Combobox einen Patienten aus!");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleMouseClick(){
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (tableView.getSelectionModel().getSelectedItem() != null)) {
                int index = this.tableView.getSelectionModel().getSelectedIndex();
                Treatment treatment = this.tableviewContent.get(index);

                treatmentWindow(treatment);
            }
        });
    }

    public void newTreatmentWindow(Patient patient, Caregiver caregiver){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/NewTreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            //da die primaryStage noch im Hintergrund bleiben soll
            Stage stage = new Stage();

            NewTreatmentController controller = loader.getController();
            controller.initialize(this, stage, patient, caregiver);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void treatmentWindow(Treatment treatment){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/TreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            //da die primaryStage noch im Hintergrund bleiben soll
            Stage stage = new Stage();
            TreatmentController controller = loader.getController();

            controller.initializeController(this, stage, treatment);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}