package com.biwork.mapper;

import java.util.List;

import com.biwork.entity.Team;
import com.biwork.po.TeamSeed;
import com.biwork.vo.TeamInfoVo;
import com.biwork.vo.TeamVo;

public interface TeamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Integer id);
    
    TeamInfoVo selectById(Integer id);
    
    List<TeamVo> selectTeamSize();
    
    List<TeamVo> selectByCreateUserId(Integer id);
    List<TeamVo> selectByJoinUserId(Integer id);
    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);
    
    int updateSeedByTeamId(TeamSeed teamSeed);
}