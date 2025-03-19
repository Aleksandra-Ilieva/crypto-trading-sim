package org.example.cryptotradingsim.model.entity;

import jakarta.persistence.*;
import org.example.cryptotradingsim.model.enums.TransactionType;

import java.math.BigDecimal;

@Entity
@Table
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column(precision = 20, scale = 10)
    private BigDecimal price;
    @Column(precision = 20, scale = 10)
    private BigDecimal quantity;

    @ManyToOne
    private CryptoCurrency currency;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public CryptoCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(CryptoCurrency currency) {
        this.currency = currency;
    }
}
