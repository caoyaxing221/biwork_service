package com.biwork.po.request;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="提交地址模板对象",description="提交地址模板对象")
public class  AddressTemplatePojo {
	@ApiModelProperty(value="团队id",name="teamId",example="",required=true)
	private String teamId;
	@ApiModelProperty(value="模版名称",name="name",example="",required=true)
	private String name;
	@ApiModelProperty(value="收币地址及数量信息",name="AddressTemplateMsg",example="",required=true)
	private  List<AddressTemplateMsgPojo> AddressTemplateMsg;
	/**
	 * @return the teamId
	 */
	public String getTeamId() {
		return teamId;
	}
	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
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
	 * @return the addressTemplateMsg
	 */
	public List<AddressTemplateMsgPojo> getAddressTemplateMsg() {
		return AddressTemplateMsg;
	}
	/**
	 * @param addressTemplateMsg the addressTemplateMsg to set
	 */
	public void setAddressTemplateMsg(List<AddressTemplateMsgPojo> addressTemplateMsg) {
		AddressTemplateMsg = addressTemplateMsg;
	}
	
	
	
}
