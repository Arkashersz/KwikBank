package com.jmc.kwikbank.Controllers;

import com.jmc.kwikbank.Models.Model;
import com.jmc.kwikbank.Views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;
    public Label id_lbl;
    public TextField id_fld;
    public Label password_lbl;
    public TextField password_fld;
    public Button login_btn;
    public Label error_lbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENTE, AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable -> setAcc_selector());
        login_btn.setOnAction(event -> onLogin());
    }

    private void onLogin() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENTE) {
            // Evaluate Login Credentials
            Model.getInstance().evaluateClientCred(id_fld.getText(), password_fld.getText());
            if (Model.getInstance().getClientLoginSuccessFlag()){
                Model.getInstance().getViewFactory().showClientWindow();
                // Close login stage
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                id_fld.setText("");
                password_fld.setText("");
                error_lbl.setText("Dados incorretos!");
            }
        } else {
            // Evaluate Admin Login Credentials
            Model.getInstance().evaluateAdminCred(id_fld.getText(), password_fld.getText());
            if (Model.getInstance().getAdminLoginSuccessFlag()){
                Model.getInstance().getViewFactory().showAdminWindow();
                // Close Login stage
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                id_fld.setText("");
                password_fld.setText("");
                error_lbl.setText("Dados incorretos!");
            }
        }
    }

    private void setAcc_selector() {
        Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue());
        //Change Payee Address label accordingly
        if (acc_selector.getValue() == AccountType.ADMIN) {
            id_lbl.setText("Matrícula:");
        } else {
            id_lbl.setText("Identificação:");
        }
    }
}
