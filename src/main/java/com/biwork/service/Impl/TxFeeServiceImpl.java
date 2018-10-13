package com.biwork.service.Impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biwork.entity.TxFee;
import com.biwork.service.TxFeeService;
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

@Service("TxFeeService")
public class TxFeeServiceImpl implements TxFeeService {

	static Logger log = LoggerFactory.getLogger(TxFeeService.class);
	private static final String PRO_URL = "https://mainnet.infura.io/PVMw2QL6TZTb2TTgIgrs";

	@Override
	public TxFee getEthTxFee() throws Exception {
		TxFee txf = new TxFee();
		Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
		BigInteger txf_bi = null;
		try {
			txf_bi = web3j.ethGasPrice().send().getGasPrice();
		} catch (IOException e) {
			e.printStackTrace();
		}

		txf.setTxFee(txf_bi.toString(10));
		return txf;
	}
}
