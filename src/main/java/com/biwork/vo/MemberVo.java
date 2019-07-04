package com.biwork.vo;


public class MemberVo {
	private String id;
	private String name;
	private String inviterId;
	private String phone;
	private String userId;
	private Integer state;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the inviterId
	 */
	public String getInviterId() {
		return inviterId;
	}
	/**
	 * @param inviterId the inviterId to set
	 */
	public void setInviterId(String inviterId) {
		this.inviterId = inviterId;
	}
	
	
}
