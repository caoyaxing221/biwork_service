package com.biwork.service.Impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biwork.entity.Balance;
import com.biwork.service.BalanceService;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

@Service("BalanceService")
public class BalanceServiceImpl implements BalanceService {

	static Logger log = LoggerFactory.getLogger(BalanceService.class);
	private static final String PRO_URL = "https://mainnet.infura.io/PVMw2QL6TZTb2TTgIgrs";

	public static Transaction createEthCallTransaction(String from, String to, String data) {
        return new Transaction(from, null, null, null, to, null, data);
	}

	@Override
	public Balance getEthBalance(String account) {
		Balance bl = new Balance();
		Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
    	BigInteger bal = null;
		try {
			bal = web3j.ethGetBalance(account, DefaultBlockParameterName.LATEST).send().getBalance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BigDecimal value = new BigDecimal(bal);
		BigDecimal ether = Convert.fromWei(value, Convert.Unit.ETHER);
	
		bl.setAccount(account);
		bl.setBalance(ether.toString());
    	
		return bl;
	}

	@Override
	public Balance getErc20Balance(String account, String contractAddress) {
		Balance bl = new Balance();
		
		Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));
		
		String bal = "";		
		String decimals = "";

		try {
			//web3.sha3('balanceOf(address)').substring(0,10) "0x70a08231"
			bal = web3j.ethCall(Transaction.createEthCallTransaction(account, contractAddress, "0x70a08231" + "000000000000000000000000" + account.substring(2)), DefaultBlockParameterName.LATEST).send().getResult();

			//web3.sha3("decimals()").substring(0,10) "0x313ce567"
			decimals = web3j.ethCall(Transaction.createEthCallTransaction(account, contractAddress, "0x313ce567"), DefaultBlockParameterName.LATEST).send().getResult();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//https://api.etherscan.io/api?module=account&action=tokenbalance&contractaddress=0xcb97e65f07da24d46bcdd078ebebd7c6e6e3d750&address=0x0D0707963952f2fBA59dD06f2b425ace40b492Fe&tag=latest&apikey=YourApiKeyToken
		BigInteger bal_bi = new BigInteger(bal.substring(2, bal.length()), 16);
		BigInteger dec_bi = new BigInteger(decimals.substring(2, decimals.length()), 16);

		BigDecimal bal_bd = new BigDecimal(bal_bi);
		BigDecimal decimals_bd = new BigDecimal(dec_bi);
		BigDecimal ten = new BigDecimal(10);

		BigDecimal fixed = bal_bd.divide(ten.pow(decimals_bd.intValue()), decimals_bd.intValue(), BigDecimal.ROUND_HALF_UP);

		bl.setAccount(account);
		bl.setBalance(fixed.toString());

		return bl;
	}
}
