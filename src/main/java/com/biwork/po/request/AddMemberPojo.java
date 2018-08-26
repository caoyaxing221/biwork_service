package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="添加团队成员对象",description="添加团队成员对象")
public class  AddMemberPojo {
	@ApiModelProperty(value="团队id",name="teamId",example="",required=true)
	private String teamId;
	@ApiModelProperty(value="手机号",name="phone",example="",required=true)
	private String phone;
	@ApiModelProperty(value="姓名",name="name",example="",required=true)
	private String name;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the type
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param type the type to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	
	
}
