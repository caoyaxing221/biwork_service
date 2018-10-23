package com.biwork.service.Impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biwork.entity.TxFee;
import com.biwork.service.TxFeeService;
import com.biwork.util.HttpUtil;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.biwork.exception.BusiException;

@Service("TxFeeService")
public class TxFeeServiceImpl implements TxFeeService {
    String bitCoinFee = "";
	static Logger log = LoggerFactory.getLogger(TxFeeService.class);
	private static final String PRO_URL = "https://mainnet.infura.io/PVMw2QL6TZTb2TTgIgrs";
    private static final String BITCOIN_RECOMMENT = "https://bitcoinfees.earn.com/api/v1/fees/recommended";
	
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
	
	public String getBitCoinFee() throws Exception {
		try {
			bitCoinFee = HttpUtil.testGet(BITCOIN_RECOMMENT);
		} catch (Exception e) {
			throw new BusiException(Integer.toString(e.hashCode()), e.getMessage());
		}
		return bitCoinFee;
	}
	
//	public TxFee getEthGas() throws Exception {
//		TxFee txf = new TxFee();
//		Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
//		Request<?, EthEstimateGas> txf_bi = null;
//		Transaction transaction = null;
//		txf_bi = web3j.ethEstimateGas(transaction);
//
//		txf.setTxFee(txf_bi.toString());
//		return txf;
//	}
}
