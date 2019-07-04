package com.biwork.po;

public class RespPojo {

	private String retCode;
	private String retMsg;
	private Object data;
	/**
	 * @return the retCode
	 */
	public String getRetCode() {
		return retCode;
	}
	/**
	 * @param retCode the retCode to set
	 */
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	/**
	 * @return the retMsg
	 */
	public String getRetMsg() {
		return retMsg;
	}
	/**
	 * @param retMsg the retMsg to set
	 */
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	/*
	* Title: toString
	*Description: 
	* @return 
	* @see java.lang.Object#toString() 
	*/
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RespPojo [retCode=");
		builder.append(retCode);
		builder.append(", retMsg=");
		builder.append(retMsg);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}
}
