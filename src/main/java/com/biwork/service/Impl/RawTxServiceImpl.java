package com.biwork.service.Impl;

import org.springframework.stereotype.Service;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biwork.entity.RawTx;
import com.biwork.service.RawTxService;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import com.biwork.exception.BusiException;


import com.biwork.util.HttpUtil;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.client.BtcdClientImpl;

import java.util.Map;
import java.util.Properties;

@Service("RawTxService")
public class RawTxServiceImpl implements RawTxService {

	static Logger log = LoggerFactory.getLogger(RawTxService.class);
	private static final String PRO_URL = "https://mainnet.infura.io/PVMw2QL6TZTb2TTgIgrs";

	@Override
	public RawTx getEthRawTx(String data) throws Exception {
		RawTx rawTx = new RawTx();
        Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
        EthSendTransaction ethSendTransaction = new EthSendTransaction();
    	String hash = "";
		try {
            ethSendTransaction = web3j.ethSendRawTransaction(data).send();
		} catch (IOException e) {
			e.printStackTrace();
        }
        
        if (ethSendTransaction.hasError()) {
            throw new BusiException(Integer.toString(ethSendTransaction.getError().getCode()), ethSendTransaction.getError().getMessage());
        } else {
            hash = ethSendTransaction.getTransactionHash();
        }

        rawTx.setRawTx(hash);
   	
		return rawTx;
	}

	@Override
	public RawTx getBtcRawTx(String data) throws Exception {
		RawTx rawTx = new RawTx();
		
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpProvider = HttpClients.custom().setConnectionManager(cm)
				.build();
		Properties nodeConfig = new Properties();
		InputStream is = this.getClass().getResourceAsStream("/node_config.properties");
		nodeConfig.load(is);
		is.close();

		BtcdClient client = new BtcdClientImpl(httpProvider, nodeConfig);
		String txid = "";
		
		try {
			txid = client.sendRawTransaction(data);
		} catch (BitcoindException btce) {
            throw new BusiException(Integer.toString(btce.getCode()), btce.getMessage());			
		} catch (CommunicationException ce) {
            throw new BusiException(Integer.toString(ce.getCode()), ce.getMessage());		
		}
		
        rawTx.setRawTx(txid);
		
		return rawTx;
	}
}
