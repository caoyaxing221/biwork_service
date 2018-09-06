package com.biwork.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(value = {"handler"})
public class ProcessListVo {
	private String id;
	private String submitterName;
	private String currencyName;
	private String receiverMsg;
	private String commitTime;
	private String state;
	private String count;
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
	 * @return the submitterName
	 */
	public String getSubmitterName() {
		return submitterName;
	}
	/**
	 * @param submitterName the submitterName to set
	 */
	public void setSubmitterName(String submitterName) {
		this.submitterName = submitterName;
	}
	/**
	 * @return the currencyName
	 */
	public String getCurrencyName() {
		return currencyName;
	}
	/**
	 * @param currencyName the currencyName to set
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	/**
	 * @return the receiverMsg
	 */
	public String getReceiverMsg() {
		return receiverMsg;
	}
	/**
	 * @param receiverMsg the receiverMsg to set
	 */
	public void setReceiverMsg(String receiverMsg) {
		this.receiverMsg = receiverMsg;
	}
	/**
	 * @return the commitTime
	 */
	public String getCommitTime() {
		return commitTime;
	}
	/**
	 * @param commitTime the commitTime to set
	 */
	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
}
