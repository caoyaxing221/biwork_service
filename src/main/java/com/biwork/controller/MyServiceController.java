package com.biwork.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.biwork.entity.*;
import com.biwork.entity.Currency;
import com.biwork.po.request.CommitOrderPojo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.biwork.vo.ServiceVo;
import com.biwork.vo.TeamVo;

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
		List<ServiceVo> service=null;
		
		try {
			 service = myService.getServiceList(Integer.parseInt(up.getUserid()));
		}
		catch (Exception e) {
			  logger.error("查询服务异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		rtnMap.put("name", service.getName());
//		rtnMap.put("expireDate", service.getExpireDate());
//		rtnMap.put("maxAccount", service.getMaxAccount());
		rtnMap.put("service", service);
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
    @RequestMapping("/getOrderNo")
    @ApiOperation(value = "生成订单号", notes = "生成订单号",httpMethod = "GET")
    public RespPojo getOrderNo(HttpServletRequest request){

        UserPojo up=new UserPojo();
        up=(UserPojo) request.getSession().getAttribute("User");
        String orderNo="";
        RespPojo resp=new RespPojo();


        try {
            orderNo=IDWorker.nextID("");

        }
        catch (Exception e) {
            logger.error("生成订单号{}",e);

            resp.setRetCode(Constants.FAIL_CODE);
            resp.setRetMsg(Constants.FAIL_MESSAGE);
            return resp;
        }

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("orderNo", orderNo);
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
		//2019.06.28 去掉是否管理员身份判断，只保留了管理员登录
//		if(up.getRoleid().equals("0")){
			service=myService.getService(Integer.parseInt(up.getUserid()));
			  if(service.getExpireDate().compareTo(new Date())<0){
				  rtnMap.put("extraAuth", "false");
			  }else{
				  rtnMap.put("extraAuth", "true");
			  }
//		}
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

	@ResponseBody
    @RequestMapping("/getReceiveCoinAddress")
    @ApiOperation(value = "获取收币地址", notes = "获取收币地址",httpMethod = "GET")
    public RespPojo getReceiveCoinAddress(HttpServletRequest request){



        RespPojo resp=new RespPojo();

        String address="";
        try {

            address=myService.getConfiguration("receiveCoinAddress");
        }
        catch(BusiException e){

            resp.setRetCode(e.getCode());
            resp.setRetMsg(e.getMessage());
            return resp;
        }
        catch (Exception e) {
            logger.error("查询收币地址异常{}",e);

            resp.setRetCode(Constants.FAIL_CODE);
            resp.setRetMsg(Constants.FAIL_MESSAGE);
            return resp;
        }


        Map<String, Object> rtnMap = new HashMap<String, Object>();


        rtnMap.put("address", address);

        resp.setRetCode(Constants.SUCCESSFUL_CODE);
        resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
        resp.setData(rtnMap);

        return resp;

    }
    @ResponseBody
    @RequestMapping("/getPurchaseType")
    @ApiOperation(value = "获取付费方式", notes = "获取付费方式",httpMethod = "GET")
    public RespPojo getPurchaseType(HttpServletRequest request){



        RespPojo resp=new RespPojo();

        List<PaymentType> purchaseTypeList;
        try {

            purchaseTypeList=myService.getPurchaseType();
        }
        catch(BusiException e){

            resp.setRetCode(e.getCode());
            resp.setRetMsg(e.getMessage());
            return resp;
        }
        catch (Exception e) {
            logger.error("查询付费方式异常{}",e);

            resp.setRetCode(Constants.FAIL_CODE);
            resp.setRetMsg(Constants.FAIL_MESSAGE);
            return resp;
        }


        Map<String, Object> rtnMap = new HashMap<String, Object>();


        rtnMap.put("purchaseTypeList", purchaseTypeList);

        resp.setRetCode(Constants.SUCCESSFUL_CODE);
        resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
        resp.setData(rtnMap);

        return resp;

    }
    @ResponseBody
    @RequestMapping(value="/queryOrderList", method= RequestMethod.GET, produces="application/json;charset=utf-8;")
    @ApiOperation(value = "查询付费列表", notes = "查询付费列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页条数", required = true, paramType = "query")
    })
    public RespPojo queryOrderList(HttpServletRequest request){
        UserPojo up=new UserPojo();
        up=(UserPojo) request.getSession().getAttribute("User");
        List<PaymentOrder> orderList=null;
        RespPojo resp=new RespPojo();
        String pageNo=request.getParameter("pageNo");
        String pageSize=request.getParameter("pageSize");


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
            orderList=myService.queryOrderList(up.getUserid(),offset.toString(),pageSize);

            totalCount=myService.queryOrderCount(up.getUserid());
        }
        catch(BusiException e){

            resp.setRetCode(e.getCode());
            resp.setRetMsg(e.getMessage());
            return resp;
        }
        catch (Exception e) {
            logger.error("查询付费列表异常{}",e);

            resp.setRetCode(Constants.FAIL_CODE);
            resp.setRetMsg(Constants.FAIL_MESSAGE);
            return resp;
        }



        Map<String, Object> rtnMap = new HashMap<String, Object>();

        rtnMap.put("orderList", orderList);
        rtnMap.put("totalCount", totalCount);
        resp.setRetCode(Constants.SUCCESSFUL_CODE);
        resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
        resp.setData(rtnMap);

        return resp;


    }
    @ResponseBody
    @RequestMapping(value="/createOrder", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
    @ApiOperation(value = "购买服务", notes = "购买服务",httpMethod = "POST")
//
    public RespPojo commitProcess(HttpServletRequest request,@RequestBody
    @ApiParam(name="付费",value="传入json格式",required=true) @Validated CommitOrderPojo req, BindingResult result){
        RespPojo resp=new RespPojo();
	    if(result.hasErrors()) {
            resp.setRetCode(Constants.PARAMETER_CODE);
            resp.setRetMsg(result.getFieldError().getDefaultMessage());
            return resp;
        }
        UserPojo up=new UserPojo();
        up=(UserPojo) request.getSession().getAttribute("User");


        try {
            myService.createOrder(up.getUserid(),req);
        }
        catch(BusiException e){

            resp.setRetCode(e.getCode());
            resp.setRetMsg(e.getMessage());
            return resp;
        }
        catch (Exception e) {
            logger.error("创建订单异常{}",e);

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
