package com.biwork.mapper;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);
    User selectByPhonePassword(@Param("phone") String phone,@Param("password") String password);
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}