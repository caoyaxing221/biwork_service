package com.biwork.entity;

import java.util.Date;

public class TxGasLimit {
	private Integer id;
	private String coinName;
	private String coinMark;
	private String gasLimit;
	private String contractAddress;
	private Integer state;
	private Date inserttime;
	private Date updatetime;
	
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
	
	public String getGasLimit(){
		return gasLimit;
	}
	public void setGasLimit(String gasLimit) {
		this.gasLimit = gasLimit;
	}
	
	public String getContractAddress(){
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	
	public Integer getState() {
        return state;
    }
    public void setState(Integer state) {
        this.state = state;
    }
	
	public Date getInserttime() {
		return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
