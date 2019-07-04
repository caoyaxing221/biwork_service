package com.biwork.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.Flow;
import com.biwork.vo.FlowListVo;
import com.biwork.vo.FlowVo;

public interface FlowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Flow record);

    int insertSelective(Flow record);

    Flow selectByPrimaryKey(Integer id);
    FlowListVo selectByName(@Param("name") String name,@Param("teamId")Integer id);
    FlowVo getFlowInfo(@Param("userId") String userId,@Param("id")Integer id);
    List<FlowListVo> getFlowList(@Param("userId") String userId,@Param("teamId")Integer teamId);
    List<FlowListVo> getUseFlowList(@Param("userId") Integer userId,@Param("teamId")Integer teamId);
    int updateByPrimaryKeySelective(Flow record);
    FlowVo getUseFlowInfo(@Param("userId") Integer userId,@Param("id")Integer id);
    int updateByPrimaryKey(Flow record);
}