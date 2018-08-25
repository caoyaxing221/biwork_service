package com.biwork.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biwork.entity.Service;
import com.biwork.entity.User;

import com.biwork.exception.BusiException;
import com.biwork.po.RespPojo;
import com.biwork.po.UserPojo;
import com.biwork.service.LoginService;
import com.biwork.service.MyService;
import com.biwork.service.VerifyCodeService;

import com.biwork.util.Constants;
import com.biwork.util.JwtUtil;
import com.biwork.util.MD5Util;
import com.biwork.util.UidUtil;
import com.biwork.util.ValidateUtil;

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
@Api(value = "/myService", description = "我的服务")
public class MyServiceController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//登录操作
	@Autowired
	MyService myService;
	
	
	//去密码登录操作（输入手机号获取验证码直接登录）
			
	@ResponseBody
	@RequestMapping("/query")
	@ApiOperation(value = "查询我的服务", notes = "查询我的服务",httpMethod = "GET")
	
	public RespPojo query(HttpServletRequest request){
		
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		Service service=null;
		
		try {
			 service = myService.query();
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
	
	
}
