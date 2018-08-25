package com.biwork.mapper;

import com.biwork.entity.AirdropAddress;

public interface AirdropAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AirdropAddress record);

    int insertSelective(AirdropAddress record);

    AirdropAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AirdropAddress record);

    int updateByPrimaryKey(AirdropAddress record);
}