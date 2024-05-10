// DepositCellFactory.java

package com.jmc.kwikbank.Views;

import com.jmc.kwikbank.Controllers.Admin.DepositCellController;
import com.jmc.kwikbank.Models.Client;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;

public class DepositCellFactory extends ListCell<Client> {

    private final ObservableList<Client> searchResults;
    private final ListView<Client> resultListView;

    public DepositCellFactory(ObservableList<Client> searchResults, ListView<Client> resultListView) {
        this.searchResults = searchResults;
        this.resultListView = resultListView;
    }

    @Override
    protected void updateItem(Client client, boolean empty) {
        super.updateItem(client, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/DepositCell.fxml"));
            DepositCellController controller = new DepositCellController(client);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Adicionar evento para o botão "Apagar"
            controller.deletar_btn.setOnAction(event -> {
                searchResults.remove(client); // Remove o cliente da lista local
                resultListView.setItems(searchResults); // Atualiza a lista exibida
            });
        }
    }
}
