package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;


/*
 * create by cyx
 */
@ApiModel(value="创建订单对象",description="CommitProcessPojo")
public class CommitOrderPojo {
	@ApiModelProperty(value="订单号(myService/getOrderNo生成)",name="orderNo",example="",required=true)
	@NotBlank(message="订单号不能为空")
	private String orderNo;
	@ApiModelProperty(value="付费种类id(myService/getPurchaseType返回list的id)",name="purchaseTypeId",example="",required=true)
	@NotBlank(message="付费种类id不能为空")
	private String purchaseTypeId;
	@ApiModelProperty(value="付币地址",name="userCoinAddress",example="",required=true)
	@NotBlank(message="付币地址不能为空")
	private String userCoinAddress;

	public String getOrderNo() {
		return orderNo;
	}

	public String getPurchaseTypeId() {
		return purchaseTypeId;
	}

	public void setPurchaseTypeId(String purchaseTypeId) {
		this.purchaseTypeId = purchaseTypeId;
	}

	public String getUserCoinAddress() {
		return userCoinAddress;
	}

	public void setUserCoinAddress(String userCoinAddress) {
		this.userCoinAddress = userCoinAddress;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
