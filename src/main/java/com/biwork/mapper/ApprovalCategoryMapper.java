package com.biwork.mapper;

import com.biwork.entity.ApprovalCategory;

public interface ApprovalCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApprovalCategory record);

    int insertSelective(ApprovalCategory record);

    ApprovalCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApprovalCategory record);

    int updateByPrimaryKey(ApprovalCategory record);
}