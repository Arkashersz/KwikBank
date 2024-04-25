module com.jmc.kwikbank.kwikbank {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jmc.kwikbank.kwikbank to javafx.fxml;
    exports com.jmc.kwikbank.kwikbank;
}