package com.biwork.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.biwork.entity.CoinRate;
import com.biwork.entity.TxGasLimit;
import com.biwork.exception.BusiException;
import com.biwork.po.RespPojo;
import com.biwork.service.CoinRateService;
import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "获取货币的汇率接口")
public class CoinRateController {
private Logger logger = LoggerFactory.getLogger(getClass());
	
	// 获取以太坊交易GasPrice
	@Autowired
	CoinRateService coinRateService;
	
	@ResponseBody
	@RequestMapping("/coinRate")
	@ApiOperation(value = "获取数字货币的汇率", notes = "获取数字货币的汇率", httpMethod = "GET")
	public RespPojo getEthTxFee(HttpServletRequest request) {
		logger.info("获取数字货币的汇率");
		RespPojo resp=new RespPojo();
		Map<String, Object>coinRate = new HashMap();
		try {
			coinRate = coinRateService.getAllCoinRate();
		} catch (BusiException e) {
			logger.error("获取数字货币的汇率", e);
			resp.setRetCode(e.getCode());
			resp.setRetMsg(e.getMessage());
			return resp;
		} catch (Exception e) {
			logger.error("获取数字货币的汇率", e);
			resp.setRetCode(Constants.FAIL_CODE);
			resp.setRetMsg(Constants.FAIL_MESSAGE);
			return resp;
		}
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		resp.setData(coinRate);
		return resp;
	}
}
