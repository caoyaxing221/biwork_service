package com.biwork.controller;

import java.math.BigDecimal;
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
import com.biwork.exception.BusiException;
import com.biwork.po.BalancePojo;
import com.biwork.po.RespPojo;
import com.biwork.service.BalanceService;
import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "获取账户余额")
public class BalanceController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	//获取账户余额操作
	@Autowired
	BalanceService balanceService;
	
	@ResponseBody
	@RequestMapping("/eth_getBalance")
	@ApiOperation(value = "获取以太坊余额", notes = "获取以太坊余额",httpMethod = "POST")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "address",value = "地址", required = true, paramType = "form")
    })
	public RespPojo getEthBalance(HttpServletRequest request){
		
		logger.info("---获取以太坊余额方法---");
		String address = request.getParameter("address")==null?"":request.getParameter("address");
		BalancePojo bp=new BalancePojo();
		
		RespPojo resp=new RespPojo();
		
		if(StringUtils.isBlank(address)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("地址不能为空");
			  return resp;
		}
		
		Balance balance;
		try {
			balance = balanceService.getEthBalance(address);
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
			bp = new BalancePojo();
			bp.setBalance(balance.getBalance());
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("balance", balance.getBalance());
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping("/erc20_getBalance")
	@ApiOperation(value = "获取ERC20余额", notes = "获取ERC20余额",httpMethod = "POST")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "address", value = "账户地址", required = true, paramType = "form"),
        @ApiImplicitParam(name = "contract", value = "合约地址", required = true, paramType = "form")
	})
	
	public RespPojo getErc20Balance(HttpServletRequest request) {
		logger.info("---获取ERC20余额方法---");
		RespPojo resp=new RespPojo();

		String address = request.getParameter("address")==null?"":request.getParameter("address");
		if (StringUtils.isBlank(address)) {
			resp.setRetCode(Constants.PARAMETER_CODE);
			resp.setRetMsg("账户地址不能为空");
			return resp;
		}

		String contract = request.getParameter("contract")==null?"":request.getParameter("contract");
		if (StringUtils.isBlank(contract)) {
			resp.setRetCode(Constants.PARAMETER_CODE);
			resp.setRetMsg("合约地址不能为空");
			return resp;
		}
		
		Balance balance;
		try {
			balance = balanceService.getErc20Balance(address, contract);
		} catch (BusiException e) {
			  logger.error("获取ERC20余额异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		} catch (Exception e) {
			  logger.error("获取ERC20余额异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}

		if(balance!=null){
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("balance", balance.getBalance());
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping("/btc_getBalance")
	@ApiOperation(value = "获取BTC余额", notes = "获取BTC余额",httpMethod = "GET")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "address", value = "账户地址", required = true, paramType = "query")
	})
	
	public RespPojo getBtcBalance(HttpServletRequest request) {
		logger.info("---获取BTC余额方法---");
		RespPojo resp=new RespPojo();

		String address = request.getParameter("address")==null?"":request.getParameter("address");
		if (StringUtils.isBlank(address)) {
			resp.setRetCode(Constants.PARAMETER_CODE);
			resp.setRetMsg("账户地址不能为空");
			return resp;
		}

		Balance balance;
		try {
			balance = balanceService.getBtcBalance(address);
		} catch (BusiException e) {
			  logger.error("获取BTC余额异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		} catch (Exception e) {
			  logger.error("获取BTC余额异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}

		if(balance!=null){
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("address", address);
			rtnMap.put("balance", balance.getBalance());
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}

}
