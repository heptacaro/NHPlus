package controller;

import datastorage.*;
import datastorage.CaregiverDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.*;
import model.Caregiver;
import model.Caregiver;
import model.Caregiver;
import utils.DateConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AllCaregiverController {
    @FXML
    private TableView<Caregiver> tableView;
    @FXML
    private TableColumn<Caregiver, Integer> colID;
    @FXML
    private TableColumn<Caregiver, String> colFirstName;
    @FXML
    private TableColumn<Caregiver, String> colSurname;
    @FXML
    private TableColumn<Caregiver, String> colPhone;
    @FXML
    private TableColumn<model.Caregiver, String> colUsername;

    @FXML
    Button btnDelete;
    @FXML
    Button btnAdd;
    @FXML
    TextField txtSurname;
    @FXML
    TextField txtFirstname;
    @FXML
    TextField txtPhone;
    @FXML
    javafx.scene.control.TextField loginUsername;
    @FXML
    javafx.scene.control.TextField loginPassword;

    private ObservableList<Caregiver> tableviewContent = FXCollections.observableArrayList();
    private CaregiverDAO dao;

    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.
     */
    public void initialize() {
        readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<Caregiver, Integer>("cid"));

        this.colFirstName.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("firstName"));
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colSurname.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colPhone.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("phone"));
        this.colPhone.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colUsername.setCellValueFactory(new PropertyValueFactory<model.Caregiver, String>("username"));
        this.colUsername.setCellFactory(TextFieldTableCell.forTableColumn());

        this.tableView.setItems(this.tableviewContent);
    }

    /**
     * handles new firstname value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Caregiver, String> event){
        event.getRowValue().setFirstName(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new surname value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Caregiver, String> event){
        event.getRowValue().setSurname(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new phone value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditPhone(TableColumn.CellEditEvent<Caregiver, String> event){
        event.getRowValue().setPhone(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new username value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditUsername(TableColumn.CellEditEvent<model.Caregiver, String> event){
        event.getRowValue().setLoginUsername(event.getNewValue());
        doUpdate(event);
    }

    /**
     * updates a caregiver by calling the update-Method in the {@link CaregiverDAO}
     * @param t row to be updated by the user (includes the caregiver)
     */
    private void doUpdate(TableColumn.CellEditEvent<Caregiver, String> t) {
        try {
            dao.update(t.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readAll in {@link CaregiverDAO} and shows caregivers in the table
     */
    private void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        try {
            this.tableviewContent.addAll(this.dao.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a delete-click-event. Calls the delete methods in the {@link CaregiverDAO} and {@link TreatmentDAO}
     */
    @FXML
    public void handleDeleteRow() {
        TreatmentDAO tDao = DAOFactory.getDAOFactory().createTreatmentDAO();
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        try {
            tDao.deleteByPid(selectedItem.getCid());
            dao.deleteById(selectedItem.getCid());
            this.tableView.getItems().remove(selectedItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a add-click-event. Creates a caregiver and calls the create method in the {@link CaregiverDAO}
     */
    @FXML
    public void handleAdd() {
        String surname = this.txtSurname.getText();
        String firstname = this.txtFirstname.getText();
        String phone = this.txtPhone.getText();
        try {
            Caregiver c = new Caregiver(firstname, surname, phone);
            dao.create(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextfields();
    }

    /**
     * removes content from all textfields
     */
    private void clearTextfields() {
        this.txtFirstname.clear();
        this.txtSurname.clear();
        this.txtPhone.clear();
    }
}
