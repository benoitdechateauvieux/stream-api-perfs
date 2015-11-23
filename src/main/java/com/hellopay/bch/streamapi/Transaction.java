package com.hellopay.bch.streamapi;

import java.math.BigDecimal;

/**
 * Created by benoit on 11/23/15.
 */
public class Transaction {

    private TransactionType type;
    private BigDecimal value;
    private Long id;

    public Transaction(long id, BigDecimal value, TransactionType type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public enum TransactionType {
        TopUp, Checkout
    }
}
