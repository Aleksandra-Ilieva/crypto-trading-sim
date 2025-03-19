package org.example.cryptotradingsim.model.dto;

import java.math.BigDecimal;

public class UserDto {

    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
