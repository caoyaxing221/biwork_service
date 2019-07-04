package com.biwork.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(value = {"handler"})
public class MeVo {
	private String name;
	private Integer defaultTeamId;
	private List<TeamVo> createTeams;
	private String phone;
	private String uuid;
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
	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	 * @return the defaultTeamId
	 */
	public Integer getDefaultTeamId() {
		return defaultTeamId;
	}
	/**
	 * @param defaultTeamId the defaultTeamId to set
	 */
	public void setDefaultTeamId(Integer defaultTeamId) {
		this.defaultTeamId = defaultTeamId;
	}
	/**
	 * @return the createTeams
	 */
	public List<TeamVo> getCreateTeams() {
		return createTeams;
	}
	/**
	 * @param createTeams the createTeams to set
	 */
	public void setCreateTeams(List<TeamVo> createTeams) {
		this.createTeams = createTeams;
	}
	
}
