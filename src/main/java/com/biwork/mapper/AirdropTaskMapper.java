package com.biwork.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.AirdropTask;
import com.biwork.vo.TaskListVo;
import com.biwork.vo.TaskVo;

public interface AirdropTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AirdropTask record);

    int insertSelective(AirdropTask record);
    List<TaskListVo>selectByTeamId(@Param("teamId")Integer teamId,@Param("userId")Integer userId,
    		@Param("fetch")Integer fetch,@Param("offset")Integer offset,@Param("type")Integer type);
   TaskVo selectByTaskId(@Param("taskId")Integer taskId,@Param("userId")Integer userId);
    AirdropTask selectByPrimaryKey(Integer id);
    AirdropTask selectByName(@Param("name") String name ,@Param("teamId") Integer teamId);
    int updateByPrimaryKeySelective(AirdropTask record);

    int updateByPrimaryKey(AirdropTask record);
}