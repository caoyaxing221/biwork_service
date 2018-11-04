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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biwork.entity.BatchRawTx;
import com.biwork.entity.RawTx;

import com.biwork.exception.BusiException;

import com.biwork.po.RespPojo;
import com.biwork.po.request.BatchRawTxFlowPojo;
import com.biwork.po.request.RawTxFlowPojo;
import com.biwork.po.RawTxPojo;

import com.biwork.service.RawTxService;

import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "发送签名后交易数据到区块链网络")
public class RawTxController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	//发送签名后交易到区块链网络
	@Autowired
	RawTxService rawTxService;
	
	@ResponseBody
	@RequestMapping(value = "/eth_sendBatchRawTransaction", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "批量发送签名后交易数据到以太坊区块链网络", notes = "批量发送签名后交易数据到以太坊区块链网络",httpMethod = "POST")
	public RespPojo getBatchEthRawTx(HttpServletRequest request, @RequestBody 
			@ApiParam(name="发送签名后交易对象",value="传入json格式",required=true) BatchRawTxFlowPojo batchRwatxFlowPojo){
		
		logger.info("---批量发送签名后交易数据到以太坊区块链网络---");
		RawTxPojo rawTx_pojo=new RawTxPojo();
		RespPojo resp=new RespPojo();
		String signCoin = batchRwatxFlowPojo.getSignCoin();
		List<String> arrList = new ArrayList<>();
		arrList = batchRwatxFlowPojo.getSignDataArr();
		System.out.println("signCoin = " + signCoin);
        System.out.println("sign = " + arrList.get(0));
		
		if(StringUtils.isBlank(signCoin) || arrList.size() == 0){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("批量签名后数据不能为空");
			  return resp;
		}
	
		RawTx rawTx;
		List<String> hashArray = new ArrayList<>();
		try {
			for(int i = 0; i < arrList.size(); i++) {
				rawTx = rawTxService.getEthRawTx(arrList.get(i));
				hashArray.add(rawTx.getRawTx());
			}
		}catch(BusiException e){
			 logger.error("批量发送签名后交易数据到以太坊区块链网络异常-业务异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("批量发送签名后交易数据到以太坊区块链网络异常-普通异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(hashArray.isEmpty()){
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(hashArray);
			return resp;
		} 
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/eth_sendRawTransaction", method=RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ApiOperation(value = "发送签名后交易数据到以太坊区块链网络", notes = "发送签名后交易数据到以太坊区块链网络",httpMethod = "POST")
	public RespPojo getEthRawTx(HttpServletRequest request, @RequestBody 
			@ApiParam(name="发送签名后交易对象",value="传入json格式",required=true) RawTxFlowPojo rwatxFlowPojo){
		
		logger.info("---发送签名后交易数据到以太坊区块链网络方法---");
		RawTxPojo rawTx_pojo=new RawTxPojo();
		RespPojo resp=new RespPojo();
		String data = rwatxFlowPojo.getData();
		System.out.println("data = " + data);

		if(StringUtils.isBlank(data)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("签名后数据不能为空");
			  return resp;
		}
		
		RawTx rawTx;
		try {
			rawTx = rawTxService.getEthRawTx(data);
		}catch(BusiException e){
			 logger.error("发送签名后交易数据到以太坊区块链网络异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("发送签名后交易数据到以太坊区块链网络异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(rawTx!=null){
			rawTx_pojo = new RawTxPojo();
			rawTx_pojo.setRawTx(rawTx.getRawTx());
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("transactionHash", rawTx.getRawTx());
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}
	
	
	@ResponseBody 
	@RequestMapping(value="/btc_sendRawTransaction", method=RequestMethod.POST, produces="application/json;charset=utf-8;")	@ApiOperation(value = "发送签名后交易数据到BTC区块链网络", notes = "发送签名后交易数据到BTC区块链网络",httpMethod = "POST")
	public RespPojo getBtcRawTx(HttpServletRequest request,@RequestBody
			@ApiParam(name="流程对象",value="传入json格式",required=true) RawTxFlowPojo rwatxFlowPojo){
		
		logger.info("---发送签名后交易数据到BTC区块链网络方法---");
		RawTxPojo rawTx_pojo=new RawTxPojo();
		RespPojo resp=new RespPojo();
		
		String data = rwatxFlowPojo.getData();
		System.out.println("data = " + data);
		
		if(StringUtils.isBlank(data)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("签名后数据不能为空");
			  return resp;
		}
		
		RawTx rawTx;
		try {
			rawTx = rawTxService.getBtcRawTx(data);
		}catch(BusiException e){
			 logger.error("发送签名后交易数据到BTC区块链网络异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("发送签名后交易数据到BTC区块链网络异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(rawTx!=null){
			rawTx_pojo = new RawTxPojo();
			rawTx_pojo.setRawTx(rawTx.getRawTx());
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("transactionHash", rawTx.getRawTx());
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}
}
