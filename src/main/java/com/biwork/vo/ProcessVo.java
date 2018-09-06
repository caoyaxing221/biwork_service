package com.biwork.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(value = {"handler"})
public class ProcessVo {
	private String id;
	private String submitterId;
	private String submitterName;
	private String applicationNo;
	private String payCategory;
	private String cause;
	private String enterDepartment;
	private String currencyName;
	private String currencyType;
	private String receiver;
	private String receiverMsg;
	private String remark;
	private String attachUrl;
	private String commitTime;
	private String state;
	private String canApprove;
	private List<ProcessNodeVo> processNode;
	private String airDropTaskId;
	private String airDropTaskName;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	public String getSubmitterId() {
		return submitterId;
	}
	public void setSubmitterId(String submitterId) {
		this.submitterId = submitterId;
	}
	/**
	 * @return the submitterName
	 */
	public String getSubmitterName() {
		return submitterName;
	}
	/**
	 * @param submitterName the submitterName to set
	 */
	public void setSubmitterName(String submitterName) {
		this.submitterName = submitterName;
	}
	/**
	 * @return the applicationNo
	 */
	public String getApplicationNo() {
		return applicationNo;
	}
	/**
	 * @param applicationNo the applicationNo to set
	 */
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	/**
	 * @return the payCategory
	 */
	public String getPayCategory() {
		return payCategory;
	}
	/**
	 * @param payCategory the payCategory to set
	 */
	public void setPayCategory(String payCategory) {
		this.payCategory = payCategory;
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
	 * @return the enterDepartment
	 */
	public String getEnterDepartment() {
		return enterDepartment;
	}
	/**
	 * @param enterDepartment the enterDepartment to set
	 */
	public void setEnterDepartment(String enterDepartment) {
		this.enterDepartment = enterDepartment;
	}
	/**
	 * @return the currencyName
	 */
	public String getCurrencyName() {
		return currencyName;
	}
	/**
	 * @param currencyName the currencyName to set
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	/**
	 * @return the currencyType
	 */
	public String getCurrencyType() {
		return currencyType;
	}
	/**
	 * @param currencyType the currencyType to set
	 */
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}
	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	/**
	 * @return the reveiverMsg
	 */
	public String getReceiverMsg() {
		return receiverMsg;
	}
	/**
	 * @param reveiverMsg the reveiverMsg to set
	 */
	public void setReceiverMsg(String receiverMsg) {
		this.receiverMsg = receiverMsg;
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
	 * @return the commitTime
	 */
	public String getCommitTime() {
		return commitTime;
	}
	/**
	 * @param commitTime the commitTime to set
	 */
	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the canApprove
	 */
	public String getCanApprove() {
		return canApprove;
	}
	/**
	 * @param canApprove the canApprove to set
	 */
	public void setCanApprove(String canApprove) {
		this.canApprove = canApprove;
	}
	/**
	 * @return the processNode
	 */
	public List<ProcessNodeVo> getProcessNode() {
		return processNode;
	}
	/**
	 * @param processNode the processNode to set
	 */
	public void setProcessNode(List<ProcessNodeVo> processNode) {
		this.processNode = processNode;
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
	 * @return the airDropTaskName
	 */
	public String getAirDropTaskName() {
		return airDropTaskName;
	}
	/**
	 * @param airDropTaskName the airDropTaskName to set
	 */
	public void setAirDropTaskName(String airDropTaskName) {
		this.airDropTaskName = airDropTaskName;
	}
	
}
