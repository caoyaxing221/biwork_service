package com.biwork.po.request;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="提交申请对象",description="提交申请对向")
public class  CommitProcessPojo {
	@ApiModelProperty(value="流程id",name="flowId",example="",required=true)
	private String flowId;
	@ApiModelProperty(value="申请编号",name="applicationNumber",example="",required=true)
	private String applicationNumber;
	@ApiModelProperty(value="支付币种",name="coinMark",example="",required=true)
	private String coinMark;
	@ApiModelProperty(value="币价id",name="coinRateId",example="",required=false)
	private String coinRateId;
	@ApiModelProperty(value="事由",name="cause",example="",required=true)
	private String cause;
	@ApiModelProperty(value="入账部门",name="departmentId",example="",required=true)
	private String departmentId;
	@ApiModelProperty(value="付币类别",name="categoryId",example="",required=true)
	private String categoryId;
	@ApiModelProperty(value="收币地址及数量信息",name="receiverMsg",example="",required=true)
	private  List<ReceiverMsgPojo> receiverMsg;
	@ApiModelProperty(value="空投助手id",name="airDropTaskId",example="",required=false)
	private String airDropTaskId;
	@ApiModelProperty(value="收币方",name="receiver",example="",required=true)
	private String receiver;
	@ApiModelProperty(value="备注",name="remark",example="",required=true)
	private String remark;
	@ApiModelProperty(value="附件链接",name="attachUrl",example="",required=false)
	private String attachUrl;
	@ApiModelProperty(value="使用模板编号",name="templateNo",example="",required=false)
	private String templateNo;
	/**
	 * @return the flowId
	 */
	public String getFlowId() {
		return flowId;
	}
	/**
	 * @param flowId the flowId to set
	 */
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	/**
	 * @return the applicationNumber
	 */
	public String getApplicationNumber() {
		return applicationNumber;
	}
	/**
	 * @param applicationNumber the applicationNumber to set
	 */
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	
	/**
	 * @return the coinMark
	 */
	public String getCoinMark() {
		return coinMark;
	}
	/**
	 * @param coinMark the coinMark to set
	 */
	public void setCoinMark(String coinMark) {
		this.coinMark = coinMark;
	}
	/**
	 * @return the coinRateId
	 */
	public String getCoinRateId() {
		return coinRateId;
	}
	/**
	 * @param coinRateId the coinRateId to set
	 */
	public void setCoinRateId(String coinRateId) {
		this.coinRateId = coinRateId;
	}
	/**
	 * @return the cause
	 */
	public String getCause() {
		return cause;
	}
	/**
	 * @param cause the cause to set
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}
	/**
	 * @return the departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}
	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	/**
	 * @return the receiverMsg
	 */
	public List<ReceiverMsgPojo> getReceiverMsg() {
		return receiverMsg;
	}
	/**
	 * @param receiverMsg the receiverMsg to set
	 */
	public void setReceiverMsg(List<ReceiverMsgPojo> receiverMsg) {
		this.receiverMsg = receiverMsg;
	}
	/**
	 * @return the airDropTaskId
	 */
	public String getAirDropTaskId() {
		return airDropTaskId;
	}
	/**
	 * @param airDropTaskId the airDropTaskId to set
	 */
	public void setAirDropTaskId(String airDropTaskId) {
		this.airDropTaskId = airDropTaskId;
	}
	/**
	 * @return the reveiver
	 */
	public String getReceiver() {
		return receiver;
	}
	/**
	 * @param reveiver the reveiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
	/**
	 * @return the templateNo
	 */
	public String getTemplateNo() {
		return templateNo;
	}
	/**
	 * @param templateNo the templateNo to set
	 */
	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}
	
	
}
