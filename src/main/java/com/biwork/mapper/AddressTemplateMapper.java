package com.biwork.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.AddressTemplate;
import com.biwork.vo.AddressTemplateListVo;
import com.biwork.vo.AddressTemplateVo;


public interface AddressTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AddressTemplate record);

    int insertSelective(AddressTemplate record);

    AddressTemplate selectByPrimaryKey(Integer id);
    List<AddressTemplateListVo>selectByTeamId(@Param("teamId")Integer teamId,@Param("userId")Integer userId,
    		@Param("fetch")Integer fetch,@Param("offset")Integer offset);
    AddressTemplateVo selectByTemplateId(@Param("templateId")Integer taskId,@Param("userId")Integer userId);
    int updateByPrimaryKeySelective(AddressTemplate record);

    int updateByPrimaryKeyWithBLOBs(AddressTemplate record);

    int updateByPrimaryKey(AddressTemplate record);
}