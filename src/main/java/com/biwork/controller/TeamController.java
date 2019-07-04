package com.biwork.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biwork.entity.Team;
import com.biwork.entity.User;

import com.biwork.exception.BusiException;
import com.biwork.po.RespPojo;
import com.biwork.po.UserPojo;
import com.biwork.po.request.AddMemberPojo;
import com.biwork.po.request.AddTeamPojo;
import com.biwork.po.request.EditTeamPojo;
import com.biwork.service.TeamService;
import com.biwork.util.Constants;
import com.biwork.util.ValidateUtil;
import com.biwork.vo.InviteVo;
import com.biwork.vo.MemberVo;
import com.biwork.vo.TeamInfoVo;
import com.biwork.vo.TeamVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
* @ClassName: TeamController 
* @Description: 团队操作相关类
* @author cyx
* @date 2018年8月25日
*
 */
@Controller
@RequestMapping("/team")
@Api(value = "/team", description = "团队相关")
public class TeamController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	TeamService teamService;
	
	
			
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "管理员创建团队", notes = "管理员创建团队",httpMethod = "POST")
//	@ApiImplicitParams({
//        @ApiImplicitParam(name = "name",value = "团队名称", required = true, paramType = "body"),
//        @ApiImplicitParam(name = "email",value = "邮箱", required = true, paramType = "body"),
//        @ApiImplicitParam(name = "stuffNum",value = "公司人数", required = true, paramType = "body"),
//        @ApiImplicitParam(name = "adminName",value = "管理员姓名", required = true, paramType = "body")
//    })
	public RespPojo addTeam(HttpServletRequest request,@RequestBody
			@ApiParam(name="团队对象",value="传入json格式",required=true) AddTeamPojo addTeamPo){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		int teamId=0;
	
		if(StringUtils.isBlank(addTeamPo.getName())){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队名称不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(addTeamPo.getEmail())){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("邮箱不能为空");
			  return resp;
		}
		if(!ValidateUtil.isEmail(addTeamPo.getEmail())){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("邮箱格式错误");
			  return resp;
		}
		if(StringUtils.isBlank(addTeamPo.getStuffNum())){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("公司人数不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(addTeamPo.getAdminName())){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("管理员姓名不能为空");
			  return resp;
		}
		
		try {
			 teamId= teamService.addTeam(up.getUserid(), addTeamPo.getName(), addTeamPo.getEmail(),
					 addTeamPo.getStuffNum(), addTeamPo.getAdminName());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("添加团队异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("teamId", teamId);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	
	@ResponseBody
	@RequestMapping(value="/edit", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "管理员编辑团队", notes = "管理员编辑团队",httpMethod = "POST")
	public RespPojo editTeam(HttpServletRequest request,@RequestBody
			@ApiParam(name="团队对象",value="传入json格式",required=true) EditTeamPojo req){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		String teamId=req.getTeamId();
		String name=req.getName();
		String email=req.getEmail();
		String stuffNum=req.getStuffNum();
		String adminName=req.getAdminName();
		if(StringUtils.isBlank(name)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队名称不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(email)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("邮箱不能为空");
			  return resp;
		}
		if(!ValidateUtil.isEmail(email)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("邮箱格式错误");
			  return resp;
		}
		if(StringUtils.isBlank(stuffNum)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("公司人数不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(adminName)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("管理员姓名不能为空");
			  return resp;
		}
		
		try {
			  teamService.editTeam(up.getUserid(), name, email, stuffNum, adminName, teamId);
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("修改团队异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("teamId", teamId);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/queryTeamSize", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "团队管理查询团队规模", notes = "团队管理查询团队规模",httpMethod = "GET")
	
	public RespPojo queryTeamSize(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		List<TeamVo> team=null;
		RespPojo resp=new RespPojo();
		
		
		
		
		try {
			  team=teamService.queryTeamSize();
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询团队规模异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("teamSize", team);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/query", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "团队管理根据id查询团队信息", notes = "团队管理根据id查询团队信息",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query")
	})
	public RespPojo queryTeam(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		TeamInfoVo team=null;
		RespPojo resp=new RespPojo();
		String teamId=request.getParameter("teamId");
		
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		
		
		try {
			  team=teamService.queryTeamById(teamId,up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询团队异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("team", team);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/addMember", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "团队管理添加成员", notes = "团队管理添加成员",httpMethod = "POST")
	public RespPojo addMember(HttpServletRequest request,@RequestBody
			@ApiParam(name="成员信息",value="传入json格式",required=true) AddMemberPojo req){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		String name=req.getName();
		String phone=req.getPhone();
		String teamId=req.getTeamId();
		RespPojo resp=new RespPojo();
		int inviteId=0;
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(name)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("姓名不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(phone)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("手机号不能为空");
			  return resp;
		}
		if(!ValidateUtil.isMobile(phone)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("手机号格式错误");
			  return resp;
		}
		
		
		try {
			inviteId= teamService.addInvite(teamId, up.getUserid(), name, phone);
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("添加团队成员异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("inviteId", teamId);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/queryInviteList", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "团队管理根据团队id查询添加的成员邀请", notes = "团队管理根据团队id查询添加的成员邀请",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query")
	})
	public RespPojo queryInviteList(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		 List<MemberVo> team=null;
		RespPojo resp=new RespPojo();
		String teamId=request.getParameter("teamId");
		
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		
		
		try {
			  team=teamService.queryTeamInvite(teamId,up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询邀请添加异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("inviteList", team);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/delMember", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "团队管理删除成员", notes = "团队管理删除成员",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "inviteId",value = "邀请记录id", required = true, paramType = "query")
	})
	public RespPojo delMember(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		RespPojo resp=new RespPojo();
		String inviteId=request.getParameter("inviteId");
		
		if(StringUtils.isBlank(inviteId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("记录id不能为空");
			  return resp;
		}
		
		
		try {
			  boolean result = teamService.delInvite(inviteId, up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("删除成员异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/queryTeamMember", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "根据团队id查询成员列表(已加入)", notes = "根据团队id查询成员列表(已加入)",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query")
	})
	public RespPojo queryTeamMember(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		 List<MemberVo> team=null;
		RespPojo resp=new RespPojo();
		String teamId=request.getParameter("teamId");
		
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		
		
		try {
			  team=teamService.queryTeamMembers(teamId,up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询团队列表异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("memberList", team);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/join", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "员工加入团队", notes = "员工加入团队",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "inviteId",value = "邀请记录id", required = true, paramType = "query")
	})
	public RespPojo join(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		RespPojo resp=new RespPojo();
		String inviteId=request.getParameter("inviteId");
		
		if(StringUtils.isBlank(inviteId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("记录id不能为空");
			  return resp;
		}
		
		
		try {
			  boolean result = teamService.joinTeam(inviteId, up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("加入团队异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/getInviteList", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "普通账号查询团队邀请", notes = "普通账号查询团队邀请",httpMethod = "GET")
	public RespPojo getInviteList(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		 List<InviteVo> team=null;
		RespPojo resp=new RespPojo();
		
		
		try {
			  team=teamService.queryInviteList(up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询邀请异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("inviteList", team);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/queryJoinTeams", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "普通账号查询加入的团队列表", notes = "普通账号查询加入的团队列表",httpMethod = "GET")
	public RespPojo queryJoinTeams(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		 List<TeamVo> team=null;
		RespPojo resp=new RespPojo();
		
		
		try {
			  team=teamService.getJoinTeams(up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询团队异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("teamList", team);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/setDefault", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "员工设置默认团队", notes = "员工设置默认团队",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query")
	})
	public RespPojo setDefault(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		RespPojo resp=new RespPojo();
		String teamId=request.getParameter("teamId");
		
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		
		
		try {
			  boolean result = teamService.setDefaultTeam(teamId, up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("设置默认团队异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	
	@ResponseBody
	@RequestMapping(value="/getInviteCode", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "团队管理邀请注册", notes = "团队管理邀请注册生成邀请码",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query")
	})
	public RespPojo getInviteCode(HttpServletRequest request){
		UserPojo up=new UserPojo();
		RespPojo resp=new RespPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		String teamId=request.getParameter("teamId");
		String inviteCode="";
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		
		
		try {
			inviteCode= teamService.getInviteCode(teamId, up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("团队管理邀请注册生成邀请码异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("inviteCode", inviteCode);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
}
