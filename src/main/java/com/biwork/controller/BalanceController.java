package com.biwork.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biwork.entity.Balance;
import com.biwork.entity.User;
import com.biwork.exception.BusiException;
import com.biwork.po.BalancePojo;
import com.biwork.po.RespPojo;
import com.biwork.po.UserPojo;
import com.biwork.service.BalanceService;
import com.biwork.service.LoginService;
import com.biwork.util.Constants;
import com.biwork.util.JwtUtil;
import com.biwork.util.MD5Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/balance")
@Api(value = "/balance", description = "获取账户余额")
public class BalanceController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	//获取账户余额操作
	@Autowired
	BalanceService balanceService;
	
	@ResponseBody
	@RequestMapping("/getbalance")
	@ApiOperation(value = "获取以太坊余额", notes = "获取以太坊余额",httpMethod = "POST")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "address",value = "地址", required = true, paramType = "form")
    })
	
	public RespPojo getEthBalance(HttpServletRequest request){
		
		logger.info("---获取以太坊余额方法---");
		String address = request.getParameter("address")==null?"":request.getParameter("address");
		BalancePojo bp=new BalancePojo();
		bp=(BalancePojo) request.getSession().getAttribute("Account");
		
		RespPojo resp=new RespPojo();
		
		if(StringUtils.isBlank(address)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("地址不能为空");
			  return resp;
		}
		
		Balance balance;
		try {
			balance = balanceService.getEthBalance();
		}catch(BusiException e){
			 logger.error("获取以太坊余额异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("获取以太坊余额异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(balance!=null){
			 //将用户信息存入session
			bp = (BalancePojo) request.getSession().getAttribute("User");
			bp = new BalancePojo();
			bp.setBalance("10000");
			request.getSession().setAttribute("Balance", bp);
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			String balance1 = "";
			try {
				balance1 = "100000";
			} catch (Exception e) {
				 resp.setRetCode(Constants.SUCCESSFUL_CODE);
				 resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
				 logger.error("获取账户余额失败", balance1, e);
			}
			rtnMap.put("balance", balance1);
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}
}
