package com.weshare.thunder.model;

import java.math.BigDecimal;

public class RecordDeposit {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.gid
     *
     * @mbggenerated
     */
    private String gid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.user_gid
     *
     * @mbggenerated
     */
    private String userGid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.trans_deduction_gid
     *
     * @mbggenerated
     */
    private String transDeductionGid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.deduct_card_gid
     *
     * @mbggenerated
     */
    private String deductCardGid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.create_time
     *
     * @mbggenerated
     */
    private Integer createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.update_time
     *
     * @mbggenerated
     */
    private Integer updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.deposit_period
     *
     * @mbggenerated
     */
    private Integer depositPeriod;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.deposit_rate
     *
     * @mbggenerated
     */
    private BigDecimal depositRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.product_id
     *
     * @mbggenerated
     */
    private Integer productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.amount_initial
     *
     * @mbggenerated
     */
    private BigDecimal amountInitial;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.amount_deposit
     *
     * @mbggenerated
     */
    private BigDecimal amountDeposit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.deposit_state
     *
     * @mbggenerated
     */
    private Byte depositState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.deposit_src
     *
     * @mbggenerated
     */
    private Byte depositSrc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.deposit_type
     *
     * @mbggenerated
     */
    private Byte depositType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.end_time
     *
     * @mbggenerated
     */
    private Integer endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.real_earning
     *
     * @mbggenerated
     */
    private BigDecimal realEarning;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.left_earning
     *
     * @mbggenerated
     */
    private BigDecimal leftEarning;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.fail_reason
     *
     * @mbggenerated
     */
    private String failReason;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column record_deposit.order_id
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.id
     *
     * @return the value of record_deposit.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.id
     *
     * @param id the value for record_deposit.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.gid
     *
     * @return the value of record_deposit.gid
     *
     * @mbggenerated
     */
    public String getGid() {
        return gid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.gid
     *
     * @param gid the value for record_deposit.gid
     *
     * @mbggenerated
     */
    public void setGid(String gid) {
        this.gid = gid == null ? null : gid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.user_gid
     *
     * @return the value of record_deposit.user_gid
     *
     * @mbggenerated
     */
    public String getUserGid() {
        return userGid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.user_gid
     *
     * @param userGid the value for record_deposit.user_gid
     *
     * @mbggenerated
     */
    public void setUserGid(String userGid) {
        this.userGid = userGid == null ? null : userGid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.trans_deduction_gid
     *
     * @return the value of record_deposit.trans_deduction_gid
     *
     * @mbggenerated
     */
    public String getTransDeductionGid() {
        return transDeductionGid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.trans_deduction_gid
     *
     * @param transDeductionGid the value for record_deposit.trans_deduction_gid
     *
     * @mbggenerated
     */
    public void setTransDeductionGid(String transDeductionGid) {
        this.transDeductionGid = transDeductionGid == null ? null : transDeductionGid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.deduct_card_gid
     *
     * @return the value of record_deposit.deduct_card_gid
     *
     * @mbggenerated
     */
    public String getDeductCardGid() {
        return deductCardGid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.deduct_card_gid
     *
     * @param deductCardGid the value for record_deposit.deduct_card_gid
     *
     * @mbggenerated
     */
    public void setDeductCardGid(String deductCardGid) {
        this.deductCardGid = deductCardGid == null ? null : deductCardGid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.create_time
     *
     * @return the value of record_deposit.create_time
     *
     * @mbggenerated
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.create_time
     *
     * @param createTime the value for record_deposit.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.update_time
     *
     * @return the value of record_deposit.update_time
     *
     * @mbggenerated
     */
    public Integer getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.update_time
     *
     * @param updateTime the value for record_deposit.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.deposit_period
     *
     * @return the value of record_deposit.deposit_period
     *
     * @mbggenerated
     */
    public Integer getDepositPeriod() {
        return depositPeriod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.deposit_period
     *
     * @param depositPeriod the value for record_deposit.deposit_period
     *
     * @mbggenerated
     */
    public void setDepositPeriod(Integer depositPeriod) {
        this.depositPeriod = depositPeriod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.deposit_rate
     *
     * @return the value of record_deposit.deposit_rate
     *
     * @mbggenerated
     */
    public BigDecimal getDepositRate() {
        return depositRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.deposit_rate
     *
     * @param depositRate the value for record_deposit.deposit_rate
     *
     * @mbggenerated
     */
    public void setDepositRate(BigDecimal depositRate) {
        this.depositRate = depositRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.product_id
     *
     * @return the value of record_deposit.product_id
     *
     * @mbggenerated
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.product_id
     *
     * @param productId the value for record_deposit.product_id
     *
     * @mbggenerated
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.amount_initial
     *
     * @return the value of record_deposit.amount_initial
     *
     * @mbggenerated
     */
    public BigDecimal getAmountInitial() {
        return amountInitial;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.amount_initial
     *
     * @param amountInitial the value for record_deposit.amount_initial
     *
     * @mbggenerated
     */
    public void setAmountInitial(BigDecimal amountInitial) {
        this.amountInitial = amountInitial;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.amount_deposit
     *
     * @return the value of record_deposit.amount_deposit
     *
     * @mbggenerated
     */
    public BigDecimal getAmountDeposit() {
        return amountDeposit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.amount_deposit
     *
     * @param amountDeposit the value for record_deposit.amount_deposit
     *
     * @mbggenerated
     */
    public void setAmountDeposit(BigDecimal amountDeposit) {
        this.amountDeposit = amountDeposit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.deposit_state
     *
     * @return the value of record_deposit.deposit_state
     *
     * @mbggenerated
     */
    public Byte getDepositState() {
        return depositState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.deposit_state
     *
     * @param depositState the value for record_deposit.deposit_state
     *
     * @mbggenerated
     */
    public void setDepositState(Byte depositState) {
        this.depositState = depositState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.deposit_src
     *
     * @return the value of record_deposit.deposit_src
     *
     * @mbggenerated
     */
    public Byte getDepositSrc() {
        return depositSrc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.deposit_src
     *
     * @param depositSrc the value for record_deposit.deposit_src
     *
     * @mbggenerated
     */
    public void setDepositSrc(Byte depositSrc) {
        this.depositSrc = depositSrc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.deposit_type
     *
     * @return the value of record_deposit.deposit_type
     *
     * @mbggenerated
     */
    public Byte getDepositType() {
        return depositType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.deposit_type
     *
     * @param depositType the value for record_deposit.deposit_type
     *
     * @mbggenerated
     */
    public void setDepositType(Byte depositType) {
        this.depositType = depositType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.end_time
     *
     * @return the value of record_deposit.end_time
     *
     * @mbggenerated
     */
    public Integer getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.end_time
     *
     * @param endTime the value for record_deposit.end_time
     *
     * @mbggenerated
     */
    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.real_earning
     *
     * @return the value of record_deposit.real_earning
     *
     * @mbggenerated
     */
    public BigDecimal getRealEarning() {
        return realEarning;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.real_earning
     *
     * @param realEarning the value for record_deposit.real_earning
     *
     * @mbggenerated
     */
    public void setRealEarning(BigDecimal realEarning) {
        this.realEarning = realEarning;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.left_earning
     *
     * @return the value of record_deposit.left_earning
     *
     * @mbggenerated
     */
    public BigDecimal getLeftEarning() {
        return leftEarning;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.left_earning
     *
     * @param leftEarning the value for record_deposit.left_earning
     *
     * @mbggenerated
     */
    public void setLeftEarning(BigDecimal leftEarning) {
        this.leftEarning = leftEarning;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.fail_reason
     *
     * @return the value of record_deposit.fail_reason
     *
     * @mbggenerated
     */
    public String getFailReason() {
        return failReason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.fail_reason
     *
     * @param failReason the value for record_deposit.fail_reason
     *
     * @mbggenerated
     */
    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column record_deposit.order_id
     *
     * @return the value of record_deposit.order_id
     *
     * @mbggenerated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column record_deposit.order_id
     *
     * @param orderId the value for record_deposit.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }
    public static final byte STATE_MATCHING = 0;
    public static final byte STATE_DEPOSITING = 1;
    public static final byte STATE_DEPOSIT_COMPLETED = 2;
    public static final byte STATE_DEDUCTING = 3;
    public static final byte STATE_DEDUCT_FAIL = 4;
}