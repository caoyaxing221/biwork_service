package com.biwork.service;

import java.math.BigInteger;

import com.biwork.entity.TxFee;

public interface TxFeeService {
	TxFee getEthTxFee() throws Exception;
	String getBitCoinFee() throws Exception;
	BigInteger getGasLimit(String formAddress, String toAddress, BigInteger value, BigInteger nonce, BigInteger gasPrice,
			String data, BigInteger gas) throws Exception;
}