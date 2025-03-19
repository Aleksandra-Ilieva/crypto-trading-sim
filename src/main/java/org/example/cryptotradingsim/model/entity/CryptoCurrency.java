package org.example.cryptotradingsim.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table
public class CryptoCurrency {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String pair;
    @Transient
    private String name;
    @Transient
    private BigDecimal askPrice;
    @Transient
    private BigDecimal bidPrice;

    public CryptoCurrency(String pair, BigDecimal askPrice, BigDecimal bidPrice) {
        this.pair = pair;
        this.askPrice = askPrice;
        this.bidPrice = bidPrice;
    }

    public CryptoCurrency() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public BigDecimal getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(BigDecimal askPrice) {
        this.askPrice = askPrice;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }
}

