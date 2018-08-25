package com.biwork.service;

import com.biwork.entity.RawTx;

public interface RawTxService {
	RawTx getEthRawTx(String data) throws Exception;
	RawTx getBtcRawTx(String data) throws Exception;
}