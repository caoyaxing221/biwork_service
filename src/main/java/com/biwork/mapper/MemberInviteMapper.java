package com.biwork.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.MemberInvite;
import com.biwork.vo.InviteVo;
import com.biwork.vo.MemberVo;

public interface MemberInviteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberInvite record);

    int insertSelective(MemberInvite record);

    MemberInvite selectByPrimaryKey(Integer id);
    List<MemberVo> selectByTeamId(@Param("teamId")Integer id,@Param("inviterId") String  inviterId);
    List<InviteVo> selectByUserId(@Param("userId")Integer userId);
    MemberVo selectByPhone(@Param("phone") String phone,@Param("teamId")Integer id);
    int updateByPrimaryKeySelective(MemberInvite record);

    int updateByPrimaryKey(MemberInvite record);
}