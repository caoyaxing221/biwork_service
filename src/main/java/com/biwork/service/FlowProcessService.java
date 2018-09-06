package com.biwork.service;

import java.util.List;

import com.biwork.po.request.ReceiverMsgPojo;
import com.biwork.vo.FlowListVo;
import com.biwork.vo.FlowVo;
import com.biwork.vo.ProcessListVo;
import com.biwork.vo.ProcessVo;

public interface FlowProcessService {	 
	int addFlow(String teamId ,String name,String isBatch,String visibleAll,String authList,String nodeList,String userId);
	boolean editFlow(String flowId ,String name,String isBatch,String visibleAll,String authList,String nodeList,String userId);
	List<FlowListVo> queryFlows(String teamId,String userId);
	List<FlowListVo> queryUseFlows(String teamId,String userId);
	boolean delFlow(String flowId,String userId);
	FlowVo queryFlowById(String flowId,String userId);
	FlowVo queryUseFlowById(String flowId, String userId);
	int commitProcess(String userId, String flowId, String applicationNumber, String currencyId, String cause,
			String departmentId, String categoryId, ReceiverMsgPojo receiverMsg, String receiver, String remark,
			String attachUrl,String airDropTaskId);
	ProcessVo queryProcessById(String processId, String userId);
	boolean dealProcess(String userId, String processId, Integer dealFlag);
	List<ProcessListVo> queryProcess(String teamId, String userId, String fetch, String offset);
	List<ProcessListVo> queryApproveProcess(String teamId, String userId, String fetch, String offset, String state);
}
