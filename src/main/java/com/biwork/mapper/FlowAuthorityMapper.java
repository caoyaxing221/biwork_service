package com.biwork.mapper;

import com.biwork.entity.FlowAuthority;

public interface FlowAuthorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowAuthority record);

    int insertSelective(FlowAuthority record);

    FlowAuthority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowAuthority record);
    int updateByFlowIdSelective(FlowAuthority record);
    int updateByPrimaryKey(FlowAuthority record);
}