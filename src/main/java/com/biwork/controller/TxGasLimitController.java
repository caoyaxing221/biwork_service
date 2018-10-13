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

import com.biwork.entity.TxGasLimit;
import com.biwork.exception.BusiException;
import com.biwork.po.RespPojo;
import com.biwork.service.TxGasLimitService;
import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "获取gasLimit接口")
public class TxGasLimitController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	// 获取以太坊交易GasPrice
	@Autowired
	TxGasLimitService txGasLimitService;
	
	@ResponseBody
	@RequestMapping("/gasLimit")
	@ApiOperation(value = "获取gasLimit", notes = "获取gasLimit", httpMethod = "GET")
	public RespPojo getEthTxFee(HttpServletRequest request) {
		logger.info("进入获取gasLimit控制器");
		RespPojo resp=new RespPojo();
		List<TxGasLimit> txGasLimit = null;
		try {
			txGasLimit = txGasLimitService.getGasLimit();
		} catch (BusiException e) {
			logger.error("获取gasLimit异常{}", e);
			resp.setRetCode(e.getCode());
			resp.setRetMsg(e.getMessage());
			return resp;
		} catch (Exception e) {
			logger.error("获取gasLimit异常{}", e);
			resp.setRetCode(Constants.FAIL_CODE);
			resp.setRetMsg(Constants.FAIL_MESSAGE);
			return resp;
		}
		System.out.println("txGasLimit = " + txGasLimit);
		if (txGasLimit != null) {
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("coinInfo",txGasLimit);
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}
}

