package com.biwork.service;

import java.util.List;
import com.biwork.entity.CoinRate;

public interface CoinRateService {
	List<CoinRate> getAllCoinRate() throws Exception;
}
