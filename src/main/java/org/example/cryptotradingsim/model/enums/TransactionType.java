package org.example.cryptotradingsim.model.enums;

public enum TransactionType {

    BUY("BUY"), SELL("SELL");

    public String value;

    TransactionType(String value) {
        this.value = value;
    }
}
