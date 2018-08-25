package com.biwork.mapper;

import com.biwork.entity.AirdropTask;

public interface AirdropTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AirdropTask record);

    int insertSelective(AirdropTask record);

    AirdropTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AirdropTask record);

    int updateByPrimaryKey(AirdropTask record);
}