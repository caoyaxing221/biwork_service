package com.biwork.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biwork.entity.TxGasLimit;
import com.biwork.mapper.TxGasLimitMapper;
import com.biwork.service.TxFeeService;
import com.biwork.service.TxGasLimitService;

@Service("TxGasLimitService")
public class TxGasLimitServiceImpl implements TxGasLimitService{
	static Logger log = LoggerFactory.getLogger(TxGasLimitService.class);
	
	@Autowired
	private TxGasLimitMapper txGasLimitMapper;
	
	
	public List<TxGasLimit> getGasLimit() {
		log.info("查询结果：" + txGasLimitMapper.selectallCoinInfo());
		return txGasLimitMapper.selectallCoinInfo();
	}
	
}
