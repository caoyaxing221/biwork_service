package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="管理员登录对象",description="管理员登录对象")
public class  AdminLoginPojo {
	@ApiModelProperty(value="手机号",name="phone",example="",required=true)
	private String phone;
	@ApiModelProperty(value="密码",name="password",example="",required=true)
	private String password;
	@ApiModelProperty(value="图形验证码",name="imgCode",example="",required=true)
	private String imgCode;
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
	 * @return the imgCode
	 */
	public String getImgCode() {
		return imgCode;
	}
	/**
	 * @param imgCode the imgCode to set
	 */
	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}
	
	
	
}
