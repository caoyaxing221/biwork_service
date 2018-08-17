package com.biwork.controller;





import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.biwork.entity.User;

import com.biwork.exception.BusiException;
import com.biwork.po.RespPojo;
import com.biwork.po.UserPojo;
import com.biwork.service.LoginService;
import com.biwork.service.VerifyCodeService;

import com.biwork.util.Constants;
import com.biwork.util.JwtUtil;
import com.biwork.util.MD5Util;
import com.biwork.util.ValidateUtil;





/**
 * 
* @ClassName: LoginController 
* @Description: 登录操作相关类
* @author cyx
* @date 2018年8月17日
*
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	

	
	
	//登录操作
	@Autowired
	LoginService loginService;
	
	
	//去密码登录操作（输入手机号获取验证码直接登录）
			
	@ResponseBody
	@RequestMapping("/login")
	public RespPojo doLoginSubmit(HttpServletRequest request){
		logger.info("---进入登录方法---");
		String phone= request.getParameter("phone")==null?"":request.getParameter("phone");
//		String password = phone.substring(5, 11);
		String verifyCode=request.getParameter("verifyCode")==null?"":request.getParameter("verifyCode");
		
		
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		
		
		if(StringUtils.isBlank(phone)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("手机号不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(verifyCode)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("验证码不能为空");
			  return resp;
		}
		
		if(!ValidateUtil.isMobile(phone)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("手机号格式错误");
			  return resp;
		}
		
		try {
			boolean vflag= verifyCodeService.verifyCode(phone, verifyCode, "login");
			if(!vflag){
				resp.setRetCode(Constants.FAIL_CODE);
			    resp.setRetMsg("验证码校验失败");
			    return resp;
			}
		}
		catch (Exception e) {
			  logger.error("登录账户验证码校验异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		
		//此处验证user表中是否有这个商户注册信息，有则不添加新的，无则添加
		User account = null;
		try {
			account = loginService.loginOrRegist(phone);
			
		}catch(BusiException e){
			 logger.error("登录异常{}",e);
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("登录异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(account!=null){
			 //将用户信息存入session
			
			up=(UserPojo) request.getSession().getAttribute("User");
			
			up=new UserPojo();
			
			
			up.setUserid(account.getId().toString());
			request.getSession().setAttribute("User",up);
		
			//TODO:返回需要跳转到哪个页面
			 Map<String, Object> rtnMap = new HashMap<String, Object>();
			 String token="";
			try {
				token = JwtUtil.createToken(account.getId().longValue());
			} catch (Exception e) {
				 resp.setRetCode(Constants.SUCCESSFUL_CODE);
				 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
				 logger.error("手机号{}登录生成token失败{}",phone,e);
			}
			 rtnMap.put("token", token);
			 resp.setRetCode(Constants.SUCCESSFUL_CODE);
			 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			 resp.setData(rtnMap);
			    
			return resp;
		}
		return resp;
		
	}
	
	@ResponseBody
	@RequestMapping("/loginPW")
	public RespPojo doLoginSubmitPW(HttpServletRequest request){
		logger.info("---进入登录方法---");
		
		String phone= request.getParameter("phone");
		String password=request.getParameter("password");
		
		
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		
		RespPojo resp=new RespPojo();
		
		
		if(StringUtils.isBlank(phone)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("手机号不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(password)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("密码不能为空");
			  return resp;
		}
		if(!ValidateUtil.isMobile(phone)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("手机号格式错误");
			  return resp;
		}
		if(!ValidateUtil.isPassword(password)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("密码格式错误");
			  return resp;
		}
		User account;
		try {
			account = loginService.login(phone,MD5Util.getMD5(password) );
		}catch(BusiException e){
			 logger.error("登录异常{}",e);
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("登录异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(account!=null){
			 //将用户信息存入session
			
			up=(UserPojo) request.getSession().getAttribute("User");
			
			up=new UserPojo();
			
			
			up.setUserid(account.getId().toString());
			request.getSession().setAttribute("User",up);
			 Map<String, Object> rtnMap = new HashMap<String, Object>();
			 String token="";
			try {
				token = JwtUtil.createToken(account.getId().longValue());
			} catch (Exception e) {
				 resp.setRetCode(Constants.SUCCESSFUL_CODE);
				 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
				 logger.error("手机号{}登录生成token失败{}",phone,e);
			}
			 rtnMap.put("token", token);
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			//TODO:返回需要跳转到哪个页面
			return resp;
		}
		return resp;
		
	}
	//发送验证码到手机
	@Autowired
	VerifyCodeService verifyCodeService;
	@ResponseBody
	@RequestMapping("/getVerifyCode")
	public RespPojo doGetMessageCode(HttpServletRequest request) throws Exception{
		logger.info("----发送短信验证码----");
	 
		//logger.info("{}",json);
		String phone= request.getParameter("phone")==null?"":request.getParameter("phone");
		String type=request.getParameter("type")==null?"":request.getParameter("type");
		
		RespPojo resp=new RespPojo();
		
		
		if(StringUtils.isBlank(phone)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("手机号不能为空");
			  return resp;
		}
		if(!ValidateUtil.isMobile(phone))
		{
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("手机号格式错误");
			  return resp;
		}
		//register 注册 forgetpassword 找回密码 transfer 交易  login 登录
		if(StringUtils.isBlank(type)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("验证码类型不能为空");
			  return resp;
		}
		if(type.equals("forgetpassword")){//找回密码短信
			try {
				User account=loginService.checkAccountByPhone(phone);
			}catch(BusiException e){
				 
				  
				  resp.setRetCode(e.getCode());
				  resp.setRetMsg(e.getMessage());
				  return resp;
			}
			catch (Exception e) {
				  
				  
				  resp.setRetCode(Constants.FAIL_CODE);
				  resp.setRetMsg(Constants.FAIL_MESSAGE);
				  return resp;
			}
		}
		
		try {
			 verifyCodeService.getCode(phone, type);
		}
		catch (Exception e) {
			  logger.error("验证码获取异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
	
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		
		return resp;
		
		
	}	
	//找回密码

	@ResponseBody
	@RequestMapping("/forgetPassword")
	public RespPojo forgetPassword(HttpServletRequest request){
		logger.info("---找回密码---");
		String phone= request.getParameter("phone");
		String password=request.getParameter("password");
		String password2=request.getParameter("password2");
		String verifyCode=request.getParameter("verifyCode");
		RespPojo resp=new RespPojo();
		
		
		if(StringUtils.isBlank(phone)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("手机号不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(password)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("新密码不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(password2)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("确认密码不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(verifyCode)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("验证码不能为空");
			  return resp;
		}
		if(!ValidateUtil.isMobile(phone))
		{
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("手机号格式错误");
			  return resp;
		}
		if(!password.equals(password2)){
			resp.setRetCode(Constants.PARAMETER_CODE);
			resp.setRetMsg("两次输入密码不一致");
			return resp;
		}
		
		if(!ValidateUtil.isPassword(password)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("密码长度为6-16位");
			  return resp;
		}
		
		
		try {
			boolean vflag= verifyCodeService.verifyCode(phone, verifyCode, "forgetpassword");
			if(!vflag){
				resp.setRetCode(Constants.FAIL_CODE);
			    resp.setRetMsg("验证码校验失败");
			    return resp;
			}
		}
		catch (Exception e) {
			  logger.error("找回密码验证码校验异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		try {
			boolean forgetflag = loginService.forgetPassword(phone,MD5Util.getMD5(password) );
		}catch(BusiException e){
			 logger.error("找回密码异常{}",e);
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("找回密码异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		
		return resp;
		
	}
	//注册

	@RequestMapping("/register")
	@ResponseBody
	public RespPojo register(HttpServletRequest request){
		
		String phone= request.getParameter("phone");
		String password=request.getParameter("password");
		String verifyCode=request.getParameter("verifyCode");
		
		RespPojo resp=new RespPojo();
		logger.info("---注册操作,手机号{}|{}|{}|{}|{}---",phone,verifyCode);
		
		
		if(StringUtils.isBlank(phone)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("手机号不能为空");
			  return resp;
		}
		if(StringUtils.isBlank(password)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("密码不能为空");
			  return resp;
		}
		
		if(StringUtils.isBlank(verifyCode)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("验证码不能为空");
			  return resp;
		}
		if(!ValidateUtil.isMobile(phone))
		{
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("手机号格式错误");
			  return resp;
		}
		
		
		if(!ValidateUtil.isPassword(password)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("密码长度为6-16位");
			  return resp;
		}
		
		
		try {
			boolean vflag= verifyCodeService.verifyCode(phone, verifyCode, "register");
			if(!vflag){
				resp.setRetCode(Constants.FAIL_CODE);
			    resp.setRetMsg("验证码校验失败");
			    return resp;
			}
		}
		catch (Exception e) {
			  logger.error("注册验证码校验异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		try {
			boolean registerflag = loginService.register(phone,MD5Util.getMD5(password) );
		}catch(BusiException e){
			 logger.error("注册异常{}",e);
			  
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("注册异常{}",e);
			  
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		
		return resp;
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public RespPojo logout(HttpServletRequest request){
		logger.info("---退出---");
		UserPojo up=new UserPojo();
		up=(UserPojo) request.getSession().getAttribute("User");
		if(up!=null){
			String userid=up.getUserid();
			loginService.loginout(userid);
		}
		
		HttpSession session = request.getSession();
		
		 //清除Session  
        session.invalidate(); 
       // ModelAndView result = new ModelAndView("/login/index");
        RespPojo result = new RespPojo();
		result.setRetCode(Constants.SUCCESSFUL_CODE);
		result.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		return result;
	}
}
