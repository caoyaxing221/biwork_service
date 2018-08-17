package com.biwork.mapper;

import com.biwork.entity.Currency;

public interface CurrencyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Currency record);

    int insertSelective(Currency record);

    Currency selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Currency record);

    int updateByPrimaryKey(Currency record);
}