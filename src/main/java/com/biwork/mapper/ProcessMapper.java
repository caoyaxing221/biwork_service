package com.biwork.mapper;

import com.biwork.entity.Process;
import com.biwork.entity.ProcessWithBLOBs;

public interface ProcessMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProcessWithBLOBs record);

    int insertSelective(ProcessWithBLOBs record);

    ProcessWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProcessWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProcessWithBLOBs record);

    int updateByPrimaryKey(Process record);
}