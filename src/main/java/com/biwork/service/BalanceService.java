package com.biwork.service;

import com.biwork.entity.Balance;
import com.biwork.po.BalancePojo;

public interface BalanceService {
	Balance getEthBalance(String account) throws Exception;
	Balance getErc20Balance(String account, String contractAddr) throws Exception;
	Balance getBtcBalance(String account) throws Exception;
}
