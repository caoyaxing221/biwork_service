package com.biwork.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.AddCoin;
import com.biwork.vo.CoinInfoVo;

public interface AddCoinMapper {
	List<AddCoin> queryCoinInfo(@Param("coinName")String coinName,
    		 @Param("coinMark")String coinMark, @Param("contractAddress")String contractAddress);
	List<CoinInfoVo> queryCoinInfoAll();
}


