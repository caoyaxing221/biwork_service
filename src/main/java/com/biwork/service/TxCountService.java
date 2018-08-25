package com.biwork.service;

import com.biwork.entity.TxCount;

public interface TxCountService {
	TxCount getEthTxCount(String account) throws Exception;
	TxCount getBtcTxCount(String account) throws Exception;
}