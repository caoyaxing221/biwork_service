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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biwork.exception.BusiException;
import com.biwork.po.RespPojo;
import com.biwork.po.UserPojo;
import com.biwork.po.request.AddFlowPojo;
import com.biwork.po.request.EditFlowPojo;
import com.biwork.service.FlowProcessService;
import com.biwork.util.Constants;
import com.biwork.vo.FlowListVo;
import com.biwork.vo.FlowVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
* @ClassName: FlowProcessController 
* @Description: 流程操作相关类
* @author cyx
* @date 2018年8月28日
*
 */
@Controller
@RequestMapping("/flow")
@Api(value = "/flow", description = "流程相关")
public class FlowProcessController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	FlowProcessService flowProcessService;
	
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "管理员创建流程", notes = "管理员创建流程",httpMethod = "POST")
//
	public RespPojo addFlow(HttpServletRequest request,@RequestBody
			@ApiParam(name="流程对象",value="传入json格式",required=true) AddFlowPojo req){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		int flowId=0;
		String teamId=req.getTeamId();
		String name=req.getName();
		String isBatch=req.getIsBatch();
		String visibleAll=req.getVisibleAll();
		String authList=req.getAuthList();
		String nodeList=req.getNodeList();
		String userId=up.getUserid();
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("未选择创建流程团队");
			  return resp;
		}
		if(StringUtils.isBlank(name)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("流程名称不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(isBatch)||(!"0".equals(isBatch)&&!"1".equals(isBatch))){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("是否批量选项不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(visibleAll)||(!"0".equals(visibleAll)&&!"1".equals(visibleAll))){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("流是否全员可见选项不能为空");
			  return resp;
		}
		if("0".equals(visibleAll)&&StringUtils.isBlank(authList)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("可见员工列表不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(nodeList)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("流程审批人列表不能为空");
			  return resp;
		}
		
		try {
			 flowId=flowProcessService.addFlow(teamId, name, isBatch, visibleAll, 
					 authList, nodeList, userId);
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("添加流程异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("flowId", flowId);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	
	@ResponseBody
	@RequestMapping(value="/edit", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "管理员编辑流程", notes = "管理员编辑流程",httpMethod = "POST")
	public RespPojo editFlow(HttpServletRequest request,@RequestBody
			@ApiParam(name="流程对象",value="传入json格式",required=true) EditFlowPojo req){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		String flowId=req.getFlowId();
		String name=req.getName();
		String isBatch=req.getIsBatch();
		String visibleAll=req.getVisibleAll();
		String authList=req.getAuthList();
		String nodeList=req.getNodeList();
		String userId=up.getUserid();
		if(StringUtils.isBlank(flowId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("未选择编辑流程");
			  return resp;
		}
		if(StringUtils.isBlank(name)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("流程名称不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(isBatch)||(!"0".equals(isBatch)&&!"1".equals(isBatch))){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("是否批量选项不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(visibleAll)||(!"0".equals(visibleAll)&&!"1".equals(visibleAll))){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("流是否全员可见选项不能为空");
			  return resp;
		}
		if("0".equals(visibleAll)&&StringUtils.isBlank(authList)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("可见员工列表不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(nodeList)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("流程审批人列表不能为空");
			  return resp;
		}
		
		try {
			  flowProcessService.editFlow(flowId, name, isBatch, visibleAll,
					  authList, nodeList, userId);
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("修改流程异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("flowId", flowId);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/query", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "流程管理根据id查询流程信息", notes = "流程管理根据id查询流程信息",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "flowId",value = "流程id", required = true, paramType = "query")
	})
	public RespPojo queryTeam(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		FlowVo flow=null;
		RespPojo resp=new RespPojo();
		String flowId=request.getParameter("flowId");
		
		if(StringUtils.isBlank(flowId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("流程id不能为空");
			  return resp;
		}
		
		
		try {
			flow=flowProcessService.queryFlowById(flowId, up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询流程异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("flow", flow);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/queryFlowList", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "管理员查询创建的流程列表", notes = "管理员查询创建的流程列表",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query")
	})
	public RespPojo queryFlowList(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		List<FlowListVo> flow=null;
		RespPojo resp=new RespPojo();
		String teamId=request.getParameter("teamId");
		
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		
		
		try {
			  flow=flowProcessService.queryFlows(teamId, up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("管理员查询创建流程列表异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("flowList", flow);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/delFlow", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "流程管理删除流程", notes = "流程管理删除流程",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "flowId",value = "流程id", required = true, paramType = "query")
	})
	public RespPojo delFlow(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		RespPojo resp=new RespPojo();
		String flowId=request.getParameter("flowId");
		
		if(StringUtils.isBlank(flowId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("记录id不能为空");
			  return resp;
		}
		
		
		try {
			  boolean result = flowProcessService.delFlow(flowId, up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("删除流程异常{}",e);
			  
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
			
	
}
