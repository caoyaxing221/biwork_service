package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="编辑flow对象",description="编辑流程对象flow")
public class  EditFlowPojo {
	@ApiModelProperty(value="流程id",name="flowId",example="",required=true)
	private String flowId;
	@ApiModelProperty(value="流程名称",name="name",example="",required=true)
	private String name;
	@ApiModelProperty(value="是否批量转账(0否1是)",name="isBatch",example="",required=true)
	private String isBatch;
	@ApiModelProperty(value="是否全员可见(0否1是)",name="visibleAll",example="",required=true)
	private String visibleAll;
	@ApiModelProperty(value="可见账号列表(可见成员id用竖线隔开)",name="authList",example="",required=true)
	private String authList;
	@ApiModelProperty(value="审核账号列表(成员id用竖线隔开,先后顺序即为审核顺序)",name="nodeList",example="",required=true)
	private String nodeList;
	@ApiModelProperty(value="使用模板编号",name="templateNo",example="",required=false)
	private String templateNo;
	/**
	 * @return the teamId
	 */
	public String getFlowId() {
		return flowId;
	}
	/**
	 * @param teamId the teamId to set
	 */
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the isBatch
	 */
	public String getIsBatch() {
		return isBatch;
	}
	/**
	 * @param isBatch the isBatch to set
	 */
	public void setIsBatch(String isBatch) {
		this.isBatch = isBatch;
	}
	/**
	 * @return the visibleAll
	 */
	public String getVisibleAll() {
		return visibleAll;
	}
	/**
	 * @param visibleAll the visibleAll to set
	 */
	public void setVisibleAll(String visibleAll) {
		this.visibleAll = visibleAll;
	}
	/**
	 * @return the authList
	 */
	public String getAuthList() {
		return authList;
	}
	/**
	 * @param authList the authList to set
	 */
	public void setAuthList(String authList) {
		this.authList = authList;
	}
	/**
	 * @return the nodeList
	 */
	public String getNodeList() {
		return nodeList;
	}
	/**
	 * @param nodeList the nodeList to set
	 */
	public void setNodeList(String nodeList) {
		this.nodeList = nodeList;
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
