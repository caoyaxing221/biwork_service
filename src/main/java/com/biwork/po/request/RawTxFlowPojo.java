package com.biwork.po.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="签名的数据对象",description="签名的数据对象")
public class RawTxFlowPojo {
	@ApiModelProperty(value="签名的数据",name="data",example="",required=true)
    private String data;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
