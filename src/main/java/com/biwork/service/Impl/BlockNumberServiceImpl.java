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

import com.biwork.exception.BusiException;

@Service("BlockNumberService")
public class BlockNumberServiceImpl implements BlockNumberService {

	static Logger log = LoggerFactory.getLogger(BlockNumberService.class);
	private static final String PRO_URL = "https://mainnet.infura.io/PVMw2QL6TZTb2TTgIgrs";
	private static final String BCI_URL = "https://blockchain.info/";

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

		String rsp = "";
		try {
			rsp = HttpUtil.testGet(BCI_URL + "/q/getblockcount");
		} catch (Exception e) {
			throw new BusiException(Integer.toString(e.hashCode()), e.getMessage());
		}

		bn.setBlockNumber(rsp);

		return bn;
	}
}
