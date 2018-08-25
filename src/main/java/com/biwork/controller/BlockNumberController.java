package com.biwork.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biwork.entity.BlockNumber;
import com.biwork.exception.BusiException;
import com.biwork.po.RespPojo;
import com.biwork.po.BlockNumberPojo;
import com.biwork.service.BlockNumberService;
import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "获取区块号")
public class BlockNumberController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	//获取区块号
	@Autowired
	BlockNumberService bnService;
	
	@ResponseBody
	@RequestMapping("/eth_blockNumber")
	@ApiOperation(value = "获取以太坊区块号", notes = "获取以太坊区块号",httpMethod = "GET")
	public RespPojo getEthBlockNumber(HttpServletRequest request){
		
		logger.info("---获取以太坊区块号方法---");
		BlockNumberPojo bnPojo=new BlockNumberPojo();
		
		RespPojo resp=new RespPojo();
		
		BlockNumber bn;
		try {
			bn = bnService.getEthBlockNumber();
		}catch(BusiException e){
			 logger.error("获取以太坊区块号异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("获取以太坊区块号异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(bn!=null){
			bnPojo = new BlockNumberPojo();
			bnPojo.setBlockNumber(bn.getBlockNumber());
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("BlockNumber", bn.getBlockNumber());
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping("/btc_blockNumber")
	@ApiOperation(value = "获取比特币区块号", notes = "获取比特币区块号",httpMethod = "GET")
	
	public RespPojo getBtcBlockNumber(HttpServletRequest request) {
        logger.info("---获取BTC区块号方法---");
        BlockNumberPojo bnPojo=new BlockNumberPojo();
		RespPojo resp=new RespPojo();

		BlockNumber bn;
		try {
			bn = bnService.getBtcBlockNumber();
		}catch(BusiException e){
			 logger.error("获取BTC区块号异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("获取BTC区块号异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(bn!=null){
			bnPojo = new BlockNumberPojo();
			bnPojo.setBlockNumber(bn.getBlockNumber());
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("BlockNumber", bn.getBlockNumber());
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}
}
