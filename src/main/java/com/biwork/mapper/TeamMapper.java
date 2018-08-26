package com.biwork.mapper;

import java.util.List;

import com.biwork.entity.Team;
import com.biwork.vo.TeamVo;

public interface TeamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Integer id);
    List<TeamVo> selectByCreateUserId(Integer id);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);
}