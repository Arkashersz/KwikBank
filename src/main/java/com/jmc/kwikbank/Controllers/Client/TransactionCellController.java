package com.jmc.kwikbank.Controllers.Client;

import com.jmc.kwikbank.Models.Model;
import com.jmc.kwikbank.Models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {

    public FontAwesomeIconView in_icon;
    public FontAwesomeIconView out_icon;
    public Label trans_date_lbl;
    public Label sender_lbl;
    public Label receive_lbl;
    public Label amount_lbl;
    public Button message_btn;

    private final Transaction transaction;

    public  TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sender_lbl.textProperty().bind(transaction.senderProperty());
        receive_lbl.textProperty().bind(transaction.receiverProperty());
        amount_lbl.textProperty().bind(transaction.amountProperty().asString("R$%.2f"));
        amount_lbl.setPrefWidth(80); // Defina a largura fixa desejada
        amount_lbl.setMaxWidth(80); // Garanta que a largura máxima também seja fixa para evitar que o texto seja cortado
        amount_lbl.setMinWidth(80); // Garanta que a largura mínima também seja fixa para evitar que o texto seja cortado
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        trans_date_lbl.textProperty().bind(transaction.dateProperty().asString().map(date -> {
            LocalDate localDate = LocalDate.parse(date);
            return dateFormatter.format(localDate);
        }));
        message_btn.setOnAction(event -> Model.getInstance().getViewFactory().showMessageWindow(transaction.senderProperty().get(), transaction.messageProperty().get()));
        transactionIcons();
    }

    public void transactionIcons() {
        if (transaction.senderProperty().get().equals(Model.getInstance().getClient().payeeAddressProperty().get())){
            in_icon.setFill((Color.rgb(240, 240, 240)));
            out_icon.setFill(Color.RED);
        } else {
            in_icon.setFill(Color.GREEN);
            out_icon.setFill(Color.rgb(240, 240, 240));
        }
    }
}
