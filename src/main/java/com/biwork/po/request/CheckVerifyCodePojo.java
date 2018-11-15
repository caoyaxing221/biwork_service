package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="校验短信验证码对象",description="校验短信验证码对象")
public class  CheckVerifyCodePojo {
	@ApiModelProperty(value="手机号",name="phone",example="",required=true)
	private String phone;
	@ApiModelProperty(value="验证码类型(register,login,forgetpassword,transfer,invite)",name="type",example="",required=true)
	private String type;
	@ApiModelProperty(value="验证码",name="verifyCode",example="",required=true)
	private String verifyCode;
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
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	

	
	
}
