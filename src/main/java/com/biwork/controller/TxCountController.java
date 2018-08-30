package com.biwork.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biwork.entity.TxCount;

import com.biwork.exception.BusiException;

import com.biwork.po.RespPojo;
import com.biwork.po.TxCountPojo;

import com.biwork.service.TxCountService;

import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "获取账户交易数量")
public class TxCountController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	//获取账户交易数量
	@Autowired
	TxCountService txcService;
	
	@ResponseBody
	@RequestMapping("/eth_getTransactionCount")
	@ApiOperation(value = "获取以太坊交易数量", notes = "获取以太坊交易数量",httpMethod = "GET")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "address",value = "地址", required = true, paramType = "query")
    })
	public RespPojo getEthTxCount(HttpServletRequest request){
		
		logger.info("---获取以太坊交易数量方法---");
		String address = request.getParameter("address")==null?"":request.getParameter("address");
		TxCountPojo tx_pojo=new TxCountPojo();
		
		RespPojo resp=new RespPojo();
		
		if(StringUtils.isBlank(address)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("地址不能为空");
			  return resp;
		}
		
		TxCount txc;
		try {
			txc = txcService.getEthTxCount(address);
		}catch(BusiException e){
			 logger.error("获取以太坊交易数量异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("获取以太坊交易数量异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(txc!=null){
			tx_pojo = new TxCountPojo();
			tx_pojo.setTxCount(txc.getTxCount());
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("transactionCount", txc.getTxCount());
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping("/btc_getTransactionCount")
	@ApiOperation(value = "获取BTC交易数量", notes = "获取BTC交易数量",httpMethod = "GET")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "address",value = "地址", required = true, paramType = "query")
    })
	public RespPojo getBtcTxCount(HttpServletRequest request){
		
		logger.info("---获取BTC交易数量方法---");
		String address = request.getParameter("address")==null?"":request.getParameter("address");
		TxCountPojo tx_pojo=new TxCountPojo();
		
		RespPojo resp=new RespPojo();
		
		if(StringUtils.isBlank(address)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("地址不能为空");
			  return resp;
		}
		
		TxCount txc;
		try {
			txc = txcService.getBtcTxCount(address);
		}catch(BusiException e){
			 logger.error("获取BTC交易数量异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("获取BTC交易数量异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(txc!=null){
			tx_pojo = new TxCountPojo();
			tx_pojo.setTxCount(txc.getTxCount());
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("transactionCount", txc.getTxCount());
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}
}
