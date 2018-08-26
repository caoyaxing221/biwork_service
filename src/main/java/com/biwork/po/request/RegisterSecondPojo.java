package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="管理员注册第二步对象",description="管理员注册第二步对象")
public class  RegisterSecondPojo {
	@ApiModelProperty(value="手机号",name="phone",example="",required=true)
	private String phone;
	@ApiModelProperty(value="密码",name="password",example="",required=true)
	private String password;
	@ApiModelProperty(value="第一步返回的registerToken",name="registerToken",example="",required=true)
	private String registerToken;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the registerToken
	 */
	public String getRegisterToken() {
		return registerToken;
	}
	/**
	 * @param registerToken the registerToken to set
	 */
	public void setRegisterToken(String registerToken) {
		this.registerToken = registerToken;
	}
	
	
	
}
