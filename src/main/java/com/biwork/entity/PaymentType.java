package com.biwork.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentType {
    private Integer id;

    private String name;

    private BigDecimal money;

    private String unit;

    private BigDecimal discount;

    private Integer state;

    private Date insertTime;

    public PaymentType(Integer id, String name, BigDecimal money, String unit, BigDecimal discount, Integer state, Date insertTime) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.unit = unit;
        this.discount = discount;
        this.state = state;
        this.insertTime = insertTime;
    }

    public PaymentType() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}