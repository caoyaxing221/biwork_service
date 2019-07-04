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
	// 数据库密码: Mywp:hywT5:u
	public String getBitCoinFee() throws Exception {
		try {
			System.out.println("开始获取比特币手续费");
			bitCoinFee = HttpUtil.testGet(BITCOIN_RECOMMENT);
		} catch (Exception e) {
			throw new BusiException(Integer.toString(e.hashCode()), e.getMessage());
		}
		System.out.println("bitCoinFee =" + bitCoinFee);
		return bitCoinFee;
	}

	@Override
	public BigInteger getGasLimit(String formAddress, String toAddress, BigInteger value, BigInteger nonce,
			BigInteger gasPrice, String data, BigInteger gasLimit) throws Exception {
	Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
		Transaction transaction = new Transaction(formAddress,nonce, gasPrice, gasLimit, toAddress, value, data);
		transaction.getFrom();
		transaction.getTo();
		transaction.getValue();
		transaction.getData();
		transaction.getGasPrice();
		transaction.getNonce();
		transaction.getGas();
		EthEstimateGas ethEstimateGas;
		try {
            ethEstimateGas = web3j.ethEstimateGas(transaction).send();
            if (ethEstimateGas.hasError()){
                throw new RuntimeException(ethEstimateGas.getError().getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("网络错误");
        }
		return ethEstimateGas.getAmountUsed();
	}
}
