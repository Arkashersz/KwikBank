package com.jmc.kwikbank.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SavingsAccount extends Account {
    //Limite de saque
    private final DoubleProperty withdrawalLimit;

    public SavingsAccount(String owner, String accountNumer, double balance, double withdrawalLimit) {
        super(owner, accountNumer, balance);
        this.withdrawalLimit = new SimpleDoubleProperty(this, "Withdrawal Limit", withdrawalLimit);
    }

    public DoubleProperty withdrawalLimit() {
        return withdrawalLimit;
    }

    @Override
    public String toString() {
        return accountNumberProperty().get();
    }
}
