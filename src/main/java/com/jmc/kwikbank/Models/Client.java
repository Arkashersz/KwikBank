package com.jmc.kwikbank.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAddress;
    private final ObjectProperty<Account> checkingAccount;
    private final ObjectProperty<Account> savingsAccount;
    private final ObjectProperty<LocalDate> dateCreated;

    public Client(String fName, String lName, String payeeAddress, Account checkingAccount, Account savingsAccount, LocalDate date) {
        this.firstName = new SimpleStringProperty(this, "First Name", fName);
        this.lastName = new SimpleStringProperty(this, "Last Name", lName);
        this.payeeAddress = new SimpleStringProperty(this, "Payee Address", payeeAddress);
        this.checkingAccount = new SimpleObjectProperty<>(this, "Checking Account", checkingAccount);
        this.savingsAccount = new SimpleObjectProperty<>(this, "Saving Account", savingsAccount);
        this.dateCreated = new SimpleObjectProperty<>(this, "Date Created", date);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public StringProperty payeeAddressProperty() {
        return payeeAddress;
    }
    public ObjectProperty<Account> checkingAccountProperty() {
        return checkingAccount;
    }
    public ObjectProperty<Account> savingsAccountProperty() {
        return savingsAccount;
    }
    public ObjectProperty<LocalDate> dateCreatedProperty() {
        return dateCreated;
    }
}
