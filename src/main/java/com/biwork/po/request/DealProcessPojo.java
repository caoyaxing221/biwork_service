package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="审批处理对象",description="审批处理对象")
public class  DealProcessPojo {
	@ApiModelProperty(value="审批Id",name="processId",example="",required=true)
	private String processId;
	@ApiModelProperty(value="操作类型(1审批通过-1拒绝 -2撤销)",name="dealFlag",example="",required=true)
	private String dealFlag;
	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}
	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	/**
	 * @return the dealFlag
	 */
	public String getDealFlag() {
		return dealFlag;
	}
	/**
	 * @param dealFlag the dealFlag to set
	 */
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}
	
	
	
}
