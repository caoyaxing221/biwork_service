package com.biwork.mapper;

import java.util.List;

import com.biwork.entity.Version;

public interface VersionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Version record);

    int insertSelective(Version record);

    Version selectByPrimaryKey(Integer id);
    Version selectByType(String type);
    List<Version> selectBoth();
    int updateByPrimaryKeySelective(Version record);

    int updateByPrimaryKey(Version record);
}