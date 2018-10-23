package com.biwork.entity;

import java.util.Date;

public class AddCoin {
	private Integer id;
	private String coinName;
	private String coinMark;
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
