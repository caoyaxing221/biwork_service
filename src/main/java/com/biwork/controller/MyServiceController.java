package com.biwork.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biwork.entity.Currency;
import com.biwork.entity.Service;
import com.biwork.entity.Version;
import com.biwork.exception.BusiException;
import com.biwork.po.RespPojo;
import com.biwork.po.TeamSeed;
import com.biwork.po.UserPojo;
import com.biwork.service.MyService;
import com.biwork.service.TeamService;
import com.biwork.util.Constants;
import com.biwork.util.IDWorker;
import com.biwork.util.TimeUtils;
import com.biwork.vo.MeVo;
import com.biwork.vo.TeamVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
* @ClassName: MyServiceController 
* @Description: 我的服务
* @author cyx
* @date 2018年8月24日
*
 */
@Controller
@RequestMapping("/myService")
@Api(value = "/myService", description = "我的服务及个人信息")
public class MyServiceController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	MyService myService;
	@Autowired
	TeamService teamService;
	
	@ResponseBody
	@RequestMapping("/query")
	@ApiOperation(value = "查询我的服务", notes = "查询我的服务",httpMethod = "GET")
	
	public RespPojo query(HttpServletRequest request){
		
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		Service service=null;
		
		try {
			 service = myService.getService(Integer.parseInt(up.getUserid()));
		}
		catch (Exception e) {
			  logger.error("查询服务异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("name", service.getName());
		rtnMap.put("expireDate", service.getExpireDate());
		rtnMap.put("maxAccount", service.getMaxAccount());
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		resp.setData(rtnMap);
		    
		return resp;
		
	}		
	@ResponseBody
	@RequestMapping("/queryCurrency")
	@ApiOperation(value = "查询币种列表", notes = "查询币种列表",httpMethod = "GET")
	
	public RespPojo queryCurrency(HttpServletRequest request){
		
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		List<Currency> currency=null;
		
		try {
			currency = myService.getCurrency();
		}
		catch (Exception e) {
			  logger.error("查询币种异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("currency", currency);
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		resp.setData(rtnMap);
		    
		return resp;
		
	}
	@ResponseBody
	@RequestMapping("/queryApprovalCategory")
	@ApiOperation(value = "查询付币类别列表", notes = "查询付币类别列表",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query")
	})
	public RespPojo queryApprovalCategory(HttpServletRequest request){
		
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		String teamId=request.getParameter("teamId");
		
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		List<TeamVo>category=null;
		try {
			 category = myService.getApprovalCategoryList(teamId,up.getUserid());
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询付币类别异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("category", category);
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		resp.setData(rtnMap);
		    
		return resp;
		
	}
	@ResponseBody
	@RequestMapping("/queryDepartment")
	@ApiOperation(value = "查询部门列表", notes = "查询部门列表",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query")
	})
	public RespPojo queryDepartment(HttpServletRequest request){
		
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		String teamId=request.getParameter("teamId");
		
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		List<TeamVo>department=null;
		try {
			 department = myService.getDepartmentList(teamId,up.getUserid());
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询部门异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("department", department);
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		resp.setData(rtnMap);
		    
		return resp;
		
	}
	@ResponseBody
	@RequestMapping("/getApproveNo")
	@ApiOperation(value = "生成审批编号", notes = "生成审批编号",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query")
	})
	public RespPojo getApproveNo(HttpServletRequest request){
		
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		String approveNo="";
		RespPojo resp=new RespPojo();
		String teamId=request.getParameter("teamId");
		
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		Integer seed=0;
		try {
//			approveNo=IDWorker.nextID("");
			TeamSeed td =new TeamSeed();
			td.setId(Integer.parseInt(teamId));
			seed=teamService.updateSeedByTeamId(td);
		}
		catch (Exception e) {
			  logger.error("生成审批编号{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		approveNo=new StringBuilder().append(TimeUtils.getDate8()).append(String.format("%03d", seed)).toString();
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("approveNo", approveNo);
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		resp.setData(rtnMap);
		    
		return resp;
		
	}
	@ResponseBody
	@RequestMapping("/getMe")
	@ApiOperation(value = "查询我的信息", notes = "查询我的信息",httpMethod = "GET")
	
	public RespPojo getMe(HttpServletRequest request){
		
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		Service service=null;
		 MeVo userInfo=null;
		try {
			  userInfo = myService.getMe(up.getUserid());
			  userInfo.getCreateTeams();
		}
		catch (Exception e) {
			  logger.error("查询个人信息异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("userInfo", userInfo);
		rtnMap.put("roleId", up.getRoleid());
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		resp.setData(rtnMap);
		    
		return resp;
		
	}
	@ResponseBody
	@RequestMapping("/getCurrentVersion")
	@ApiOperation(value = "版本更新", notes = "版本更新",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type",value = "客户端类型(IOS/ANDROID)", required = true, paramType = "query"),
	})
	public RespPojo getCurrentVersion(HttpServletRequest request){
		
		
		
		RespPojo resp=new RespPojo();
		String type=request.getParameter("type");
//		if(StringUtils.isBlank(type)){
//			  resp.setRetCode(Constants.PARAMETER_CODE);
//			  resp.setRetMsg("客户端类型不能为空");
//			  return resp;
//		}
		Version version=null;
		List<Version> versions=null;
		try {
			
			if(StringUtils.isBlank(type)){
				versions=myService.getCurrentVersionBoth();
			}else{
				version = myService.getCurrentVersion(type);
			}
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询最新客户端版本异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		Map<String, Object> appVersionList = new HashMap<String, Object>();
		if(StringUtils.isBlank(type)){
			for(int i=0;i<versions.size();i++){
				Map<String, Object> appVersion = new HashMap<String, Object>();
				appVersion.put("type", versions.get(i).getType());
				appVersion.put("version",versions.get(i).getNewversion());
				appVersion.put("downloadUrl",versions.get(i).getApkurl());
				appVersion.put("updateDescription",versions.get(i).getUpdatedescription());
				appVersion.put("forceUpdate", versions.get(i).getForceUpdate());
				appVersionList.put(versions.get(i).getType(), appVersion);
			}
			rtnMap.put("versionInfo", appVersionList);
		}else{
			Map<String, Object> appVersion = new HashMap<String, Object>();
			appVersion.put("type", version.getType());
			appVersion.put("version",version.getNewversion());
			appVersion.put("downloadUrl",version.getApkurl());
			appVersion.put("updateDescription",version.getUpdatedescription());
			appVersion.put("forceUpdate", version.getForceUpdate());
			rtnMap.put("versionInfo", appVersion);
		}
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		resp.setData(rtnMap);
		    
		return resp;
		
	}
	
}
