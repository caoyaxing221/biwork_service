package com.biwork.entity;

import java.util.Date;

public class Member {
    private Integer id;

    private Integer teamId;

    private Integer inviterId;
    
    private Integer inviteTableId;

    private Integer userId;

    private String name;

    private String phone;

    private Integer state;

    private Date inserttime;

    private Date updatetime;

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

    public Integer getInviterId() {
        return inviterId;
    }

    public void setInviterId(Integer inviterId) {
        this.inviterId = inviterId;
    }

    /**
	 * @return the inviteTableId
	 */
	public Integer getInviteTableId() {
		return inviteTableId;
	}

	/**
	 * @param inviteTableId the inviteTableId to set
	 */
	public void setInviteTableId(Integer inviteTableId) {
		this.inviteTableId = inviteTableId;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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
}