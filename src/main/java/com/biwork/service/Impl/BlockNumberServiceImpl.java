package com.biwork.service.Impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Properties;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.biwork.entity.BlockNumber;
import com.biwork.service.BlockNumberService;
import com.biwork.util.HttpUtil;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.client.BtcdClientImpl;

import java.util.Map;

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

        bn.setBlockNumber(bn_bi.toString(10));
   	
		return bn;
	}

	@Override
	public BlockNumber getBtcBlockNumber() throws Exception {
		BlockNumber bn = new BlockNumber();
		
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpProvider = HttpClients.custom().setConnectionManager(cm)
				.build();
		Properties nodeConfig = new Properties();
		InputStream is = this.getClass().getResourceAsStream("/node_config.properties");
		nodeConfig.load(is);
		is.close();

		BtcdClient client = new BtcdClientImpl(httpProvider, nodeConfig);
		Integer blockCount = client.getBlockCount();
		
		bn.setBlockNumber(blockCount.toString());
		
		return bn;
	}
}
