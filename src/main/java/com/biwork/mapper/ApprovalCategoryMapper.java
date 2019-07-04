package com.biwork.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.ApprovalCategory;
import com.biwork.vo.TeamVo;

public interface ApprovalCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApprovalCategory record);

    int insertSelective(ApprovalCategory record);

    ApprovalCategory selectByPrimaryKey(Integer id);
    List<TeamVo> selectApprovalCategoryList(@Param("teamId")Integer teamId);
    int updateByPrimaryKeySelective(ApprovalCategory record);

    int updateByPrimaryKey(ApprovalCategory record);
}