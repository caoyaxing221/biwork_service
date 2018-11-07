package com.biwork.po.request;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="批量签名的数据对象",description="批量签名的数据对象")
public class BatchRawTxFlowPojo {
	@ApiModelProperty(value="批量签名的数据",name="data",example="",required=true)
    private String signCoin;
	private List<String> signDataArr;
	public String getSignCoin() {
		return signCoin;
	}
	public void setSignCoin(String signCoin) {
		this.signCoin = signCoin;
	}
	public List<String> getSignDataArr() {
		return signDataArr;
	}
	public void setSignDataArr(List<String> signDataArr) {
		this.signDataArr = signDataArr;
	}
	
}
