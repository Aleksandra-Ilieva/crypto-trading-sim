package org.example.cryptotradingsim.model.dto;

import org.example.cryptotradingsim.model.entity.CryptoCurrency;
import org.example.cryptotradingsim.model.enums.TransactionType;

import java.math.BigDecimal;

public class TransactionHistoryDto {

    private TransactionType transactionType;

    private BigDecimal price;

    private BigDecimal quantity;

    private CryptoCurrency currency;

    public CryptoCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(CryptoCurrency currency) {
        this.currency = currency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
