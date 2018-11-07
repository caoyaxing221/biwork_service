package com.biwork.service;

import java.util.List;

import com.biwork.entity.AddCoin;
import com.biwork.vo.CoinInfoVo;


public interface AddCoinService {
	String getLikeQueryCoin(String likeQueryStr, String queryMark) throws Exception;
	List<AddCoin> queryCoinInfo(String queryMark, String queryName, String contractAddress) throws Exception;
	List<CoinInfoVo> queryCoinInfoAll() throws Exception;
}
