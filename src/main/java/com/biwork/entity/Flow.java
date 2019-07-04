package com.biwork.entity;

import java.util.Date;

public class Flow {
    private Integer id;

    private String name;

    private Integer nodeNum;

    private Integer visibleAll;

    private Integer isBatchTranser;

    private Integer teamId;

    private Integer state;

    private Date inserttime;

    private Date updatetime;

    private Integer createUserId;
    
    private Integer templateNo;

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

    public Integer getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(Integer nodeNum) {
        this.nodeNum = nodeNum;
    }

    public Integer getVisibleAll() {
        return visibleAll;
    }

    public void setVisibleAll(Integer visibleAll) {
        this.visibleAll = visibleAll;
    }

    public Integer getIsBatchTranser() {
        return isBatchTranser;
    }

    public void setIsBatchTranser(Integer isBatchTranser) {
        this.isBatchTranser = isBatchTranser;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
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

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

	public Integer getTemplateNo() {
		return templateNo;
	}

	public void setTemplateNo(Integer templateNo) {
		this.templateNo = templateNo;
	}
}