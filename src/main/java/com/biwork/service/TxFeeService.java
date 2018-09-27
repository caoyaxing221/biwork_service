package com.biwork.service;

import com.biwork.entity.TxFee;

public interface TxFeeService {
	TxFee getEthTxFee() throws Exception;
}