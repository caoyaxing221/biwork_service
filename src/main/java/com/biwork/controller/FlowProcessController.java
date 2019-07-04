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
import com.biwork.po.request.AddressTemplateMsgPojo;
import com.biwork.po.request.AddressTemplatePojo;
import com.biwork.po.request.CommitProcessPojo;
import com.biwork.po.request.DealProcessPojo;
import com.biwork.po.request.EditFlowPojo;
import com.biwork.po.request.ReceiverMsgPojo;
import com.biwork.service.FlowProcessService;
import com.biwork.util.AESUtil;
import com.biwork.util.Constants;
import com.biwork.util.PropertiesUtil;
import com.biwork.vo.AddressTemplateListVo;
import com.biwork.vo.AddressTemplateVo;
import com.biwork.vo.FlowListVo;
import com.biwork.vo.FlowVo;
import com.biwork.vo.ProcessListVo;
import com.biwork.vo.ProcessVo;
import com.biwork.vo.TaskListVo;
import com.biwork.vo.TaskVo;

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
	@RequestMapping(value="/queryTemplate", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "地址模板id查询模板信息", notes = "地址模板id查询模板信息",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "templateId",value = "模板id", required = true, paramType = "query")
	})
	public RespPojo queryTask(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		AddressTemplateVo task=null;
		RespPojo resp=new RespPojo();
		String templateId=request.getParameter("templateId");
		
		if(StringUtils.isBlank(templateId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("模板id不能为空");
			  return resp;
		}
		
		
		try {
			  task=flowProcessService.queryTempalteInfo(templateId ,up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询地址模板异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("template", task);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/saveAddressTemplate", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "普通保存地址模版", notes = "普通保存地址模版",httpMethod = "POST")
//
	public RespPojo saveAddressTemplate(HttpServletRequest request,@RequestBody
			@ApiParam(name="模版对象",value="传入json格式",required=true) AddressTemplatePojo req){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		int templateId=0;
		String teamId=req.getTeamId();
		String name=req.getName();
		List<AddressTemplateMsgPojo> AddressTemplateMsg = req.getAddressTemplateMsg();
		
		String userId=up.getUserid();
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("未选择团队");
			  return resp;
		}
		if(StringUtils.isBlank(name)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("模版名称缺失");
			  return resp;
		}

	
		if(null==AddressTemplateMsg){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("未提交地址信息");
			  return resp;
		}
		try {
			templateId=flowProcessService.saveAddressTemplate(userId, teamId, name, AddressTemplateMsg);
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("提交地址模版异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("templateId", templateId);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/queryTemplateList", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "根据团队id查询地址模版列表", notes = "根据团队id查询地址模版列表",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query"),
		@ApiImplicitParam(name = "pageNo",value = "页码", required = true, paramType = "query"),
		@ApiImplicitParam(name = "pageSize",value = "每页条数", required = true, paramType = "query")
	})
	public RespPojo queryTemplateList(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		 List<AddressTemplateListVo> task=null;
		RespPojo resp=new RespPojo();
		String teamId=request.getParameter("teamId");
		String pageNo=request.getParameter("pageNo");
		String pageSize=request.getParameter("pageSize");
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(pageNo)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("页码不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(pageSize)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("每页条数不能为空");
			  return resp;
		}
		Integer fetch=(Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize);
		Integer totalCount;
		try {
			  task=flowProcessService.queryTemplateList(teamId, up.getUserid(),
					   pageSize,fetch.toString() );
			  totalCount=task.size()==0?0:Integer.parseInt(task.get(0).getCount());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询地址模版列表异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("addressTemplateList", task);
		 rtnMap.put("totalCount", totalCount);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
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
		String templateNo=StringUtils.isBlank(req.getTemplateNo())?"0":req.getTemplateNo();
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
					 authList, nodeList, userId,templateNo);
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
		String templateNo=StringUtils.isBlank(req.getTemplateNo())?"0":req.getTemplateNo();
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
					  authList, nodeList, userId,templateNo);
			
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
	@RequestMapping(value="/dealProcess", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "处理审批", notes = "处理审批",httpMethod = "POST")
	public RespPojo dealProcess(HttpServletRequest request,@RequestBody
			@ApiParam(name="审批对象",value="传入json格式",required=true) DealProcessPojo req){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		String processId=req.getProcessId();
		String dealFlag=req.getDealFlag();
		if(StringUtils.isBlank(processId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("未选择审批");
			  return resp;
		}
		
		if(StringUtils.isBlank(dealFlag)||(!"-2".equals(dealFlag)&&!"1".equals(dealFlag)&&!"-1".equals(dealFlag))){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("处理方式缺失");
			  return resp;
		}
		
		
		try {
			  flowProcessService.dealProcess(up.getUserid(), processId, Integer.parseInt(dealFlag));
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("审批异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("processId", processId);
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
	public RespPojo queryFlow(HttpServletRequest request){
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
	@RequestMapping(value="/queryFlow", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "普通用户根据id查询流程信息", notes = "普通用户根据id查询流程信息",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "flowId",value = "流程id", required = true, paramType = "query")
	})
	public RespPojo queryFlowUser(HttpServletRequest request){
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
			flow=flowProcessService.queryUseFlowById(flowId, up.getUserid());
			
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
	
	@ResponseBody
	@RequestMapping(value="/queryUseFlowList", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "普通账号查询可用流程列表", notes = "普通账号查询可用流程列表",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query")
	})
	public RespPojo queryUseFlowList(HttpServletRequest request){
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
			  flow=flowProcessService.queryUseFlows(teamId, up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("普通账号查询可用流程列表异常{}",e);
			  
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
	@RequestMapping(value="/queryProcess", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "根据id查询审批信息", notes = "根据id查询审批信息",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "processId",value = "审批id", required = true, paramType = "query")
	})
	public RespPojo queryProcess(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		ProcessVo process=null;
		RespPojo resp=new RespPojo();
		String processId=request.getParameter("processId");
		
		if(StringUtils.isBlank(processId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("审批id不能为空");
			  return resp;
		}
		
		
		try {
			process=flowProcessService.queryProcessById(processId, up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询审批异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(!StringUtils.isBlank(process.getAirDropTaskId())){
			try {
				process.setAirDropTaskToken(AESUtil.AESEncode(PropertiesUtil.getProperty("aeskey"), String.valueOf(process.getAirDropTaskId())));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("process", process);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/queryProcessList", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "查询审批列表", notes = "查询审批列表",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query"),
		@ApiImplicitParam(name = "type",value = "类别（-1我提交的,0待审批,1已审批）", required = true, paramType = "query"),
		@ApiImplicitParam(name = "pageNo",value = "页码", required = true, paramType = "query"),
		@ApiImplicitParam(name = "pageSize",value = "每页条数", required = true, paramType = "query")
	})
	public RespPojo queryProcessList(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		List<ProcessListVo> process=null;
		RespPojo resp=new RespPojo();
		String teamId=request.getParameter("teamId");
		String type=request.getParameter("type");
		String pageNo=request.getParameter("pageNo");
		String pageSize=request.getParameter("pageSize");
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(type)||(!type.equals("-1")&&!type.equals("0")&&!type.equals("1"))){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("类别有误");
			  return resp;
		}
		if(StringUtils.isBlank(pageNo)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("页码不能为空");
			  return resp;
		}
		
		
		if(StringUtils.isBlank(pageSize)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("每页条数不能为空");
			  return resp;
		}
		Integer offset =(Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize);
		Integer totalCount;
		try {
			if(type.equals("-1")){
				 process=flowProcessService.queryProcess(teamId, up.getUserid(), pageSize, offset.toString());
			}else{
				 process=flowProcessService.queryApproveProcess(teamId, up.getUserid(), pageSize, offset.toString(), type);
			}
			 
			totalCount=process.size()==0?0:Integer.parseInt(process.get(0).getCount());
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
		 
		 rtnMap.put("processList", process);
		 rtnMap.put("totalCount", totalCount);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/commitProcess", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "普通用户提交审批", notes = "普通用户提交审批",httpMethod = "POST")
//
	public RespPojo commitProcess(HttpServletRequest request,@RequestBody
			@ApiParam(name="审批对象",value="传入json格式",required=true) CommitProcessPojo req){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		int processId=0;
		String flowId=req.getFlowId();
		String applicationNumber=req.getApplicationNumber();
		String attachUrl=req.getAttachUrl();
		String categoryId=req.getCategoryId();
		String cause=req.getCause();
		String coinMark=req.getCoinMark();
		String departmentId = req.getDepartmentId();
		String receiver=req.getReceiver();
		List<ReceiverMsgPojo> receiverMsg = req.getReceiverMsg();
		String remark = req.getRemark();
		String userId=up.getUserid();
		String airDropTaskId=null==req.getAirDropTaskId()||"".equals(req.getAirDropTaskId())?null:req.getAirDropTaskId();
		String templateNo=StringUtils.isBlank(req.getTemplateNo())?"0":req.getTemplateNo();
		String coinRateId=req.getCoinRateId();
		if(StringUtils.isBlank(flowId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("未选择流程");
			  return resp;
		}
		if(StringUtils.isBlank(applicationNumber)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("申请编号缺失");
			  return resp;
		}
		
		if(StringUtils.isBlank(categoryId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("未选择付币类型");
			  return resp;
		}
		if(StringUtils.isBlank(coinMark)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("未选择币种");
			  return resp;
		}
		if(StringUtils.isBlank(coinRateId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("未选择币种");
			  return resp;
		}
		if(StringUtils.isBlank(departmentId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("未选择入账部门");
			  return resp;
		}
//		if(StringUtils.isBlank(receiver)){
//			  resp.setRetCode(Constants.PARAMETER_CODE);
//			  resp.setRetMsg("未选择收款人");
//			  return resp;
//		}
		if(null==receiverMsg){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("未提交地址信息");
			  return resp;
		}
		try {
			processId=flowProcessService.commitProcess(userId, flowId, applicationNumber, coinMark,
					cause, departmentId, categoryId, receiverMsg, receiver, remark, attachUrl,airDropTaskId,templateNo,coinRateId);
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("提交审批异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("processId", processId);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
			
	
}
