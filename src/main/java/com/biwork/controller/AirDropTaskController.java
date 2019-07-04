package com.biwork.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;

import com.biwork.entity.User;

import com.biwork.exception.BusiException;
import com.biwork.po.RespPojo;
import com.biwork.po.UserPojo;
import com.biwork.po.request.AddAddressPojo;
import com.biwork.po.request.AddAirDropTaskPojo;
import com.biwork.po.request.EditAirDropTaskPojo;
import com.biwork.service.AirdropTaskService;
import com.biwork.util.Constants;
import com.biwork.util.ValidateUtil;
import com.biwork.vo.AddressListVo;
import com.biwork.vo.TaskListVo;
import com.biwork.vo.TaskVo;
import com.biwork.util.AESUtil;
import com.biwork.util.PropertiesUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
* @ClassName: AirDropTaskController 
* @Description: 团队操作相关类
* @author cyx
* @date 2018年8月25日
*
 */
@Controller
@RequestMapping("/airDropTask")
@Api(value = "/airDropTask", description = "空投活动相关")
public class AirDropTaskController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	AirdropTaskService airDropTaskService;
	
	
			
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "管理员创建空投任务", notes = "管理员创建空投任务",httpMethod = "POST")

	public RespPojo addAirDropTask(HttpServletRequest request,@RequestBody
			@ApiParam(name="空投任务对象",value="传入json格式",required=true) AddAirDropTaskPojo req){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		int taskId=0;
		String teamId=req.getTeamId();
		String name =req.getName();
		String endTime =req.getEndTime()==null||"".equals(req.getEndTime())?null:req.getEndTime();
		String title =req.getTitle()==null||"".equals(req.getTitle())?null:req.getTitle();
		String remark =req.getRemark()==null||"".equals(req.getRemark())?null:req.getRemark();
		String bannerUrl=req.getBannerUrl()==null||"".equals(req.getBannerUrl())?null:req.getBannerUrl();
		String needAttach=req.getNeedAttach()==null||"".equals(req.getNeedAttach())?null:req.getNeedAttach();
		String type=req.getType();
		if(StringUtils.isBlank(teamId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("团队id不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(name)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("活动名称不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(type)||(!type.equals("0")&&!type.equals("1"))){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("活动类别不能为空");
			  return resp;
		}
		if(!req.getType().equals("1")){
			if(!ValidateUtil.isDateTime(endTime)){
				  resp.setRetCode(Constants.PARAMETER_CODE);
				  resp.setRetMsg("截止日期格式错误");
				  return resp;
			}
			if(StringUtils.isBlank(title)){
				  resp.setRetCode(Constants.PARAMETER_CODE);
				  resp.setRetMsg("活动标题不能为空");
				  return resp;
			}
			if(StringUtils.isBlank(remark)){
				  resp.setRetCode(Constants.PARAMETER_CODE);
				  resp.setRetMsg("活动备注不能为空");
				  return resp;
			}
			if(StringUtils.isBlank(bannerUrl)){
				  resp.setRetCode(Constants.PARAMETER_CODE);
				  resp.setRetMsg("活动banner不能为空");
				  return resp;
			}
			if(!ValidateUtil.isIMG(bannerUrl)){
				  resp.setRetCode(Constants.PARAMETER_CODE);
				  resp.setRetMsg("活动banner格式错误");
				  return resp;
			}
			if(StringUtils.isBlank(needAttach)){
				  resp.setRetCode(Constants.PARAMETER_CODE);
				  resp.setRetMsg("需要上传附件不能为空");
				  return resp;
			}
		}
		
		
		
		try {
			 taskId= airDropTaskService.addTask(teamId, up.getUserid(), name, endTime, title, remark,needAttach, bannerUrl, type);
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("添加空投活动异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 String taskToken="";
		 try {
			taskToken=AESUtil.AESEncode(PropertiesUtil.getProperty("aeskey"), String.valueOf(taskId));
		} catch (Exception e) {
			logger.error("添加空投活动生成token异常{}",e);
//			e.printStackTrace();
		}
		 rtnMap.put("taskId", taskId);
		 rtnMap.put("taskToken", taskToken);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	
	@ResponseBody
	@RequestMapping(value="/edit", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "管理员编辑空投活动", notes = "管理员编辑空投活动",httpMethod = "POST")
	public RespPojo editTask(HttpServletRequest request,@RequestBody
			@ApiParam(name="空投活动编辑对象",value="传入json格式",required=true) EditAirDropTaskPojo req){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		String taskId=req.getTaskId();
		String name =req.getName();
		String endTime =req.getEndTime();
		String title =req.getTitle();
		String remark =req.getRemark();
		String bannerUrl=req.getBannerUrl();
		String needAttach=req.getNeedAttach();
		if(StringUtils.isBlank(taskId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("活动id不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(name)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("活动名称不能为空");
			  return resp;
		}
		if(!ValidateUtil.isDateTime(endTime)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("截止日期格式错误");
			  return resp;
		}
		if(StringUtils.isBlank(title)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("活动标题不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(remark)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("活动备注不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(needAttach)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("是否需要附件");
			  return resp;
		}
		if(StringUtils.isBlank(bannerUrl)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("活动banner不能为空");
			  return resp;
		}
		
		if(!ValidateUtil.isIMG(bannerUrl)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("活动banner格式错误");
			  return resp;
		}
		try {
			  airDropTaskService.editTask(taskId, up.getUserid(), name, endTime, title, remark,needAttach, bannerUrl);
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("修改活动异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		 String taskToken="";
		 try {
			taskToken=AESUtil.AESEncode(PropertiesUtil.getProperty("aeskey"), String.valueOf(taskId));
		} catch (Exception e) {
			logger.error("修改空投活动生成token异常{}",e);
//			e.printStackTrace();
		}
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("taskId",taskId);
		 rtnMap.put("taskToken", taskToken);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/query", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "空投活动根据id查询活动信息", notes = "空投活动根据id查询活动信息",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId",value = "活动id", required = true, paramType = "query")
	})
	public RespPojo queryTask(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		TaskVo task=null;
		RespPojo resp=new RespPojo();
		String taskId=request.getParameter("taskId");
		
		if(StringUtils.isBlank(taskId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("活动id不能为空");
			  return resp;
		}
		
		
		try {
			  task=airDropTaskService.queryTaskInfo(taskId, null);
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询空投活动异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("task", task);
		 try {
			task.setToken(AESUtil.AESEncode(PropertiesUtil.getProperty("aeskey"), String.valueOf(task.getId())));
		} catch (Exception e) {
			logger.error("查询空投活动异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/addAddress", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "空投活动提交地址", notes = "空投活动提交地址",httpMethod = "POST")
	public RespPojo addMember(HttpServletRequest request,@RequestBody
			@ApiParam(name="地址信息",value="传入json格式",required=true) AddAddressPojo req){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		String address=req.getAddress();
		String taskToken=req.getTaskToken();
		String attachUrl=req.getAttachUrl();
		String taskId = null;
		try {
			taskId = AESUtil.AESDncode(PropertiesUtil.getProperty("aeskey"), taskToken);
		} catch (Exception e1) {
			 logger.error("添加地址解析Token异常{}",e1);
		}
		RespPojo resp=new RespPojo();
		int addressId=0;
		if(StringUtils.isBlank(taskId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("活动id不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(address)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("地址不能为空");
			  return resp;
		}
		if(!ValidateUtil.isAddress(address)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("地址格式错误");
			  return resp;
		}
		try {
			addressId= airDropTaskService.addAddress(taskId, address,attachUrl);
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("添加地址异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("addressId", addressId);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/queryTaskList", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "空投活动根据团队id查询活动列表", notes = "空投活动根据团队id查询活动列表",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "teamId",value = "团队id", required = true, paramType = "query"),
		@ApiImplicitParam(name = "type",value = "活动类型 0 空投活动 1 导入地址 不传查所有", required = false, paramType = "query"),
		@ApiImplicitParam(name = "pageNo",value = "页码", required = true, paramType = "query"),
		@ApiImplicitParam(name = "pageSize",value = "每页条数", required = true, paramType = "query")
	})
	public RespPojo queryAirDropTaskList(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		 List<TaskListVo> task=null;
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
			  task=airDropTaskService.queryTaskList(teamId, up.getUserid(),
					  type, pageSize,fetch.toString() );
			  totalCount=task.size()==0?0:Integer.parseInt(task.get(0).getCount());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询空投活动列表异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		for(int i=0;i<task.size();i++){
			 try {
					task.get(i).setToken(AESUtil.AESEncode(PropertiesUtil.getProperty("aeskey"), String.valueOf(task.get(i).getId())));
				} catch (Exception e) {
					logger.error("查询空投活动异常{}",e);
					  
					  resp.setRetCode(Constants.FAIL_CODE);
					  resp.setRetMsg(Constants.FAIL_MESSAGE);
					  return resp;
				}
		}
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("taskList", task);
		 rtnMap.put("totalCount", totalCount);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	
	@ResponseBody
	@RequestMapping(value="/queryAddressList", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "空投活动根据活动id查询地址列表", notes = "空投活动根据活动id查询地址列表",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId",value = "活动id", required = true, paramType = "query"),
		@ApiImplicitParam(name = "pageNo",value = "页码", required = true, paramType = "query"),
		@ApiImplicitParam(name = "pageSize",value = "每页条数", required = true, paramType = "query")
	})
	public RespPojo queryAirDropAddressList(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		 List<AddressListVo> address=null;
		RespPojo resp=new RespPojo();
		String taskId=request.getParameter("taskId");
		String pageNo=request.getParameter("pageNo");
		String pageSize=request.getParameter("pageSize");
		if(StringUtils.isBlank(taskId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("活动id不能为空");
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
			  address=airDropTaskService.queryAddressList(taskId, up.getUserid(), pageSize,fetch.toString());
			  totalCount=address.size()==0?0:Integer.parseInt(address.get(0).getCount());
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询空投活动地址异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		 rtnMap.put("addressList", address);
		 rtnMap.put("totalCount", totalCount);
		 resp.setRetCode(Constants.SUCCESSFUL_CODE);
		 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		 resp.setData(rtnMap);
		    
		return resp;
	
		
	}
	@ResponseBody
	@RequestMapping(value="/endTask", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "空投活动结束活动", notes = "空投活动结束活动",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "taskId",value = "活动id", required = true, paramType = "query")
	})
	public RespPojo delMember(HttpServletRequest request){
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		RespPojo resp=new RespPojo();
		String taskId=request.getParameter("taskId");
		
		if(StringUtils.isBlank(taskId)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("记录id不能为空");
			  return resp;
		}
		
		
		try {
			  boolean result = airDropTaskService.endTask(taskId, up.getUserid());
			
		}
		catch(BusiException e){
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("结束空投活动异常{}",e);
			  
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
	/**
	   * 地址文件上传
	   */
	  @ResponseBody
	  @RequestMapping(value = "/addressUpload",method = RequestMethod.POST,consumes="multipart/*",headers="content-type=multipart/form-data")
	  @ApiOperation(value = "上传地址", notes = "上传地址")
	  @ApiImplicitParams({
			@ApiImplicitParam(name = "taskToken",value = "活动Token", required = true, paramType = "query")
		})
	  public RespPojo photoUpload(@ApiParam(value="上传的文件",required=true)MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException{
			RespPojo resp=new RespPojo();
			String taskToken=request.getParameter("taskToken");
			
			if(StringUtils.isBlank(taskToken)){
				  resp.setRetCode(Constants.PARAMETER_CODE);
				  resp.setRetMsg("记录token不能为空");
				  return resp;
			}
			String taskId = null;
			try {
				taskId = AESUtil.AESDncode(PropertiesUtil.getProperty("aeskey"), taskToken);
			} catch (Exception e1) {
				 logger.error("添加地址解析Token异常{}",e1);
			}
	      if (file!=null) {// 判断上传的文件是否为空
	          String path=null;// 文件路径
	          String type=null;// 文件类型
	          String fileName=file.getOriginalFilename();// 文件原名称
	          logger.info("上传的文件原名称:"+fileName);
	          // 判断文件类型
	          type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
	          if (type!=null) {// 判断文件类型是否为空
	              if ("XLSX".equals(type.toUpperCase())||"XLS".equals(type.toUpperCase())) {
	            	  boolean flag = airDropTaskService.importAddress(taskId,file);
	              }else {
	            	  resp.setRetCode(Constants.PARAMETER_CODE);
	    			  resp.setRetMsg("不是我们想要的文件类型,请按要求重新上传");
	    			  return resp;
	                  
	              }
	          }else{
	        	  resp.setRetCode(Constants.PARAMETER_CODE);
				  resp.setRetMsg("文件类型为空");
				  return resp;
	          }
	      }else {
	    	  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("没有找到相对应的文件");
			  return resp;
	      }
	      resp.setRetCode(Constants.SUCCESSFUL_CODE);
		  resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			    
	      return resp;
	  }
	
}
