package com.jmc.kwikbank.Controllers.Client;

import com.jmc.kwikbank.Models.Client;
import com.jmc.kwikbank.Models.Model;
import com.jmc.kwikbank.Views.TransactionCellFactory;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import com.jmc.kwikbank.Models.Converter;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label savings_bal;
    public Label savings_acc_num;
    public Label expense_lbl;
    public ListView transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;
    public Button desblock1_btn;
    public Button desblock_btn;
    public Label checking_bal1;
    public Label savings_bal1;
    public Text name_complet1;
    public Text name_complet2;
    public AnchorPane card1;
    public ImageView image_fundo1;
    public AnchorPane card2;
    public ImageView image_fundo2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        initLatestTransactionsList();
        int maxTransactionsToShow = 10; // Defina o número máximo de transações que você deseja mostrar
        Model.getInstance().setLatestTransactions(maxTransactionsToShow);
        transaction_listview.setItems(Model.getInstance().getLatestTransactions());
        transaction_listview.setCellFactory(e -> new TransactionCellFactory());

        Converter converter = new Converter();

        checking_bal.setPrefWidth(200); // Defina a largura fixa desejada
        savings_bal.setPrefWidth(200); // Defina a largura fixa desejada

        checking_bal.textProperty().bind(
                Bindings.createStringBinding(() -> new Converter().toString(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().get()),
                        Model.getInstance().getClient().checkingAccountProperty())
        );
        savings_bal.textProperty().bind(
                Bindings.createStringBinding(() -> new Converter().toString(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().get()),
                        Model.getInstance().getClient().savingsAccountProperty())
        );

        // Configuração da transição de fade para as Labels
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(100), checking_bal);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        // Configuração inicial: Ocultar a label com asteriscos
        checking_bal1.setVisible(false);

        // Ação para o botão desblock_btn
        desblock_btn.setOnAction(event -> {
            if (!checking_bal.isVisible()) {
                // Se checking_bal não estiver visível, mostra e esconde checking_bal1
                fadeTransition.setOnFinished(e -> {
                    checking_bal.setVisible(true);
                    checking_bal1.setVisible(false);
                });
                fadeTransition.play();
                desblock_btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.UNLOCK));
            } else {
                // Se checking_bal estiver visível, esconde e mostra checking_bal1
                fadeTransition.stop();
                checking_bal.setVisible(false);
                checking_bal1.setVisible(true);
                desblock_btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.LOCK));
            }
        });

        // Configuração da transição de fade para savings_bal
        FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(100), savings_bal);
        fadeTransition1.setFromValue(0.0);
        fadeTransition1.setToValue(1.0);

        // Configuração inicial: Ocultar a label com asteriscos
        savings_bal1.setVisible(false);

        // Ação para o botão desblock1_btn
        desblock1_btn.setOnAction(event -> {
            if (!savings_bal.isVisible()) {
                // Se savings_bal não estiver visível, mostra e esconde savings_bal1
                fadeTransition1.setOnFinished(e -> {
                    savings_bal.setVisible(true);
                    savings_bal1.setVisible(false);
                });
                fadeTransition1.play();
                desblock1_btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.UNLOCK));
            } else {
                // Se savings_bal estiver visível, esconde e mostra savings_bal1
                fadeTransition1.stop();
                savings_bal.setVisible(false);
                savings_bal1.setVisible(true);
                desblock1_btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.LOCK));
            }
        });

        // Adicionando estilos aos botões desblock_btn e desblock1_btn
        desblock_btn.getStyleClass().addAll("unlock-button");
        desblock1_btn.getStyleClass().addAll("unlock-button");

        desblock_btn.setMinWidth(100);
        desblock_btn.setMinHeight(30);
        desblock1_btn.setMinWidth(100);
        desblock1_btn.setMinHeight(30);

        // Configuração do botão send_money_btn
        send_money_btn.setOnAction(event -> onSendMoney());

        // Chama o método para configurar o texto das labels com asteriscos
        updateAsterisksLabel(checking_bal, checking_bal1);
        updateAsterisksLabel(savings_bal, savings_bal1);
    }

    private void updateAsterisksLabel(Label originalLabel, Label asterisksLabel) {
        String originalText = originalLabel.getText();
        StringBuilder asterisks = new StringBuilder();
        for (char c : originalText.toCharArray()) {
            if (Character.isDigit(c)) {
                asterisks.append("*");
            } else {
                // Se o caractere não for um dígito, simplesmente ignore
            }
        }
        asterisksLabel.setText(asterisks.toString());
    }



    private void bindData() {
        Client client = Model.getInstance().getClient();

        // Concatena o primeiro nome e o último nome do cliente
        String fullName = client.firstNameProperty().get() + " " + client.lastNameProperty().get();

        // Define o texto nas Labels name_complet1 e name_complet2
        name_complet1.setText(fullName);
        name_complet2.setText(fullName);

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

    private void initLatestTransactionsList() {
        if (Model.getInstance().getLatestTransactions().isEmpty()){
            Model.getInstance().setLatestTransactions(5);
        }
    }

    private void onSendMoney() {
        String receiver = payee_fld.getText();
        double amount = Double.parseDouble(amount_fld.getText());
        String message = message_fld.getText();
        String sender = Model.getInstance().getClient().payeeAddressProperty().get();
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().searchClient(receiver);
        try {
            if (resultSet.isBeforeFirst()){
                Model.getInstance().getDatabaseDriver().updateBalance(receiver, amount, "ADD");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        Model.getInstance().getDatabaseDriver().updateBalance(sender, amount, "SUB");
        Model.getInstance().getClient().savingsAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(sender));
        Model.getInstance().getDatabaseDriver().newTransaction(sender, receiver, amount, message);
        payee_fld.setText("");
        amount_fld.setText("");
        message_fld.setText("");
    }
}
