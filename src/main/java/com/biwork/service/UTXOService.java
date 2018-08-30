package com.biwork.service;

import com.biwork.entity.UTXO;

public interface UTXOService {
	UTXO getBtcUtxo(String address) throws Exception;
}