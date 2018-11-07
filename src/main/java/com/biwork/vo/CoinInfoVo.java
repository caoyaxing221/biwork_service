package com.biwork.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(value = {"handler"})
public class CoinInfoVo {
	private String id;
	private String coinName;
	private String coinMark;
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

}
