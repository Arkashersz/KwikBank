// DepositController.java

package com.jmc.kwikbank.Controllers.Admin;

import com.jmc.kwikbank.Models.Client;
import com.jmc.kwikbank.Models.Model;
import com.jmc.kwikbank.Views.DepositCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {

    public TextField pAddress_fld;
    public Button search_btn;
    public ListView<Client> result_listview;
    public TextField amount_fld;
    public Button deposit_btn;

    private ObservableList<Client> searchResults;
    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_btn.setOnAction(event -> onClientSearch());
        deposit_btn.setOnAction(event -> onDeposit());
    }

    private void onClientSearch() {
        // Armazenar os resultados da pesquisa em uma lista local
        searchResults = Model.getInstance().searchClients(pAddress_fld.getText());
        if (searchResults.isEmpty()) {
            // Exibir alerta se nenhum resultado for encontrado
            showAlert("KwikBank", "Nenhum cliente encontrado.", search_btn.getScene().getWindow());
        } else {
            result_listview.setItems(searchResults);
            // Passar a lista de resultados e a listView para o DepositCellFactory
            result_listview.setCellFactory(e -> new DepositCellFactory(searchResults, result_listview));
            client = searchResults.get(0);
        }
        // Limpar o campo de pesquisa
        pAddress_fld.setText("");
    }

    private void onDeposit() {
        double amount = Double.parseDouble(amount_fld.getText());
        double newBalance = amount + client.savingsAccountProperty().get().balanceProperty().get();
        if (amount_fld.getText() != null){
            Model.getInstance().getDatabaseDriver().depositSavings(client.payeeAddressProperty().get(), newBalance);
            showAlert("KwikBank", "O depósito foi feito com sucesso.", deposit_btn.getScene().getWindow());
        }
        emptyFields();
    }

    private void emptyFields() {
        pAddress_fld.setText("");
        amount_fld.setText("");
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
