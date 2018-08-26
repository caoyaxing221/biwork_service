package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="team对象",description="团队对象team")
public class  AddTeamPojo {
	@ApiModelProperty(value="团队名称",name="name",example="",required=true)
	private String name;
	@ApiModelProperty(value="email",name="email",example="",required=true)
	private String email;
	@ApiModelProperty(value="公司人数",name="stuffNum",example="",required=true)
	private String stuffNum;
	@ApiModelProperty(value="管理员姓名",name="adminName",example="",required=true)
	private String adminName;
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
