package com.biwork.service;

import com.biwork.entity.TokenInfo;

public interface TokenInfoService {
	TokenInfo getErc20Info(String contractAddress) throws Exception;
}