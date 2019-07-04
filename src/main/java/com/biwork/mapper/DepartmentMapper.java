package com.biwork.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.Department;
import com.biwork.vo.TeamVo;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);
    List<TeamVo> selectDepartmentList(@Param("teamId")Integer teamId);
    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}