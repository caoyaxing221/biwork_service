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

import com.biwork.entity.UTXO;

import com.biwork.exception.BusiException;

import com.biwork.po.RespPojo;
import com.biwork.po.UTXOPojo;

import com.biwork.service.UTXOService;

import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "获取BTC UTXO")
public class UTXOController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    public static Object GetResultJsonObject(String json) throws BusiException {
    	Object obj = new Object();
        JSONParser parser = new JSONParser();
        try {
            obj = parser.parse(json);
            JSONObject jsonObject = (JSONObject) obj;
            obj = jsonObject.get("unspent_outputs");
        } catch (ParseException e) {
            throw new BusiException(Integer.toString(e.getErrorType()), e.getMessage());
        }

        return obj;
    }

	//获取BTC UTXO
	@Autowired
	UTXOService utxoService;
	
	@ResponseBody
	@RequestMapping("/unspent")
	@ApiOperation(value = "获取BTC UTXO", notes = "获取BTC UTXO",httpMethod = "GET")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "address",value = "地址", required = true, paramType = "query")
    })
	public RespPojo getBtcUtxo(HttpServletRequest request){
        logger.info("---获取BTC UTXO方法---");
        
        RespPojo resp=new RespPojo();
        
		String address = request.getParameter("address")==null?"":request.getParameter("address");
		if (StringUtils.isBlank(address)) {
            resp.setRetCode(Constants.PARAMETER_CODE);
            resp.setRetMsg("address不能为空");
            return resp;
        }
        
        UTXOPojo utxo_pojo = new UTXOPojo();
        UTXO utxo;
        Object utxoObj = null;
		try {
            utxo = utxoService.getBtcUtxo(address);
            utxoObj = GetResultJsonObject(utxo.getUtxo());
		}catch(BusiException e){
			 logger.error("获取BTC UTXO异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("获取BTC UTXO异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if (utxo != null) {
			utxo_pojo = new UTXOPojo();
			utxo_pojo.setUtxo(utxo.getUtxo());
			Map<String, Object> rtnMap = new HashMap<String, Object>();
            rtnMap.put("unspent_outputs", utxoObj);
			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}
}