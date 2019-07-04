package com.biwork.vo;


public class InviteVo {
	private String id;
	private String inviterName;	
	private String teamName;
	private String inviterPhone;
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
	 * @return the inviterName
	 */
	public String getInviterName() {
		return inviterName;
	}
	/**
	 * @param inviterName the inviterName to set
	 */
	public void setInviterName(String inviterName) {
		this.inviterName = inviterName;
	}
	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}
	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	/**
	 * @return the inviterPhone
	 */
	public String getInviterPhone() {
		return inviterPhone.replaceAll("(?<=[\\d]{3})\\d(?=[\\d]{4})", "*");
	}
	/**
	 * @param inviterPhone the inviterPhone to set
	 */
	public void setInviterPhone(String inviterPhone) {
		this.inviterPhone = inviterPhone;
	}
	
	
	
}
