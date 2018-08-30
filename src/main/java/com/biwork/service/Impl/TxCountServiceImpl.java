package com.biwork.service.Impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biwork.entity.TxCount;
import com.biwork.service.TxCountService;
import com.biwork.util.HttpUtil;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.biwork.exception.BusiException;

@Service("TxCountService")
public class TxCountServiceImpl implements TxCountService {

	static Logger log = LoggerFactory.getLogger(TxCountService.class);
	private static final String PRO_URL = "https://mainnet.infura.io/PVMw2QL6TZTb2TTgIgrs";
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
	public TxCount getEthTxCount(String account) throws Exception {
		TxCount txc = new TxCount();
		Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
    	BigInteger txc_bi = null;
		try {
			txc_bi = web3j.ethGetTransactionCount(account, DefaultBlockParameterName.LATEST).send().getTransactionCount();
		} catch (IOException e) {
			e.printStackTrace();
		}

        txc.setTxCount(txc_bi.toString(10));
   	
		return txc;
	}

	@Override
	public TxCount getBtcTxCount(String account) throws Exception {
		TxCount txc = new TxCount();
		
		String rsp = "";
		try {
			rsp = HttpUtil.testGet(BCI_URL + "balance?active=" + account);
		} catch (Exception e) {
			throw new BusiException(Integer.toString(e.hashCode()), e.getMessage());
		}
		
		Object balObj = GetResultJsonObject(rsp);

		JSONObject jsonObject = (JSONObject) balObj;
		JSONObject tmp = (JSONObject)jsonObject.get(account);
		String n_tx = tmp.get("n_tx").toString();

		txc.setTxCount(n_tx);
   	
		return txc;
	}
}
