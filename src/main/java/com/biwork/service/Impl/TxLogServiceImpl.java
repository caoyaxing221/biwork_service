package com.biwork.service.Impl;

import com.biwork.entity.TxLog;
import com.biwork.service.TxLogService;
import com.biwork.util.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.biwork.exception.BusiException;

@Service("TxLogService")
public class TxLogServiceImpl implements TxLogService {
	static Logger log = LoggerFactory.getLogger(TxLogService.class);
	
	private static final String BCI_URL = "https://blockchain.info/";

	//https://blockchain.info/balance?active=$address
	//	https://blockchain.info/balance?active=1MDUoxL1bGvMxhuoDYx6i11ePytECAk9QK
	//	{
	//	    "1MDUoxL1bGvMxhuoDYx6i11ePytECAk9QK": {
	//	        "final_balance": 0,
	//	        "n_tx": 0,
	//	        "total_received": 0
	//	    }
	//	}
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
	public TxLog getEthTxLog(String query) throws Exception {
		TxLog txl = new TxLog();
		
		String rsp = "";
		try {
			rsp = HttpUtil.testGet(query);
		} catch (Exception e) {
			throw new BusiException(Integer.toString(e.hashCode()), e.getMessage());
		}
        txl.setTxLog(rsp);
  	
		return txl;
    }
    
    @Override
	public TxLog getErc20TxLog(String query) throws Exception {
		TxLog txl = new TxLog();
       
		String rsp = "";
		try {
			rsp = HttpUtil.testGet(query);
		} catch (Exception e) {
			throw new BusiException(Integer.toString(e.hashCode()), e.getMessage());
		}
        txl.setTxLog(rsp);
  	
		return txl;
	}

	@Override
	public TxLog getBtcTxLog(String query) throws Exception {
		TxLog txl = new TxLog();
       
		String rsp = "";
		try {
			rsp = HttpUtil.testGet(query);
		} catch (Exception e) {
			throw new BusiException(Integer.toString(e.hashCode()), e.getMessage());
		}
		
		Object balObj = GetResultJsonObject(rsp);

		JSONObject jsonObject = (JSONObject) balObj;
		System.out.println("bitcoin recor" + jsonObject);
		JSONArray txs = (JSONArray)jsonObject.get("txs");

		txl.setTxLog(txs.toString());
 	
		return txl;
	}
}
