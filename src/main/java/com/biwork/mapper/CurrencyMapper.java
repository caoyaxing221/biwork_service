package com.biwork.mapper;

import java.util.List;

import com.biwork.entity.Currency;

public interface CurrencyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Currency record);

    int insertSelective(Currency record);

    Currency selectByPrimaryKey(Integer id);
    List<Currency> selectCurrencys();
    int updateByPrimaryKeySelective(Currency record);

    int updateByPrimaryKey(Currency record);
}