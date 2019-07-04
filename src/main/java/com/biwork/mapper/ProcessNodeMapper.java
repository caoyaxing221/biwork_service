package com.biwork.mapper;

import com.biwork.entity.ProcessNode;

public interface ProcessNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProcessNode record);

    int insertSelective(ProcessNode record);

    ProcessNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProcessNode record);

    int updateByPrimaryKey(ProcessNode record);
}