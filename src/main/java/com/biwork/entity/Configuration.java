package com.biwork.entity;

import java.util.Date;

public class Configuration {
    private Integer id;

    private String name;

    private String value;

    private Integer state;

    private Date inserttime;

    public Configuration(Integer id, String name, String value, Integer state, Date inserttime) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.state = state;
        this.inserttime = inserttime;
    }

    public Configuration() {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }
}