package com.biwork.service;

import java.util.List;
import java.util.Map;

public interface CoinRateService {
	Map<String, Object> getAllCoinRate(String coinRateMark, List<String> coinRateIdList) throws Exception;
}
