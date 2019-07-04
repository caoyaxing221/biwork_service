package com.biwork.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.Service;
import com.biwork.vo.ServiceVo;

public interface ServiceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Service record);

    int insertSelective(Service record);

    Service selectByPrimaryKey(Integer id);
    Service selectByUserId(@Param("userId") Integer userId);
    List<ServiceVo> selectListByUserId(@Param("userId") Integer userId);
    int updateByPrimaryKeySelective(Service record);

    int updateByPrimaryKey(Service record);
}