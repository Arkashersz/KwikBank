package com.jmc.kwikbank.Controllers.Admin;

import com.jmc.kwikbank.Models.Client;
import com.jmc.kwikbank.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {

    public Label fName_lbl;
    public Label lName_lbl;
    public Label pAddress_lbl;
    public Label ch_acc_lbl;
    public Label sv_acc_lbl;
    public Label date_lbl;
    public Button delete_btn;

    private final Client client;

    public ClientCellController(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fName_lbl.textProperty().bind(client.firstNameProperty());
        lName_lbl.textProperty().bind(client.lastNameProperty());
        pAddress_lbl.textProperty().bind(client.payeeAddressProperty());
        ch_acc_lbl.textProperty().bind(client.checkingAccountProperty().asString());
        sv_acc_lbl.textProperty().bind(client.checkingAccountProperty().asString());
        date_lbl.textProperty().bind(client.dateCreatedProperty().asString());
        delete_btn.setOnAction(event -> onDeleteClient());
    }

    private void onDeleteClient() {
        // Remover o cliente da lista de clientes
        Model.getInstance().getClients().remove(client);

        // Remover o cliente do banco de dados
        Model.getInstance().getDatabaseDriver().deleteClient(client.payeeAddressProperty().get());
    }

}