package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="添加地址对象",description="添加地址对象")
public class  AddAddressPojo {
	@ApiModelProperty(value="空投活动token",name="空投活动token",example="",required=true)
	private String taskToken;
	/**
	 * @return the taskToken
	 */
	public String getTaskToken() {
		return taskToken;
	}
	/**
	 * @param taskToken the taskToken to set
	 */
	public void setTaskToken(String taskToken) {
		this.taskToken = taskToken;
	}
	@ApiModelProperty(value="地址",name="address",example="",required=true)
	private String address;
	@ApiModelProperty(value="附件url",name="attachUrl",example="",required=false)
	private String attachUrl;

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the attachUrl
	 */
	public String getAttachUrl() {
		return attachUrl;
	}
	/**
	 * @param attachUrl the attachUrl to set
	 */
	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}
	
	
	
}
