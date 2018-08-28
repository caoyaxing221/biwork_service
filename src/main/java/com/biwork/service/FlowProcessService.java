package com.biwork.service;

import java.util.List;

import com.biwork.vo.FlowListVo;
import com.biwork.vo.FlowVo;

public interface FlowProcessService {	 
	int addFlow(String teamId ,String name,String isBatch,String visibleAll,String authList,String nodeList,String userId);
	boolean editFlow(String flowId ,String name,String isBatch,String visibleAll,String authList,String nodeList,String userId);
	List<FlowListVo> queryFlows(String teamId,String userId);
	boolean delFlow(String flowId,String userId);
	FlowVo queryFlowById(String flowId,String userId);
}
