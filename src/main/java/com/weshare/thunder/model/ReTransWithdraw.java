package com.weshare.thunder.model;

import java.math.BigDecimal;

/**
 * Created by tobepro on 15-8-6.
 */
public class ReTransWithdraw {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount==null ? BigDecimal.ZERO : amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
