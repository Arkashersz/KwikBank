package com.jmc.kwikbank.Models;

import javafx.util.StringConverter;

public class Converter extends StringConverter<Number> {

    @Override
    public String toString(Number object) {
        return String.format("R$ %,.2f", object.doubleValue());
    }

    @Override
    public Number fromString(String string) {
        // Implementação opcional, depende se você precisar converter de volta para um número
        return null;
    }
}
