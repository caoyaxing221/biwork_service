package com.biwork.controller;

import java.math.BigInteger;
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

import com.biwork.entity.TxFee;

import com.biwork.exception.BusiException;

import com.biwork.po.RespPojo;
import com.biwork.po.TxFeePojo;

import com.biwork.service.TxFeeService;

import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "获取以太坊交易GasPrice")
public class TxFeeController{
	private Logger logger = LoggerFactory.getLogger(getClass());

	// 获取以太坊交易GasPrice
	@Autowired
	TxFeeService txFeeService;

	@ResponseBody
	@RequestMapping("/eth_gasPrice")
	@ApiOperation(value = "获取以太坊交易GasPrice", notes = "获取以太坊交易GasPrice", httpMethod = "GET")
	public RespPojo getEthTxFee(HttpServletRequest request) {

		logger.info("---获取以太坊交易GasPrice---");
		TxFeePojo tx_pojo = new TxFeePojo();

		RespPojo resp = new RespPojo();

		TxFee txf;
		try {
			txf = txFeeService.getEthTxFee();
		} catch (BusiException e) {
			logger.error("获取以太坊交易GasPrice异常{}", e);
			resp.setRetCode(e.getCode());
			resp.setRetMsg(e.getMessage());
			return resp;
		} catch (Exception e) {
			logger.error("获取以太坊交易GasPrice异常{}", e);
			resp.setRetCode(Constants.FAIL_CODE);
			resp.setRetMsg(Constants.FAIL_MESSAGE);
			return resp;
		}
		if (txf != null) {
			tx_pojo = new TxFeePojo();
			tx_pojo.setTxFee(txf.getTxFee());
			Map<String, BigInteger> rtnMap = new HashMap<String, BigInteger>();
			BigInteger fee = new BigInteger(txf.getTxFee());  
			BigInteger two = new BigInteger("2"); 
			rtnMap.put("fastestFee", (fee.multiply(two)));
			rtnMap.put("hourFee", fee);
			rtnMap.put("halfHourFee", (fee.divide(two)));
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}
}
