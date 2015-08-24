package com.weshare.thunder.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/8/20.
 */
public class ReTransInitAmount {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount == null ? BigDecimal.ZERO : amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
