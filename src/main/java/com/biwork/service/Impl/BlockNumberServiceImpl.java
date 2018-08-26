package com.biwork.service.Impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biwork.entity.BlockNumber;
import com.biwork.service.BlockNumberService;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

@Service("BlockNumberService")
public class BlockNumberServiceImpl implements BlockNumberService {

	static Logger log = LoggerFactory.getLogger(BlockNumberService.class);
	private static final String PRO_URL = "https://mainnet.infura.io/PVMw2QL6TZTb2TTgIgrs";

	@Override
	public BlockNumber getEthBlockNumber() throws Exception {
		BlockNumber bn = new BlockNumber();
		Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
    	BigInteger bn_bi = null;
		try {
			bn_bi = web3j.ethBlockNumber().send().getBlockNumber();
		} catch (IOException e) {
			e.printStackTrace();
		}

        bn.setBlockNUmber(bn_bi.toString(10));
   	
		return bn;
	}

	@Override
	public BlockNumber getBtcBlockNumber() throws Exception {
		BlockNumber bn = new BlockNumber();
		// Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
    	// BigInteger bn_bi = null;
		// try {
		// 	bn_bi = web3j.ethBlockNumber().send().getBlockNumber();
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }

        // bn.setBlockNUmber(bn_bi.toString(10));
   	
		return bn;
	}
}
