package com.weshare.thunder.model;

import java.math.BigDecimal;

/**
 * Created by tobepro on 15-8-6.
 */
public class ReFundToFix {
    private Integer productId;
    private BigDecimal amount;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount==null ? BigDecimal.ZERO : amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
