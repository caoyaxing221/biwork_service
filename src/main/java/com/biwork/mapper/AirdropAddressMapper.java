package com.biwork.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.AirdropAddress;
import com.biwork.vo.AddressListVo;

public interface AirdropAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AirdropAddress record);

    int insertSelective(AirdropAddress record);

    AirdropAddress selectByPrimaryKey(Integer id);
    List<AddressListVo>selectByTaskId(@Param("taskId")Integer taskId,
    		@Param("fetch")Integer fetch,@Param("offset")Integer offset);
    AirdropAddress selectByAddress(@Param("taskId")Integer taskId,
    		@Param("address")String address);
    int updateByPrimaryKeySelective(AirdropAddress record);

    int updateByPrimaryKey(AirdropAddress record);
}