package com.biwork.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.biwork.entity.CoinRate;
import com.biwork.exception.BusiException;
import com.biwork.service.CoinRateService;
import com.biwork.util.HttpUtil;
import com.kenai.jffi.Array;

@Service("CoinRateService")
public class CoinRateServiceImpl implements CoinRateService {
	
	static Logger log = LoggerFactory.getLogger(CoinRateService.class);
	private static final String COIN_URL = "http://op.juhe.cn/onebox/exchange/query";
	private static final String OKEX_URL = "https://www.okex.com/api/v1/ticker.do";
	private static final String AppKey = "0fab0b7318d7f03ef64543b06ecda60c"; 
	
	public static Object GetResultJsonObject(String json) throws BusiException {
		Object obj = new Object();
		JSONParser parser = new JSONParser();
		try {
			obj = parser.parse(json);
		} catch (ParseException e) {
			throw new BusiException(Integer.toString(e.getErrorType()), e.getMessage());
		}
		return obj;
	}
	
	@Override
	public List<CoinRate> getAllCoinRate() {
		String[] CoinSymbol = new String[]{"btc_usdt"};
		String rsp = "";
		String usaCoin = "";
		CoinRate coinRate;
		HashMap<String, Object> hashMap = new HashMap();
		try {
			rsp = HttpUtil.testGet(COIN_URL + "?key=" + AppKey);
		} catch (Exception e) {
			throw new BusiException(Integer.toString(e.hashCode()), e.getMessage());
		}
		Object balObj = GetResultJsonObject(rsp);
		JSONObject jsonObject = (JSONObject) balObj;
		String successCode = jsonObject.get("error_code").toString();
		JSONObject result = (JSONObject) jsonObject.get("result");
		ArrayList<List> list = new ArrayList();
		list = (ArrayList) result.get("list");
		for(int i = 0; i < list.size(); i ++) {
			if(list.get(i).get(0).equals("美元")){
				usaCoin = list.get(i).get(5).toString();
			}
		}
		
		System.out.println("usaCoin = " + usaCoin);
	
		return null;
	}

}
