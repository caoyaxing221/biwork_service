package com.biwork.service;

import java.util.List;

import com.biwork.entity.Team;
import com.biwork.po.TeamSeed;
import com.biwork.vo.InviteVo;
import com.biwork.vo.MemberVo;
import com.biwork.vo.TeamInfoVo;
import com.biwork.vo.TeamVo;

public interface TeamService {	 
	List<TeamVo> getJoinTeams(String userId);
	boolean setDefaultTeam(String teamId,String userId);
	boolean joinTeam(String inviteId,String userId);
	boolean rejectInvite(String inviteId ,String userId);
	int addTeam(String userId ,String name,String email,String stuffNum,String adminName);
	boolean editTeam(String userId ,String name,String email,String stuffNum,String adminName,String teamId);
	List<MemberVo> queryTeamInvite(String teamId,String userId);
	List<MemberVo> queryTeamMembers(String teamId,String userId);
	int addInvite(String teamId,String userId,String name,String phone);
	boolean delInvite(String inviteId,String userId);
	TeamInfoVo queryTeamById(String teamId,String userId);
	List<TeamVo> queryTeamSize();
	List<InviteVo> queryInviteList(String userId);
	String getInviteCode(String teamId, String userId);
	int updateSeedByTeamId(TeamSeed teamSeed);
}
