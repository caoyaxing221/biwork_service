package com.biwork.controller;

import java.util.ArrayList;
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

import com.biwork.entity.AddCoin;
import com.biwork.entity.RawTx;
import com.biwork.exception.BusiException;
import com.biwork.po.RawTxPojo;
import com.biwork.po.RespPojo;
import com.biwork.po.request.AddCoinPojo;
import com.biwork.service.AddCoinService;
import com.biwork.util.Constants;
import com.biwork.vo.CoinInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "添加币种接口控制器")
public class AddCoinController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	//发送签名后交易到区块链网络
	@Autowired
	AddCoinService addCoinService;
	
	@ResponseBody
	@RequestMapping(value = "/coin_getLikeQueryCoin", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "模糊查询币种", notes = "根据输入模糊查询币种",httpMethod = "POST")
	public RespPojo getLikeQueryCoin(HttpServletRequest request, @RequestBody 
			@ApiParam(name="模糊查询币种",value="传入json格式",required=true) com.biwork.po.request.AddCoinPojo addCoinPojo){
		logger.info("---根据输入模糊查询币种---");
		com.biwork.po.AddCoinPojo addCoinPo =new com.biwork.po.AddCoinPojo();
		RespPojo resp=new RespPojo();
		String queryMark = addCoinPojo.getSearchMark();
		String queryName = addCoinPojo.getLikeQueryName();
		System.out.println("queryName = " + queryName);
		if(StringUtils.isBlank(queryName)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("匹配的字符串不能为空");
			  return resp;
		}
		String coinInfo;  
		try {
			coinInfo = addCoinService.getLikeQueryCoin(queryName, queryMark);
		}catch(BusiException e){
			 logger.error("模糊查询币种异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("模糊查询币种异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(coinInfo != null){
			System.out.println("coinInfo = " + coinInfo);
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("data", coinInfo);
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(coinInfo);
			return resp;
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/coin_queryCoinInfo", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "模糊查询币种", notes = "根据输入模糊查询币种",httpMethod = "POST")
	public RespPojo queryCoinInfo(HttpServletRequest request, @RequestBody 
			@ApiParam(name="模糊查询币种",value="传入json格式",required=true) com.biwork.po.request.AddCoinPojo addCoinPojo){
		logger.info("---根据输入模糊查询币种---");
		com.biwork.po.AddCoinPojo addCoinPo =new com.biwork.po.AddCoinPojo();
		RespPojo resp=new RespPojo();
		String queryMark = addCoinPojo.getSearchMark();
		String queryName = addCoinPojo.getLikeQueryName();
		String contractAddress = addCoinPojo.getContractAddress();
		if(StringUtils.isBlank(queryMark) || StringUtils.isBlank(queryName) || StringUtils.isBlank(contractAddress)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("参数为空,请重检查并输入");
			  return resp;
		}
		List<AddCoin> coinInfo;  
		try {
			coinInfo = addCoinService.queryCoinInfo(queryMark, queryName, contractAddress);
		}catch(BusiException e){
			 logger.error("模糊查询币种异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("模糊查询币种异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(coinInfo != null){
			System.out.println("coinInfo = " + coinInfo);
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("data", coinInfo);
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(coinInfo);
			return resp;
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/coin_queryCoinAll", method=RequestMethod.GET, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "查询全部币种", notes = "查询全部币种",httpMethod = "GET")
	public RespPojo queryCoinAll(HttpServletRequest request ){
		RespPojo resp=new RespPojo();
		List<CoinInfoVo> coinInfo;  
		try {
			coinInfo = addCoinService.queryCoinInfoAll();
		}catch(BusiException e){
			 logger.error("查询币种异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("查询币种异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(coinInfo != null){
			System.out.println("coinInfo = " + coinInfo);
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("data", coinInfo);
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(coinInfo);
			return resp;
		}
		return resp;
	}
}
