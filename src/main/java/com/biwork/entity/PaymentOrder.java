package com.biwork.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentOrder {
    private Integer id;

    private Integer userId;

    private Integer paymentId;

    private String paymentName;

    private BigDecimal paymentMoney;

    private String paymentUnit;

    private BigDecimal paymentDiscount;

    private BigDecimal actualMoney;

    private String userCoinAddress;

    private String receiveCoinAddress;

    private Date paymentTime;

    private Date insertTime;

    private Integer state;

    private String orderNo;

    public PaymentOrder(Integer id, Integer userId, Integer paymentId, String paymentName, BigDecimal paymentMoney, String paymentUnit, BigDecimal paymentDiscount, BigDecimal actualMoney, String userCoinAddress, String receiveCoinAddress, Date paymentTime, Date insertTime, Integer state, String orderNo) {
        this.id = id;
        this.userId = userId;
        this.paymentId = paymentId;
        this.paymentName = paymentName;
        this.paymentMoney = paymentMoney;
        this.paymentUnit = paymentUnit;
        this.paymentDiscount = paymentDiscount;
        this.actualMoney = actualMoney;
        this.userCoinAddress = userCoinAddress;
        this.receiveCoinAddress = receiveCoinAddress;
        this.paymentTime = paymentTime;
        this.insertTime = insertTime;
        this.state = state;
        this.orderNo = orderNo;
    }

    public PaymentOrder() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName == null ? null : paymentName.trim();
    }

    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public String getPaymentUnit() {
        return paymentUnit;
    }

    public void setPaymentUnit(String paymentUnit) {
        this.paymentUnit = paymentUnit == null ? null : paymentUnit.trim();
    }

    public BigDecimal getPaymentDiscount() {
        return paymentDiscount;
    }

    public void setPaymentDiscount(BigDecimal paymentDiscount) {
        this.paymentDiscount = paymentDiscount;
    }

    public BigDecimal getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(BigDecimal actualMoney) {
        this.actualMoney = actualMoney;
    }

    public String getUserCoinAddress() {
        return userCoinAddress;
    }

    public void setUserCoinAddress(String userCoinAddress) {
        this.userCoinAddress = userCoinAddress == null ? null : userCoinAddress.trim();
    }

    public String getReceiveCoinAddress() {
        return receiveCoinAddress;
    }

    public void setReceiveCoinAddress(String receiveCoinAddress) {
        this.receiveCoinAddress = receiveCoinAddress == null ? null : receiveCoinAddress.trim();
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
}