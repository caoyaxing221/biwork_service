package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="获取短信验证码对象",description="获取短信验证码对象")
public class  GetVerifyCodePojo {
	@ApiModelProperty(value="手机号",name="phone",example="",required=true)
	private String phone;
	@ApiModelProperty(value="验证码类型(register,login,forgetpassword,transfer,invite)",name="type",example="",required=true)
	private String type;
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
	

	
	
}
