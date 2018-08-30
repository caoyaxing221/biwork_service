package com.biwork.service.Impl;

import com.biwork.entity.UTXO;
import com.biwork.service.UTXOService;
import com.biwork.util.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.biwork.exception.BusiException;

@Service("UTXOService")
public class UTXOServiceImpl implements UTXOService {
    static Logger log = LoggerFactory.getLogger(UTXOService.class);
    private static final String BCI_URL = "https://blockchain.info/";
    
	@Override
	public UTXO getBtcUtxo(String address) throws Exception {
		UTXO utxo = new UTXO();
       
        String rsp = "";
        try {
            rsp = HttpUtil.testGet(BCI_URL + "unspent?active=" + address);
        } catch (Exception e) {
            throw new BusiException(Integer.toString(e.hashCode()), e.getMessage());
        }
        utxo.setUtxo(rsp);
  	
		return utxo;
    }
}
