package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="空投任务对象",description="空投任务对象airDropTask")
public class  AddAirDropTaskPojo {
	@ApiModelProperty(value="团队id",name="teamId",example="",required=true)
	private String teamId;
	@ApiModelProperty(value="名称",name="name",example="",required=true)
	private String name;
	@ApiModelProperty(value="截止日期",name="endTime",example="2019-08-01 00:00:00",required=false)
	private String endTime;
	@ApiModelProperty(value="标题",name="title",example="",required=false)
	private String title;
	@ApiModelProperty(value="备注",name="remark",example="",required=false)
	private String remark;
	@ApiModelProperty(value="需要上传附件(1需要0不需要)",name="needAttach",example="",required=false)
	private String needAttach;
	@ApiModelProperty(value="banner图链接",name="bannerUrl",example="",required=false)
	private String bannerUrl;
	@ApiModelProperty(value="任务类型 0 空投任务 1 导入地址任务",name="type",example="",required=true)
	private String type;
	/**
	 * @return the teamId
	 */
	public String getTeamId() {
		return teamId;
	}
	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the needAttach
	 */
	public String getNeedAttach() {
		return needAttach;
	}
	/**
	 * @param needAttach the needAttach to set
	 */
	public void setNeedAttach(String needAttach) {
		this.needAttach = needAttach;
	}
	/**
	 * @return the bannerUrl
	 */
	public String getBannerUrl() {
		return bannerUrl;
	}
	/**
	 * @param bannerUrl the bannerUrl to set
	 */
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
}
