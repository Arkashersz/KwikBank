package com.jmc.kwikbank.Controllers.Admin;

import com.jmc.kwikbank.Models.Client;
import com.jmc.kwikbank.Models.Model;
import com.jmc.kwikbank.Views.ClientCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController  implements Initializable {
    public ListView<Client> clients_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClientList();
        clients_listview.setItems(Model.getInstance().getClients());
        clients_listview.setCellFactory(e -> new ClientCellFactory());
    }

    private void initClientList() {
        if (Model.getInstance().getClients().isEmpty()){
            Model.getInstance().setClients();
        }
    }
}
