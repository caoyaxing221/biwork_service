package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="普通用户登录对象",description="普通用户登录对象")
public class  UserInvitePojo {
	@ApiModelProperty(value="手机号",name="phone",example="",required=true)
	private String phone;
	@ApiModelProperty(value="短信验证码",name="verifyCode",example="",required=true)
	private String verifyCode;
	@ApiModelProperty(value="真实姓名",name="name",example="",required=true)
	private String name;
	@ApiModelProperty(value="邀请注册码",name="inviteCode",example="",required=false)
	private String inviteCode;

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
	 * @return the verifyCode
	 */
	public String getVerifyCode() {
		return verifyCode;
	}
	/**
	 * @param verifyCode the verifyCode to set
	 */
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
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
	 * @return the inviteCode
	 */
	public String getInviteCode() {
		return inviteCode;
	}
	/**
	 * @param inviteCode the inviteCode to set
	 */
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	
	
	
	
}
