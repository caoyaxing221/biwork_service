package com.biwork.service;

import java.util.List;

import com.biwork.entity.TxGasLimit;

public interface TxGasLimitService {
	List<TxGasLimit> getGasLimit() throws Exception;
}
