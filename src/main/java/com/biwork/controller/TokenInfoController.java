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

import com.biwork.entity.TokenInfo;

import com.biwork.exception.BusiException;

import com.biwork.po.RespPojo;
import com.biwork.po.TokenInfoPojo;

import com.biwork.service.TokenInfoService;

import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "获取ERC20代币信息")
public class TokenInfoController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	//获取ERC20信息
	@Autowired
	TokenInfoService tokenInfoService;
	
	@ResponseBody
	@RequestMapping("/erc20_tokeninfo")
	@ApiOperation(value = "获取ERC20代币信息", notes = "获取ERC20代币信息",httpMethod = "GET")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "contract",value = "合约地址", required = true, paramType = "query")
    })
	public RespPojo getErc20Info(HttpServletRequest request){
		
		logger.info("---获取erc20代币信息方法---");
		String contract = request.getParameter("contract")==null?"":request.getParameter("contract");
		TokenInfoPojo tokenInfo_pojo=new TokenInfoPojo();
		
		RespPojo resp=new RespPojo();
		
		if(StringUtils.isBlank(contract)){
			  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("合约地址不能为空");
			  return resp;
		}
		
		TokenInfo tokenInfo;
		try {
			tokenInfo = tokenInfoService.getErc20Info(contract);
		}catch(BusiException e){
			 logger.error("获取erc20代币信息方法异常{}",e);
			  resp.setRetCode(e.getCode());
			  resp.setRetMsg(e.getMessage());
			  return resp;
		}
		catch (Exception e) {
			  logger.error("获取erc20代币信息方法异常{}",e);
			  resp.setRetCode(Constants.FAIL_CODE);
			  resp.setRetMsg(Constants.FAIL_MESSAGE);
			  return resp;
		}
		if(tokenInfo!=null){
			tokenInfo_pojo = new TokenInfoPojo();
            
            tokenInfo_pojo.setDecimals(tokenInfo.getDecimals());
            tokenInfo_pojo.setName(tokenInfo.getName());
            tokenInfo_pojo.setSymbol(tokenInfo.getSymbol());
            tokenInfo_pojo.setTotalSupply(tokenInfo.getTotalSupply());
            
			Map<String, Object> rtnMap = new HashMap<String, Object>();
            
            rtnMap.put("decimals", tokenInfo.getDecimals());
            rtnMap.put("name", tokenInfo.getName());
            rtnMap.put("symbol", tokenInfo.getSymbol());
            rtnMap.put("totalSupply", tokenInfo.getTotalSupply());

			resp.setRetCode(Constants.SUCCESSFUL_CODE);
			resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
			resp.setData(rtnMap);
			return resp;
		}
		return resp;
	}
}
