package com.biwork.service;

import com.biwork.entity.Balance;
import com.biwork.po.BalancePojo;

public interface BalanceService {
	Balance getEthBalance() throws Exception;
	Balance getErc20Balance() throws Exception;
}
