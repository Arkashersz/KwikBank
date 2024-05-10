package com.jmc.kwikbank.Controllers.Admin;

import com.jmc.kwikbank.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Window;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField password_fld;
    public Label pAddress_lbl;
    public CheckBox ch_acc_box;
    public TextField ch_amount_fld;
    public CheckBox sv_acc_box;
    public TextField sv_amount_fld;
    public Button create_client_btn;
    public CheckBox pAddress_box;
    public Label error_lbl;

    private String payeeAddress;
    private boolean createCheckingAccountFlag = false;
    private boolean createSavingsAccountFlag = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_client_btn.setOnAction(event -> createClient());
        pAddress_box.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                payeeAddress = createPayeeAddress();
                onCreatePayeeAddress();
            }
        });
        ch_acc_box.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                createCheckingAccountFlag = true;
            }
        });
        sv_acc_box.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                createSavingsAccountFlag = true;
            }
        });

    }

    private void createClient() {
        if(createCheckingAccountFlag) {
            createAccount("Checking");
        }

        if (createSavingsAccountFlag) {
            createAccount("Savings");
        }

        String fName = fName_fld.getText();
        String lName = lName_fld.getText();
        String password = password_fld.getText();
        Model.getInstance().getDatabaseDriver().createClient(fName, lName, payeeAddress, password, LocalDate.now());
        showAlert("KwikBank", "Cliente cadastrado com sucesso!", create_client_btn.getScene().getWindow());
        emptyFields();
    }

    private void createAccount(String accountType) {
        double balance = Double.parseDouble(ch_amount_fld.getText());
        // Criar o número da conta
        String firstSection = "3201";
        String lastSection = Integer.toString((new Random()).nextInt(999) + 1000);
        String accountNumber = firstSection + " " + lastSection;
        // Criando a conta corrente ou poupança
        if(accountType.equals("Checking")) {
            Model.getInstance().getDatabaseDriver().createCheckingAccount(payeeAddress, accountNumber, 10, balance);
        } else {
            Model.getInstance().getDatabaseDriver().createSavingsAccount(payeeAddress, accountNumber, 2000, balance);
        }
    }

    private void onCreatePayeeAddress() {
        if (fName_fld.getText() != null & lName_fld.getText() != null) {
            pAddress_lbl.setText(payeeAddress);
        }
    }

    private String createPayeeAddress() {
        int id = Model.getInstance().getDatabaseDriver().getLastClientsId() + 1;
        char fChar = Character.toLowerCase(fName_fld.getText().charAt(0));
        return "@"+fChar+lName_fld.getText()+id;
    }

    private void emptyFields() {
        fName_fld.setText("");
        lName_fld.setText("");
        password_fld.setText("");
        pAddress_box.setSelected(false);
        pAddress_lbl.setText("");
        ch_acc_box.setSelected(false);
        ch_amount_fld.setText("");
        sv_acc_box.setSelected(false);
        sv_amount_fld.setText("");
    }

    private void showAlert(String title, String content, Window ownerWindow) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.initOwner(ownerWindow);
        alert.showAndWait();
    }
}
