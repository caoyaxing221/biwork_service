package com.biwork.service.Impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biwork.entity.Member;
import com.biwork.entity.MemberInvite;
import com.biwork.entity.Team;
import com.biwork.entity.User;
import com.biwork.exception.BusiException;
import com.biwork.mapper.MemberInviteMapper;
import com.biwork.mapper.MemberMapper;
import com.biwork.mapper.ServiceMapper;
import com.biwork.mapper.TeamMapper;
import com.biwork.mapper.UserMapper;
import com.biwork.service.TeamService;
import com.biwork.util.Constants;
import com.biwork.vo.MemberVo;
import com.biwork.vo.TeamVo;



@Service("TeamService")
public class TeamServiceImpl implements TeamService {
	static Logger log = LoggerFactory.getLogger(TeamService.class);
	@Autowired
	private TeamMapper teamMapper;
	@Autowired
	private ServiceMapper serviceMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	MemberInviteMapper memberInviteMapper;
	@Autowired
	MemberMapper memberMapper;

	@Override
	public List<TeamVo> getJoinTeams() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int addTeam(String userId, String name, String email, String stuffNum, String adminName) {
		List<TeamVo> teamList = teamMapper.selectByCreateUserId(Integer.parseInt(userId));
		if(teamList.size()>0){
			throw new BusiException(Constants.FAIL_CODE,Constants.MAX_TEAM_MESSAGE);
		}
		Team team=new Team();
		team.setAdminName(adminName);
		team.setCreateUserId(Integer.parseInt(userId));
		team.setEmail(email);
		team.setName(name);
		team.setStuffNum(Integer.parseInt(stuffNum));
		teamMapper.insertSelective(team);
		int teamId=team.getId();
		//更新管理员姓名//设为默认团队
		User user=  new User();
		user.setId(Integer.parseInt(userId));
		user.setName(adminName);
		user.setUpdatetime(new Date());
		user.setDefaultTeamId(teamId);
		userMapper.updateByPrimaryKeySelective(user);
		
		return teamId;
	}
	@Override
	public boolean editTeam(String userId, String name, String email, String stuffNum, String adminName,
			String teamId) {
		Team teamDb = teamMapper.selectByPrimaryKey(Integer.parseInt(teamId));
		if(null==teamDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.TEAM_NOT_FOUND);
		}
		if(!teamDb.getCreateUserId().toString().equals(userId)){
			throw new BusiException(Constants.FAIL_CODE,Constants.TEAM_NOT_FOUND);
		}
		Team team=new Team();
		team.setId(teamDb.getId());
		team.setAdminName(adminName);
		team.setEmail(email);
		team.setName(name);
		team.setStuffNum(Integer.parseInt(stuffNum));
		teamMapper.updateByPrimaryKeySelective(team);
		//更新管理员姓名
		User user=  new User();
		user.setId(Integer.parseInt(userId));
		user.setName(adminName);
		user.setUpdatetime(new Date());
		userMapper.updateByPrimaryKeySelective(user);
		return false;
	}
	@Override
	public List<MemberVo> queryTeamInvite(String teamId,String userId) {
		List<MemberVo> inviteList=memberInviteMapper.selectByTeamId(Integer.parseInt(teamId),userId);
		return inviteList;
	}
	@Override
	public List<MemberVo> queryTeamMembers(String teamId,String userId) {
		List<MemberVo> mermberList=memberMapper.selectByTeamId(Integer.parseInt(teamId),userId);
		return mermberList;
	}
	@Override
	public int addInvite(String teamId,String userId,String name, String phone) {
		com.biwork.entity.Service service = serviceMapper.selectByPrimaryKey(1);
		List<MemberVo> inviteList=memberInviteMapper.selectByTeamId(Integer.parseInt(teamId),userId);
		if(inviteList.size()>=service.getMaxAccount()){
			throw new BusiException(Constants.FAIL_CODE,Constants.MAX_ACCOUNT_MESSAGE);
		}
		Team teamDb = teamMapper.selectByPrimaryKey(Integer.parseInt(teamId));
		if(null==teamDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.TEAM_NOT_FOUND);
		}
		if(!teamDb.getCreateUserId().toString().equals(userId)){
			throw new BusiException(Constants.FAIL_CODE,Constants.TEAM_NOT_FOUND);
		}
		MemberVo mVo=memberInviteMapper.selectByPhone(phone,Integer.parseInt(teamId));
		if(null!=mVo){
			throw new BusiException(Constants.FAIL_CODE,Constants.INVITE_ALREADY_EXISTS);
		}
		MemberInvite minvite=new MemberInvite();
		minvite.setInviterId(Integer.parseInt(userId));
		minvite.setName(name);
		minvite.setPhone(phone);
		minvite.setTeamId(Integer.parseInt(teamId));
		 memberInviteMapper.insertSelective(minvite);
		 return minvite.getId();
		 
	}
	@Override
	public boolean delInvite(String inviteId,String userId) {
		MemberInvite mInvitedb = memberInviteMapper.selectByPrimaryKey(Integer.parseInt(inviteId));
		if(null==mInvitedb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		if(!mInvitedb.getInviterId().toString().equals(userId)){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		MemberInvite minvite=new MemberInvite();
		minvite.setId(mInvitedb.getId());
		minvite.setState(-2);
		minvite.setUpdatetime(new Date());
		memberInviteMapper.updateByPrimaryKeySelective(minvite);
		//同时删除团队成员
		List<MemberVo> memberDb = memberMapper.selectByTeamId(mInvitedb.getTeamId(), userId);
		if( memberDb.size()>0){
			Member member=new Member();
			member.setId(Integer.parseInt(memberDb.get(0).getId()));
			member.setState(-1);
			member.setUpdatetime(new Date());
			memberMapper.updateByPrimaryKeySelective(member);
		}
		
		return true;
	}
	@Override
	public Team queryTeamById(String teamId,String userId) {
		Team teamDb = teamMapper.selectByPrimaryKey(Integer.parseInt(teamId));
		if(null==teamDb||!teamDb.getCreateUserId().toString().equals(userId)){
			throw new BusiException(Constants.FAIL_CODE,Constants.TEAM_NOT_FOUND);
		}
		return teamDb;
	}
	@Override
	public boolean setDefaultTeam(String teamId,String userId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean joinTeam(String inviteId,String userId) {
		MemberInvite mInvitedb = memberInviteMapper.selectByPrimaryKey(Integer.parseInt(inviteId));
		if(null==mInvitedb||mInvitedb.getState()!=0){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		User userDb = userMapper.selectByPrimaryKey(Integer.parseInt(userId));
		if(!mInvitedb.getPhone().equals(userDb.getPhone())){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		MemberInvite mInvite=new MemberInvite();
		mInvite.setId(mInvitedb.getId());
		mInvite.setState(1);
		mInvite.setUpdatetime(new Date());
		memberInviteMapper.updateByPrimaryKeySelective(mInvite);
		Member member=new Member();
		member.setInviterId(mInvitedb.getId());
		member.setName(mInvitedb.getName());
		member.setPhone(mInvitedb.getPhone());
		member.setTeamId(mInvitedb.getTeamId());
		member.setUserId(Integer.parseInt(userId));
		memberMapper.insertSelective(member);
		// 如果没有默认团队，设为默认
		if(null==userDb.getDefaultTeamId()){
			User user=new User();
			user.setId(userDb.getId());
			user.setDefaultTeamId(mInvitedb.getTeamId());
			user.setUpdatetime(new Date());
			userMapper.updateByPrimaryKeySelective(user);
		}
		return false;
	}
	@Override
	public boolean rejectInvite(String inviteId, String userId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	


}
