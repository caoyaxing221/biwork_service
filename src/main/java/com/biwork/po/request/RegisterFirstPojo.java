package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="管理员注册第一步对象",description="管理员注册第一步对象")
public class  RegisterFirstPojo {
	@ApiModelProperty(value="手机号",name="phone",example="",required=true)
	private String phone;
	@ApiModelProperty(value="短信验证码",name="verifyCode",example="",required=true)
	private String verifyCode;
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
