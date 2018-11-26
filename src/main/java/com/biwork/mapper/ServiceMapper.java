package com.biwork.mapper;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.Service;

public interface ServiceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Service record);

    int insertSelective(Service record);

    Service selectByPrimaryKey(Integer id);
    Service selectByUserId(@Param("userId") Integer userId);
    int updateByPrimaryKeySelective(Service record);

    int updateByPrimaryKey(Service record);
}