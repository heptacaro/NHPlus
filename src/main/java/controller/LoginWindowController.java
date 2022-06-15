package controller;

import datastorage.CaregiverDAO;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Caregiver;

import java.awt.*;

public class LoginWindowController {
    @FXML
    TextField loginUsername;
    @FXML
    TextField loginPassword;
    @FXML
    Button btnSubmit;

    private datastorage.CaregiverDAO dao;
    private AllCaregiverController controller;
    private model.Caregiver caregiver;
    private Stage stage;

    public void initialize(AllCaregiverController controller, Stage stage, model.Caregiver caregiver) {
        this.controller = controller;
        this.stage = stage;
        this.caregiver = caregiver;
        showLoginData();
    }

    public void showLoginData() {
        this.loginUsername.setText(caregiver.getLoginUsername());
    }
}