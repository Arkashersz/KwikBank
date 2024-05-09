package com.jmc.kwikbank.Controllers.Client;

import com.jmc.kwikbank.Models.Model;
import com.jmc.kwikbank.Views.TransactionCellFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label savings_bal;
    public Label savings_acc_num;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        initLatestTransactionList();
        transaction_listview.setItems(Model.getInstance().getLatestTransactions());
        transaction_listview.setCellFactory(e -> new TransactionCellFactory());
    }

    private void bindData() {
        user_name.textProperty().bind(
                Bindings.concat("Olá, ").concat(
                        Model.getInstance().getClient().firstNameProperty()
                )
        );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        login_date.setText("Hoje é, " + LocalDate.now().format(formatter));

        checking_bal.textProperty().bind(
                Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().asString()
        );
        checking_acc_num.textProperty().bind(
                Model.getInstance().getClient().checkingAccountProperty().get().accountNumberProperty()
        );
        savings_bal.textProperty().bind(
                Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString()
        );
        savings_acc_num.textProperty().bind(
                Model.getInstance().getClient().savingsAccountProperty().get().accountNumberProperty()
        );
    }

    private void initLatestTransactionList() {
        if (Model.getInstance().getLatestTransactions().isEmpty());
        Model.getInstance().setLatestTransactions();
    }
}
