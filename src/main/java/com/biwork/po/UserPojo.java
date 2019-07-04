package com.biwork.po;

public class UserPojo {

	private String userid;
	private String roleid;
	private String token;
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
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
		builder.append("UserPojo [userid=");
		builder.append(userid);
		builder.append(", token=");
		builder.append(token);
		builder.append("]");
		return builder.toString();
	}

}
