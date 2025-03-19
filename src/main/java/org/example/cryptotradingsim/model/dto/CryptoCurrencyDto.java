package org.example.cryptotradingsim.model.dto;

import java.math.BigDecimal;

public class CryptoCurrencyDto {

    private String pair;
    private String name;
    private BigDecimal price;
    private BigDecimal quantity;

    public CryptoCurrencyDto() {

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

    public CryptoCurrencyDto(String pair, BigDecimal price, BigDecimal quantity) {
        this.pair = pair;
        this.price = price;
        this.quantity = quantity;

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


}
