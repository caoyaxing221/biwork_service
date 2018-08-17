package com.biwork.mapper;

import com.biwork.entity.MemberInvite;

public interface MemberInviteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberInvite record);

    int insertSelective(MemberInvite record);

    MemberInvite selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberInvite record);

    int updateByPrimaryKey(MemberInvite record);
}