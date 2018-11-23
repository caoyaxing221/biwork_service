package com.biwork.service.Impl;

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

@Service("CoinRateService")
public class CoinRateServiceImpl implements CoinRateService {
	static Logger log = LoggerFactory.getLogger(CoinRateService.class);
	private static final String COIN_URL = "http://op.juhe.cn/onebox/exchange/query";
	private static final String COIN_MARKET_URL = "https://api.coinmarketcap.com/v2/ticker/";
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
	
	public Map<String, Object> getAllCoinRate(String coinRateMark, List<String> coinRateIdList) {
		String rsp = "";
		String usaCoin = "";
		CoinRate coinRate;
		// 获取美元汇率
		List<CoinRate> coinRates = new ArrayList();
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
		Double usdCoin = Double.parseDouble(usaCoin) * 1e-2;
		System.out.println("usdCoin = " + usdCoin);
		Map<String, Object> coinRateMap = new HashMap<String, Object>();
		for(int j = 0; j < coinRateIdList.size(); j++) {
			try {
				rsp = HttpUtil.testGet(COIN_MARKET_URL + coinRateIdList.get(j) + "/");
			} catch (Exception e) {
				throw new BusiException(Integer.toString(e.hashCode()), e.getMessage());
			}
			Object rateObj = GetResultJsonObject(rsp);
			JSONObject rateObject = (JSONObject) rateObj;
			JSONObject data = (JSONObject) rateObject.get("data");
			String coinName = (String) data.get("name");
			String coinSymbol = (String) data.get("symbol");
			JSONObject coinQuotes = (JSONObject) data.get("quotes");
            JSONObject coinUsd = (JSONObject) coinQuotes.get("USD");
            Double coinPrice = (Double) coinUsd.get("price");
            Double lastPrice = coinPrice * usdCoin;
            coinRateMap.put(coinSymbol, lastPrice);
		}
		return coinRateMap;
	}
}
