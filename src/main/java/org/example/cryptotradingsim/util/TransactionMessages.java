package org.example.cryptotradingsim.util;

public class TransactionMessages {

    public static final String SUCCESSFUL_TRANSACTION = "Successful transaction!";
    public static final String FAILED_TRANSACTION_PRICE_CHANGE = "Transaction failed. The cryptocurrency ask price has changed by more than 1%.";
    public static final String FAILED_TRANSACTION_MISSING_CRYPTO= "Transaction failed.Тhe user does not have this cryptocurrency.";
    public static final String FAILED_TRANSACTION_NOT_ENOUGH_QUANTITY= "Transaction failed. Not enough quantity.";
    public static final String FAILED_TRANSACTION_INSUFFICIENT_FUNDS= "Transaction failed. Insufficient funds";
}
