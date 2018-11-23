package com.biwork.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biwork.entity.AddCoin;
import com.biwork.entity.TxFee;

import com.biwork.exception.BusiException;

import com.biwork.po.RespPojo;
import com.biwork.po.TxFeePojo;

import com.biwork.service.TxFeeService;

import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "获取比特币，以太坊和ERC20代币的手续费")
public class TxFeeController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	TxFeeService txFeeService;
	@ResponseBody
	@RequestMapping(value = "/est_gasLimit", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "获取估计值的gasLimit", notes = "获取估计值的gasLimit",httpMethod = "POST")
	public RespPojo queryCoinInfo(HttpServletRequest request, @RequestBody 
			@ApiParam(name="获取估计值的gasLimit",value="传入json格式",required=true) com.biwork.po.request.TxFeeGasLimitPojo txFeeGasLimitPojo){
		logger.info("---获取估计值的gasLimit---");
		RespPojo resp=new RespPojo();
		String formAddress = txFeeGasLimitPojo.getFormAddress().replace(" ", "");
		logger.info("formAddress = " + formAddress);
		String toAddress = txFeeGasLimitPojo.getToAddress().replace(" ", "");
		logger.info("toAddress = " + toAddress);
		BigInteger value = txFeeGasLimitPojo.getValue();
		logger.info("value = " + value);
		BigInteger nonce = txFeeGasLimitPojo.getNonce();
		logger.info("nonce = " + nonce);
		BigInteger gasPrice = txFeeGasLimitPojo.getGasPrice();
		logger.info("gasPrice = " + gasPrice);
		String data = txFeeGasLimitPojo.getData().replace(" ", "");
		logger.info("data = " + data);
		BigInteger gas = txFeeGasLimitPojo.getGas();
		logger.info("gas = " + gas);
		BigInteger gasLimit; 
		try {
			gasLimit = txFeeService.getGasLimit(formAddress, toAddress, value, nonce, gasPrice, data, gas);
		}catch(BusiException e){
			Object obj = new Object();
			JSONParser parser = new JSONParser();
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(120000);
			return resp;
		}
		catch (Exception e) {
			Object obj = new Object();
			JSONParser parser = new JSONParser();
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(120000);
			return resp;
		}
		if(gasLimit != null){
			Object obj = new Object();
			JSONParser parser = new JSONParser();
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(gasLimit);
			return resp;
		}
		return resp;
	}
	
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
			rtnMap.put("halfHourFee", fee);
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping("/btc_txFee")
	@ApiOperation(value = "获取比特币交易手续费btc_txFee", notes = "获取比特币交易手续费btc_txFee", httpMethod = "GET")
	public RespPojo getBitcoinTxFee(HttpServletRequest request) {
		logger.info("---获取比特币交易手续费---");
		RespPojo resp = new RespPojo();
		String bitTxf = "";
		try {
			bitTxf = txFeeService.getBitCoinFee();
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
		if (bitTxf != null) {
			Object obj = new Object();
			JSONParser parser = new JSONParser();
			try {
				obj = parser.parse(bitTxf);
			} catch (ParseException e) {
				throw new BusiException(Integer.toString(e.getErrorType()), e.getMessage());
			}
			JSONObject jsonObject = (JSONObject) obj;
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(jsonObject);
			return resp;
		}
		return resp;
	}
}
