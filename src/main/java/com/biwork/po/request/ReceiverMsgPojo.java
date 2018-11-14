package com.biwork.po.request;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
public class  ReceiverMsgPojo {
	@ApiModelProperty(value="地址信息",name="address",example="",required=true)
	private String address;
	@ApiModelProperty(value="数量",name="amount",example="",required=true)
	private String amount;
	@ApiModelProperty(value="备注",name="remark",example="",required=true)
	private String remark;
	
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
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	
	
}
