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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biwork.entity.TxLog;

import com.biwork.exception.BusiException;

import com.biwork.po.RespPojo;
import com.biwork.po.TxLogPojo;

import com.biwork.service.TxLogService;

import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.alibaba.fastjson.JSON; 

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "获取交易记录")
public class TxLogController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    public static Object GetResultJsonObject(String json) throws BusiException {
    	Object obj = new Object();
        JSONParser parser = new JSONParser();
        try {
            obj = parser.parse(json);
            JSONObject jsonObject = (JSONObject) obj;
            obj = jsonObject.get("result");
        } catch (ParseException e) {
            throw new BusiException(Integer.toString(e.getErrorType()), e.getMessage());
        }

        return obj;
    }

	//获取账户交易记录
	@Autowired
	TxLogService txLogService;
	
	@ResponseBody
	@RequestMapping("/eth_transaction_log")
	@ApiOperation(value = "获取以太坊账户交易记录", notes = "获取以太坊账户交易记录",httpMethod = "GET")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "address",value = "地址", required = true, paramType = "query"),
        @ApiImplicitParam(name = "page",value = "页码", required = false, paramType = "query"),
        @ApiImplicitParam(name = "offset",value = "偏移", required = false, paramType = "query"),
        @ApiImplicitParam(name = "sort",value = "排序方法", required = false, paramType = "query")
    })
	public RespPojo getEthTxLog(HttpServletRequest request){
        logger.info("---获取以太坊账户交易记录方法---");
        
        RespPojo resp=new RespPojo();
        
		String address = request.getParameter("address")==null?"":request.getParameter("address");
		if (StringUtils.isBlank(address)) {
            resp.setRetCode(Constants.PARAMETER_CODE);
            resp.setRetMsg("address不能为空");
            return resp;
        }
        
        TxLogPojo txLog_pojo = new TxLogPojo();
        TxLog txLog;
        Object txLogsObj = null;
		try {
            txLog = txLogService.getEthTxLog("https://api.etherscan.io/api?module=account&action=txlist&" + request.getQueryString());
            txLogsObj = GetResultJsonObject(txLog.getTxLog());
		}catch(BusiException e){
			 logger.error("获取以太坊账户交易记录异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("获取以太坊账户交易记录异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if (txLog != null) {
			txLog_pojo = new TxLogPojo();
			txLog_pojo.setTxLog(txLog.getTxLog());
			Map<String, Object> rtnMap = new HashMap<String, Object>();
            rtnMap.put("txlogs", txLogsObj);
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping("/erc20_transaction_log")
	@ApiOperation(value = "获取ERC20账户交易记录", notes = "获取ERC20账户交易记录",httpMethod = "GET")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "address",value = "地址", required = true, paramType = "query"),
        @ApiImplicitParam(name = "contractaddress",value = "合约地址", required = true, paramType = "query"),
        @ApiImplicitParam(name = "page",value = "页码", required = false, paramType = "query"),
        @ApiImplicitParam(name = "offset",value = "偏移", required = false, paramType = "query"),
        @ApiImplicitParam(name = "sort",value = "排序方法", required = false, paramType = "query")
    })
	public RespPojo getErc20TxLog(HttpServletRequest request){
        logger.info("---获取ERC20账户交易记录方法---");
        
        RespPojo resp=new RespPojo();
        
		String address = request.getParameter("address")==null?"":request.getParameter("address");
		if (StringUtils.isBlank(address)) {
            resp.setRetCode(Constants.PARAMETER_CODE);
            resp.setRetMsg("address不能为空");
            return resp;
        }

        String contractaddress = request.getParameter("contractaddress")==null?"":request.getParameter("contractaddress");
		if (StringUtils.isBlank(contractaddress)) {
            resp.setRetCode(Constants.PARAMETER_CODE);
            resp.setRetMsg("contractaddress不能为空");
            return resp;
        }
        
        TxLogPojo txLog_pojo = new TxLogPojo();
        TxLog txLog;
        Object txLogsObj = null;
        
		try {
            txLog = txLogService.getErc20TxLog("https://api.etherscan.io/api?module=account&action=tokentx&" + request.getQueryString());
            txLogsObj = GetResultJsonObject(txLog.getTxLog());
		}catch(BusiException e){
			 logger.error("获取ERC20账户交易记录异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("获取ERC20账户交易记录异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if (txLog != null) {
			txLog_pojo = new TxLogPojo();
			txLog_pojo.setTxLog(txLog.getTxLog());
			Map<String, Object> rtnMap = new HashMap<String, Object>();
            rtnMap.put("txlogs", txLogsObj);
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
    }
    
    @ResponseBody
	@RequestMapping("/btc_transaction_log")
	@ApiOperation(value = "获取BTC账户交易记录", notes = "获取BTC账户交易记录",httpMethod = "GET")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "address",value = "地址", required = true, paramType = "query"),
        @ApiImplicitParam(name = "limit",value = "限制", required = false, paramType = "query"),
        @ApiImplicitParam(name = "offset",value = "偏移", required = false, paramType = "query"),
    })
	public RespPojo getBtcTxLog(HttpServletRequest request){
        logger.info("---获取BTC账户交易记录方法---");
        
        RespPojo resp=new RespPojo();
        
		String address = request.getParameter("address")==null?"":request.getParameter("address");
		if (StringUtils.isBlank(address)) {
            resp.setRetCode(Constants.PARAMETER_CODE);
            resp.setRetMsg("address不能为空");
            return resp;
        }

        String limit = request.getParameter("limit")==null?"":request.getParameter("limit");
		String offset = request.getParameter("offset")==null?"":request.getParameter("offset");
		
		if (!StringUtils.isBlank(limit)) {
			limit = "&limit=" + limit;
		}
		
		if (!StringUtils.isBlank(offset)) {
			offset = "&offset=" + offset;
		}
	
        TxLogPojo txLog_pojo = new TxLogPojo();
		TxLog txLog;
		try {
            //https://blockchain.info/rawaddr/1AJbsFZ64EpEfS5UAjAfcUG8pH8Jn3rn1F?&limit=50&offset=100
			String query = "";
			if (limit != "" || offset != "") {
				query = "https://blockchain.info/rawaddr/" + address + "?" + limit + offset;
			} else {
				query = "https://blockchain.info/rawaddr/" + address;
			}
		
			txLog = txLogService.getBtcTxLog(query);
		}catch(BusiException e){
			 logger.error("获取BTC账户交易记录异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("获取BTC账户交易记录异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if (txLog != null) {
			txLog_pojo = new TxLogPojo();
			txLog_pojo.setTxLog(txLog.getTxLog());
			Map<String, Object> rtnMap = new HashMap<String, Object>();
            List<Map> jsonObjects = new ArrayList<Map>();
			String s = txLog.getTxLog();
			com.alibaba.fastjson.JSONArray txLogsArr = JSON.parseArray(s);
			int formAmount = 0, toAmount = 0;
            for(int i = 0; i < txLogsArr.size(); i++){
            	//交易Hash
                Map<String, Object> txLogMap = new HashMap<String, Object>();
                txLogMap.put("tansHash", txLogsArr.getJSONObject(i).get("hash"));
    			
                //区块高度
                txLogMap.put("blockHeight", txLogsArr.getJSONObject(i).get("block_height"));
                
                //转账时间
                txLogMap.put("transTime", txLogsArr.getJSONObject(i).get("time"));
                
                //交易标识
                txLogMap.put("transMark", txLogsArr.getJSONObject(i).get("tx_index"));
               
                //转账状态
                txLogMap.put("transtatus", 1);
                
                //转出地址
                String inputStr = txLogsArr.getJSONObject(i).get("inputs").toString();
                com.alibaba.fastjson.JSONArray inputsArr = JSON.parseArray(inputStr);
                List<Map> AddressObjects = new ArrayList<Map>();
                
                for(int j = 0; j < inputsArr.size(); j++) {
                    Map<String, Object> AddressMap = new HashMap<String, Object>();
                	String prevOut = inputsArr.getJSONObject(j).get("prev_out").toString();
                	com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(prevOut);
                	String fromAddress = (String) jsonObject.get("addr");
                	formAmount = (int) jsonObject.get("value");
                	AddressMap.put("address", fromAddress);
                	AddressMap.put("tansAmount", formAmount * 1e-8);
                	AddressObjects.add(AddressMap);
                }
                txLogMap.put("fromData", AddressObjects);
                jsonObjects.add(txLogMap);
                
                //转入地址
                List<Map> toAddressObjects = new ArrayList<Map>();
                
                String outStr = txLogsArr.getJSONObject(i).get("out").toString();
                com.alibaba.fastjson.JSONArray outArr = JSON.parseArray(outStr);
                
                for(int k = 0; k < outArr.size(); k++) {
                    Map<String, Object> toAddressMap = new HashMap<String, Object>();
                	String Out = outArr.getJSONObject(k).toString();
                	com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(Out);
                	String toAddress = (String) jsonObject.get("addr");
                	toAmount = (int) jsonObject.get("value");
                	toAddressMap.put("address", toAddress);
                	toAddressMap.put("tansAmount", toAmount * 1e-8);
                	toAddressObjects.add(toAddressMap);
                }
                
                txLogMap.put("toData", toAddressObjects);
                jsonObjects.add(txLogMap);
            
                //===========转账金额度和手续费====================
                int totalAmount = toAmount;
                int sendFee = formAmount - toAmount;
                txLogMap.put("tansAmount", totalAmount * 1e-8);
                txLogMap.put("tansFee", sendFee * 1e-8);
                
                //转账类型
                int ret = (int) txLogsArr.getJSONObject(i).get("result");
                if(ret < 0) {
                	txLogMap.put("transType", 1);
                }
                txLogMap.put("transType", 0);
            }
            
			rtnMap.put("txLogs", jsonObjects);
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}
}
