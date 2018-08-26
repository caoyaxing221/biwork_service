package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="管理员编辑team对象",description="团队对象team")
public class  EditTeamPojo {
	@ApiModelProperty(value="团队id",name="teamId",example="",required=true)
	private String teamId;
	@ApiModelProperty(value="团队名称",name="name",example="",required=true)
	private String name;
	@ApiModelProperty(value="email",name="email",example="",required=true)
	private String email;
	@ApiModelProperty(value="公司人数",name="stuffNum",example="",required=true)
	private String stuffNum;
	@ApiModelProperty(value="管理员姓名",name="adminName",example="",required=true)
	private String adminName;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStuffNum() {
		return stuffNum;
	}
	public void setStuffNum(String stuffNum) {
		this.stuffNum = stuffNum;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
	
	
}
