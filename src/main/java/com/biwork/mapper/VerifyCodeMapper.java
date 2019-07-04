package com.biwork.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.VerifyCode;

public interface VerifyCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VerifyCode record);

    int insertSelective(VerifyCode record);

    VerifyCode selectByPrimaryKey(Integer id);
    VerifyCode selectByCode(@Param("phone") String phone,@Param("code") String code,@Param("type") String type,@Param("expireTime") Date expireTime);
    int updateByPrimaryKeySelective(VerifyCode record);

    int updateByPrimaryKey(VerifyCode record);
}