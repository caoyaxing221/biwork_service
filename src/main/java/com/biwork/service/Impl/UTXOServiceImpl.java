package com.biwork.service.Impl;

import com.biwork.entity.UTXO;
import com.biwork.service.UTXOService;
import com.biwork.util.HttpUtil;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.biwork.exception.BusiException;

@Service("UTXOService")
public class UTXOServiceImpl implements UTXOService {
    static Logger log = LoggerFactory.getLogger(UTXOService.class);
    private static final String BCI_URL = "https://blockchain.info/";
    
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
	public UTXO getBtcUtxo(String address) throws Exception {
		UTXO utxo = new UTXO();
       
        String rsp = "";
        try {
            rsp = HttpUtil.testGet(BCI_URL + "unspent?active=" + address);
        } catch (Exception e) {
            throw new BusiException(Integer.toString(e.hashCode()), e.getMessage());
        }
        
        Object utxoObj = new Object();
        String utxoStr = "";
        try {
        	 utxoObj = GetResultJsonObject(rsp);
     		 JSONObject jsonObject = (JSONObject) utxoObj;
    		 JSONArray unspent_outputs = (JSONArray)jsonObject.get("unspent_outputs");
    		 utxoStr = unspent_outputs.toString();
        } catch (Exception e) {
        	utxoStr = "[]";
        }
        
        utxo.setUtxo(utxoStr);
  	
		return utxo;
    }
}
