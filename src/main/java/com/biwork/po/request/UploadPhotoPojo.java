package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * create by cyx
 */
@ApiModel(value="上传base64图片对象",description="上传base64图片对象")
public class  UploadPhotoPojo {
	@ApiModelProperty(value="图片base64",name="图片base64",example="",required=true)
	private String picUrl;

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * @param picUrl the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	
	
	
}
