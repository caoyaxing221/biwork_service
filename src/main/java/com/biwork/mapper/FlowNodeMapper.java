package com.biwork.mapper;

import com.biwork.entity.FlowNode;

public interface FlowNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowNode record);

    int insertSelective(FlowNode record);

    FlowNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowNode record);
    int updateByFlowIdSelective(FlowNode record);
    int updateByPrimaryKey(FlowNode record);
}