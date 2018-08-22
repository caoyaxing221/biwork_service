package com.biwork.service.Impl;

import org.springframework.stereotype.Service;

import com.biwork.entity.Balance;
import com.biwork.po.BalancePojo;
import com.biwork.service.BalanceService;

@Service("BalanceService")
public class BalanceServiceImpl implements BalanceService {

	@Override
	public Balance getEthBalance() {
		Balance bl = null;
		bl.setAccount("qqqqqqqqqqqqqqq");
		bl.setBalance("1000000");		
		return bl;
	}

	@Override
	public Balance getErc20Balance() {
		Balance bl = null;
		bl.setAccount("qqqqqqqqqqqqqqq");
		bl.setBalance("1000000");		
		return bl;
	}

	
}
