package com.biwork.service;

import com.biwork.entity.BlockNumber;

public interface BlockNumberService {
	BlockNumber getEthBlockNumber() throws Exception;
	BlockNumber getBtcBlockNumber() throws Exception;
}