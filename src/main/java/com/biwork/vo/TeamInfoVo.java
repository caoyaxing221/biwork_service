package com.biwork.vo;

import java.util.Date;

public class TeamInfoVo {
    private Integer id;

    private String name;

    private String email;

    private Integer stuffNum;
    private String stuffSize;
    private String adminName;

    private Integer state;

    private Date inserttime;

    private Date updatetime;

    private String inviteCode;

    private Integer createUserId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getStuffNum() {
        return stuffNum;
    }

    public void setStuffNum(Integer stuffNum) {
        this.stuffNum = stuffNum;
    }

    /**
	 * @return the stuffSize
	 */
	public String getStuffSize() {
		return stuffSize;
	}

	/**
	 * @param stuffSize the stuffSize to set
	 */
	public void setStuffSize(String stuffSize) {
		this.stuffSize = stuffSize;
	}

	public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
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

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}