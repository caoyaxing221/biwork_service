package com.biwork.entity;

import java.util.Date;

public class Process {
    private Integer id;

    private Integer teamId;

    private Integer submitterId;

    private String applicationNumber;

    private Integer currencyId;

    private Integer departmentId;

    private Integer categoryId;

    private String receiver;

    private Integer currentNode;

    private Integer state;

    private Date inserttime;

    private Date updatetime;

    private Date finishtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(Integer submitterId) {
        this.submitterId = submitterId;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber == null ? null : applicationNumber.trim();
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public Integer getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Integer currentNode) {
        this.currentNode = currentNode;
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }
}