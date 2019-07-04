package com.biwork.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.Member;
import com.biwork.vo.MemberVo;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer id);
    
    List<MemberVo> selectByTeamId(@Param("teamId")Integer id,@Param("userId") String  userId);
    MemberVo selectByTeamIdUseId(@Param("teamId")Integer id,@Param("userId") String  userId);
    MemberVo selectByInviteTableId(@Param("inviteTableId")Integer inviteTableId);
    MemberVo selectByPhone(@Param("phone") String phone,@Param("teamId")Integer id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
}