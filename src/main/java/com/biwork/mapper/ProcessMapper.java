package com.biwork.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biwork.entity.Process;
import com.biwork.entity.ProcessWithBLOBs;
import com.biwork.vo.ProcessListVo;
import com.biwork.vo.ProcessVo;

public interface ProcessMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProcessWithBLOBs record);

    int insertSelective(ProcessWithBLOBs record);
    int insertNodes(@Param("processId") Integer processId,@Param("flowId") Integer flowId );
    ProcessWithBLOBs selectByPrimaryKey(Integer id);
    ProcessWithBLOBs selectByApplicationNo(@Param("appNo") String appNo);
    ProcessVo getProcessInfo(@Param("id") Integer processId,@Param("userId") Integer userId);
    List<ProcessListVo> getProcessList(@Param("teamId") Integer teamId,@Param("fetch") Integer fetch,@Param("offset") Integer offset,@Param("userId") Integer userId);
    List<ProcessListVo> getApproveProcessList(@Param("teamId") Integer teamId,@Param("fetch") Integer fetch,@Param("offset") Integer offset,@Param("userId") Integer userId,@Param("state") Integer state);
    int updateByPrimaryKeySelective(ProcessWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProcessWithBLOBs record);

    int updateByPrimaryKey(Process record);
}