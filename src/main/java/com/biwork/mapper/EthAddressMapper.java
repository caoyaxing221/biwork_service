package com.biwork.mapper;

import com.biwork.entity.EthAddress;

public interface EthAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EthAddress record);

    int insertSelective(EthAddress record);

    EthAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EthAddress record);

    int updateByPrimaryKey(EthAddress record);
}