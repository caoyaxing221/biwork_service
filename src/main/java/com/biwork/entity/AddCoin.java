package com.biwork.entity;

import java.util.Date;

public class AddCoin {
	private Integer id;
	private String coinName;
	private String coinMark;
	private Integer bip44Num;
	private String coinRateId;
	private String coinImgUrl;
	private String contractAddress;
	
	public Integer getId() {
	        return id;
	}
	
    public void setId(Integer id) {
        this.id = id;
    }

	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
	public Integer getBip44Num() {
		return bip44Num;
	}
	public void setBip44Num(Integer bip44Num) {
		this.bip44Num = bip44Num;
	}
	
	public String getCoinRateId() {
		return coinRateId;
	}
	
	public void setCoinRateId(String coinRateId) {
		this.coinRateId = coinRateId;
	}
	
	public String getCoinImgUrl() {
		return coinImgUrl;
	}

	public void setCoinImgUrl(String coinImgUrl) {
		this.coinImgUrl = coinImgUrl;
	}

	public String getCoinMark() {
		return coinMark;
	}
	public void setCoinMark(String coinMark) {
		this.coinMark = coinMark;
	}
	
	public String getContractAddress(){
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
}
