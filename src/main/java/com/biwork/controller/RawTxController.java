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

import com.biwork.entity.RawTx;

import com.biwork.exception.BusiException;

import com.biwork.po.RespPojo;
import com.biwork.po.RawTxPojo;

import com.biwork.service.RawTxService;

import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "发送签名后交易数据到区块链网络")
public class RawTxController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	//获取账户交易数量
	@Autowired
	RawTxService rawTxService;
	
	@ResponseBody
	@RequestMapping("/eth_sendRawTransaction")
	@ApiOperation(value = "发送签名后交易数据到以太坊区块链网络", notes = "发送签名后交易数据到以太坊区块链网络",httpMethod = "POST")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "data",value = "签名后数据", required = true, paramType = "form")
    })
	public RespPojo getEthRawTx(HttpServletRequest request){
		
		logger.info("---发送签名后交易数据到以太坊区块链网络方法---");
		String data = request.getParameter("data")==null?"":request.getParameter("data");
		RawTxPojo rawTx_pojo=new RawTxPojo();
		
		RespPojo resp=new RespPojo();
		
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
	@RequestMapping("/btc_sendRawTransaction")
	@ApiOperation(value = "发送签名后交易数据到BTC区块链网络", notes = "发送签名后交易数据到BTC区块链网络",httpMethod = "POST")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "data",value = "签名后数据", required = true, paramType = "form")
    })
	public RespPojo getBtcRawTx(HttpServletRequest request){
		
		logger.info("---发送签名后交易数据到BTC区块链网络方法---");
		String data = request.getParameter("data")==null?"":request.getParameter("data");
		RawTxPojo rawTx_pojo=new RawTxPojo();
		
		RespPojo resp=new RespPojo();
		
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
