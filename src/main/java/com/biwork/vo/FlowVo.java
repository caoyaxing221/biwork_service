package com.biwork.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(value = {"handler"})
public class FlowVo {
	private Integer flowId;
	private String name;
	private String visibleAll;
	private Integer templateNo;
	private List<TeamVo> authList;
	private List<TeamVo> nodeList;
	/**
	 * @return the flowId
	 */
	public Integer getFlowId() {
		return flowId;
	}
	/**
	 * @param flowId the flowId to set
	 */
	public void setFlowId(Integer flowId) {
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
	public List<TeamVo> getAuthList() {
		return authList;
	}
	/**
	 * @param authList the authList to set
	 */
	public void setAuthList(List<TeamVo> authList) {
		this.authList = authList;
	}
	/**
	 * @return the nodeList
	 */
	public List<TeamVo> getNodeList() {
		return nodeList;
	}
	/**
	 * @param nodeList the nodeList to set
	 */
	public void setNodeList(List<TeamVo> nodeList) {
		this.nodeList = nodeList;
	}
	public Integer getTemplateNo() {
		return templateNo;
	}
	public void setTemplateNo(Integer templateNo) {
		this.templateNo = templateNo;
	}
	
	
}
