module com.jmc.kwikbank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.jmc.kwikbank to javafx.fxml;
    exports com.jmc.kwikbank;
    exports com.jmc.kwikbank.Controllers;
    exports com.jmc.kwikbank.Controllers.Admin;
    exports com.jmc.kwikbank.Controllers.Client;
    exports com.jmc.kwikbank.Models;
    exports com.jmc.kwikbank.Views;
}
