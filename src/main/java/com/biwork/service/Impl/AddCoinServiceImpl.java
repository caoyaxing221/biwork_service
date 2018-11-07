package com.biwork.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biwork.entity.AddCoin;
import com.biwork.exception.BusiException;
import com.biwork.service.AddCoinService;
import com.biwork.util.HttpUtil;
import com.biwork.vo.CoinInfoVo;
import com.biwork.mapper.AddCoinMapper;

@Service("AddCoinService")
public class AddCoinServiceImpl implements AddCoinService{
	String rsp = "";
	static Logger log = LoggerFactory.getLogger(AddCoinService.class);
	
	@Autowired
	private AddCoinMapper addCoinMapper;
	private static final String LIKE_QUERY_URL = "https://etherscan.io/searchHandler?term=";

	@Override
	public String getLikeQueryCoin(String likeQueryStr, String queryMark) throws Exception 
	{
		try {
			rsp = HttpUtil.testGet(LIKE_QUERY_URL + likeQueryStr);
		} catch (Exception e) {
			throw new BusiException(Integer.toString(e.hashCode()), e.getMessage());
		}
		return rsp;
	}
	
	public List<CoinInfoVo> queryCoinInfoAll() throws Exception {
		return addCoinMapper.queryCoinInfoAll();
	}
	
	public List<AddCoin> queryCoinInfo(String coinName, String coinMark, String contractAddress) throws Exception {
		log.info("查询结果：" + addCoinMapper.queryCoinInfo(coinName, coinMark, contractAddress));
		return addCoinMapper.queryCoinInfo(coinName, coinMark, contractAddress);
	}
}
