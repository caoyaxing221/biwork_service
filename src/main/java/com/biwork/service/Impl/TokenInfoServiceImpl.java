package com.biwork.service.Impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biwork.entity.TokenInfo;
import com.biwork.service.TokenInfoService;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.methods.response.*;

import com.biwork.exception.BusiException;

@Service("TokenInfoService")
public class TokenInfoServiceImpl implements TokenInfoService {

	static Logger log = LoggerFactory.getLogger(TokenInfoService.class);
	private static final String PRO_URL = "https://mainnet.infura.io/PVMw2QL6TZTb2TTgIgrs";

	@Override
	public TokenInfo getErc20Info(String contractAddress) throws Exception {
		TokenInfo info = new TokenInfo();
        Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
        
        String decimals = "";
        String name = "";
        String symbol = "";
        String totalSupply = "";

        //web3.sha3("decimals()").substring(0,10) "0x313ce567"
        EthCall decimals_call = web3j.ethCall(Transaction.createEthCallTransaction(contractAddress, contractAddress, "0x313ce567"), DefaultBlockParameterName.LATEST).send();
        if (decimals_call.hasError()) {
            throw new BusiException(Integer.toString(decimals_call.getError().getCode()), decimals_call.getError().getMessage());
        } else {
            decimals = decimals_call.getResult();
        }

        //web3.sha3("name()").substring(0,10) "0x06fdde03"
        EthCall name_call = web3j.ethCall(Transaction.createEthCallTransaction(contractAddress, contractAddress, "0x06fdde03"), DefaultBlockParameterName.LATEST).send();
        if (name_call.hasError()) {
            throw new BusiException(Integer.toString(name_call.getError().getCode()), name_call.getError().getMessage());
        } else {
            name = name_call.getResult();
        }

        //web3.sha3("symbol()").substring(0,10) "0x95d89b41"
        EthCall symbol_call = web3j.ethCall(Transaction.createEthCallTransaction(contractAddress, contractAddress, "0x95d89b41"), DefaultBlockParameterName.LATEST).send();
        if (symbol_call.hasError()) {
            throw new BusiException(Integer.toString(symbol_call.getError().getCode()), symbol_call.getError().getMessage());
        } else {
            symbol = symbol_call.getResult();
        }

        //web3.sha3("totalSupply()").substring(0,10) "0x18160ddd"
        EthCall totalSupply_call = web3j.ethCall(Transaction.createEthCallTransaction(contractAddress, contractAddress, "0x18160ddd"), DefaultBlockParameterName.LATEST).send();
        if (totalSupply_call.hasError()) {
            throw new BusiException(Integer.toString(totalSupply_call.getError().getCode()), totalSupply_call.getError().getMessage());
        } else {
            totalSupply = totalSupply_call.getResult();
        }
        
        info.setDecimals(decimals);
        info.setName(name);
        info.setSymbol(symbol);
        info.setTotalSupply(totalSupply);
   	
		return info;
	}
}
