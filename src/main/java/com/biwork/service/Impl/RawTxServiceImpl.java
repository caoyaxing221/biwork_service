package com.biwork.service.Impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biwork.entity.RawTx;
import com.biwork.service.RawTxService;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

import com.biwork.exception.BusiException;

@Service("RawTxService")
public class RawTxServiceImpl implements RawTxService {

	static Logger log = LoggerFactory.getLogger(RawTxService.class);
	private static final String PRO_URL = "https://mainnet.infura.io/PVMw2QL6TZTb2TTgIgrs";

	@Override
	public RawTx getEthRawTx(String data) throws Exception {
		RawTx rawTx = new RawTx();
        Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
        EthSendTransaction ethSendTransaction = new EthSendTransaction();
    	String hash = "";
		try {
            ethSendTransaction = web3j.ethSendRawTransaction(data).send();
		} catch (IOException e) {
			e.printStackTrace();
        }
        
        if (ethSendTransaction.hasError()) {
            throw new BusiException(Integer.toString(ethSendTransaction.getError().getCode()), ethSendTransaction.getError().getMessage());
        } else {
            hash = ethSendTransaction.getTransactionHash();
        }

        rawTx.setRawTx(hash);
   	
		return rawTx;
	}

	@Override
	public RawTx getBtcRawTx(String data) throws Exception {
		RawTx rawTx = new RawTx();
		// Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
    	// String hash = "";
		// try {
        //     hash = web3j.ethSendRawTransaction(data).send().getTransactionHash();
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }

        // rawTx.setRawTx(hash);
   	
		return rawTx;
	}
}
