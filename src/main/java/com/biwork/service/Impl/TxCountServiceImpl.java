package com.biwork.service.Impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biwork.entity.TxCount;
import com.biwork.service.TxCountService;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

@Service("TxCountService")
public class TxCountServiceImpl implements TxCountService {

	static Logger log = LoggerFactory.getLogger(TxCountService.class);
	private static final String PRO_URL = "https://mainnet.infura.io/PVMw2QL6TZTb2TTgIgrs";

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
		// Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
    	// BigInteger txc_bi = null;
		// try {
		// 	txc_bi = web3j.ethGetTransactionCount(account, DefaultBlockParameterName.LATEST).send().getTransactionCount();
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }

        // txc.setTxCount(txc_bi.toString(10));
   	
		return txc;
	}
}
