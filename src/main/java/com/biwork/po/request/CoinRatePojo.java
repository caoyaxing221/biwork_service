package com.biwork.po.request;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="获取数字货币汇率数据对象",description="获取数字货币汇率数据对象")
public class CoinRatePojo {
	@ApiModelProperty(value="获取数字货币汇率数据对象",name="data",example="",required=true)
	private String coinRateMark;
	private List<String> coinRateIdList;
	
	public String getCoinRateMark() {
		return coinRateMark;
	}
	public void setCoinRateMark(String coinRateMark) {
		this.coinRateMark = coinRateMark;
	}
	
	public List<String> getCoinRateIdList() {
		return coinRateIdList;
	}
	public void setCoinRateIdList(List<String> coinRateIdList) {
		this.coinRateIdList = coinRateIdList;
	}
}
