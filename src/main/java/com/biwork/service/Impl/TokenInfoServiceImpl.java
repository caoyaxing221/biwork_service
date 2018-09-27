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

import org.web3j.abi.datatypes.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.biwork.exception.BusiException;

@Service("TokenInfoService")
public class TokenInfoServiceImpl implements TokenInfoService {

    static Logger log = LoggerFactory.getLogger(TokenInfoService.class);
    private static final String PRO_URL = "https://mainnet.infura.io/PVMw2QL6TZTb2TTgIgrs";

    private static String DecodeSolidityString(String s) {
        String str = "";

        String cnt_str = "";
        String enc_str = "";


        str = s.substring(2);

        cnt_str = str.substring(64, 128);
        enc_str = str.substring(128);

        StringBuilder sb = new StringBuilder();

        BigInteger cnt = new BigInteger(cnt_str, 16);
        for (int i = 0; i < cnt.intValue(); i++) {
            String ch = enc_str.substring(2 * i, 2 * (i + 1));
            int dec = Integer.parseInt(ch, 16);
            sb.append((char) dec);
        }

        return sb.toString();
    }

    @Override
    public TokenInfo getErc20Info(String contractAddress) throws Exception {
        TokenInfo info = new TokenInfo();
        Web3j web3j = Web3j.build(new HttpService(PRO_URL, true));

        String decimals = "";
        String name = "";
        String symbol = "";
        String totalSupply = "";

        // web3.sha3("decimals()").substring(0,10) "0x313ce567"
        EthCall decimals_call = web3j
                .ethCall(Transaction.createEthCallTransaction(contractAddress, contractAddress, "0x313ce567"),
                        DefaultBlockParameterName.LATEST)
                .send();
        if (decimals_call.hasError()) {
            throw new BusiException(Integer.toString(decimals_call.getError().getCode()),
                    decimals_call.getError().getMessage());
        } else {
            decimals = decimals_call.getResult();
        }

        // web3.sha3("name()").substring(0,10) "0x06fdde03"
        EthCall name_call = web3j
                .ethCall(Transaction.createEthCallTransaction(contractAddress, contractAddress, "0x06fdde03"),
                        DefaultBlockParameterName.LATEST)
                .send();
        if (name_call.hasError()) {
            throw new BusiException(Integer.toString(name_call.getError().getCode()),
                    name_call.getError().getMessage());
        } else {
            name = name_call.getResult();
        }

        // web3.sha3("symbol()").substring(0,10) "0x95d89b41"
        EthCall symbol_call = web3j
                .ethCall(Transaction.createEthCallTransaction(contractAddress, contractAddress, "0x95d89b41"),
                        DefaultBlockParameterName.LATEST)
                .send();
        if (symbol_call.hasError()) {
            throw new BusiException(Integer.toString(symbol_call.getError().getCode()),
                    symbol_call.getError().getMessage());
        } else {
            symbol = symbol_call.getResult();
        }

        // web3.sha3("totalSupply()").substring(0,10) "0x18160ddd"
        EthCall totalSupply_call = web3j
                .ethCall(Transaction.createEthCallTransaction(contractAddress, contractAddress, "0x18160ddd"),
                        DefaultBlockParameterName.LATEST)
                .send();
        if (totalSupply_call.hasError()) {
            throw new BusiException(Integer.toString(totalSupply_call.getError().getCode()),
                    totalSupply_call.getError().getMessage());
        } else {
            totalSupply = totalSupply_call.getResult();
        }

        BigInteger dec_bi = new BigInteger(decimals.substring(2, decimals.length()), 16);
        BigDecimal decimals_bd = new BigDecimal(dec_bi);
        decimals = decimals_bd.toString();

        info.setDecimals(decimals);// uint8 public decimals

        name = DecodeSolidityString(name);
        info.setName(name);// string public name

        symbol = DecodeSolidityString(symbol);
        info.setSymbol(symbol);// string public symbol

        BigInteger ts_bi = new BigInteger(totalSupply.substring(2, totalSupply.length()), 16);
        BigDecimal ts_bd = new BigDecimal(ts_bi);
        BigDecimal ten = new BigDecimal(10);
        BigDecimal ts_fixed = ts_bd.divide(ten.pow(decimals_bd.intValue()), decimals_bd.intValue(),
                BigDecimal.ROUND_HALF_UP);
        totalSupply = ts_fixed.toString();

        info.setTotalSupply(totalSupply);// uint256 public totalSupply;

        return info;
    }
}
