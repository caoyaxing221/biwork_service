package com.biwork.service;

import com.biwork.entity.TxLog;

public interface TxLogService {
	TxLog getEthTxLog(String query) throws Exception;
	TxLog getErc20TxLog(String query) throws Exception;
	TxLog getBtcTxLog(String query) throws Exception;
}