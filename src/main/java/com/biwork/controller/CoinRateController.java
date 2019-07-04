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

import com.alibaba.fastjson.JSONObject;
import com.biwork.entity.CoinRate;
import com.biwork.entity.TxGasLimit;
import com.biwork.exception.BusiException;
import com.biwork.po.RespPojo;
import com.biwork.service.CoinRateService;
import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "获取货币的汇率接口")
public class CoinRateController {
private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	CoinRateService coinRateService;
	
	@ResponseBody
	@RequestMapping(value = "/coinRate", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "获取数字货币的汇率", notes = "获取数字货币的汇率",httpMethod = "POST")
	public RespPojo getCoinRate(HttpServletRequest request, @RequestBody 
			@ApiParam(name="获取数字货币的汇率",value="传入json格式",required=true) com.biwork.po.request.CoinRatePojo coinRatePojo) {
		logger.info("获取数字货币的汇率");
		List<String> coinRateIdList = new ArrayList<>();
		String coinRateMark = coinRatePojo.getCoinRateMark();
		coinRateIdList = coinRatePojo.getCoinRateIdList();
		System.out.println("coinRateMark" + coinRateMark);
		System.out.println("coinRateIdList = " + coinRateIdList);
		System.out.println("coinRateIdList =" + coinRateIdList.size());
		RespPojo resp=new RespPojo();
		if(StringUtils.isBlank(coinRateMark) || coinRateIdList.size() == 0){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("获取数字货币的汇率参数不能为空");
			  return resp;
		}
		Map<String, Object>coinRate = new HashMap();
		try {
			coinRate = coinRateService.getAllCoinRate(coinRateMark, coinRateIdList);
		} catch (BusiException e) {
			logger.error("获取数字货币的汇率业务异常", e);
			resp.setRetCode(e.getCode());
			resp.setRetMsg(e.getMessage());
			return resp;
		} catch (Exception e) {
			logger.error("获取数字货币的汇率抛出异常", e);
			resp.setRetCode(Constants.FAIL_CODE);
			resp.setRetMsg(Constants.FAIL_MESSAGE);
			return resp;
		}
		System.out.println("coinRate = " + coinRate);
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		resp.setData(coinRate);
		return resp;
	}
}
