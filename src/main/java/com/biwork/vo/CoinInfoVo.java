package com.biwork.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(value = {"handler"})
public class CoinInfoVo {
	private String id;
	private String coinName;
	private String coinMark;
	private Integer bip44Num;
	private String coinRateId;
	private String coinImgUrl;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the coinName
	 */
	public String getCoinName() {
		return coinName;
	}
	/**
	 * @param coinName the coinName to set
	 */
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	/**
	 * @return the coinMark
	 */
	public String getCoinMark() {
		return coinMark;
	}
	/**
	 * @param coinMark the coinMark to set
	 */
	public void setCoinMark(String coinMark) {
		this.coinMark = coinMark;
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
}
