package com.biwork.entity;

import java.util.Date;

public class AirdropTask {
    private Integer id;

    private String name;

    private Date inserttime;

    private Date updatetime;

    private Date endtime;

    private Integer state;

    private String title;

    private String remark;
    private Integer needAttach;
    private String teamId;
    
    private String bannerUrl;
    
    private Integer createUserid;
    
    private Integer type;

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

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	/**
	 * @return the needAttach
	 */
	public Integer getNeedAttach() {
		return needAttach;
	}

	/**
	 * @param needAttach the needAttach to set
	 */
	public void setNeedAttach(Integer needAttach) {
		this.needAttach = needAttach;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public Integer getCreateUserid() {
		return createUserid;
	}

	public void setCreateUserid(Integer createUserid) {
		this.createUserid = createUserid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}