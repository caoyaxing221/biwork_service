package com.biwork.service;

import java.util.List;
import com.biwork.po.request.ReceiverMsgPojo;
import com.biwork.po.request.AddressTemplateMsgPojo;
import com.biwork.vo.AddressTemplateListVo;
import com.biwork.vo.AddressTemplateVo;
import com.biwork.vo.FlowListVo;
import com.biwork.vo.FlowVo;
import com.biwork.vo.ProcessListVo;
import com.biwork.vo.ProcessVo;

public interface FlowProcessService {	 
	int addFlow(String teamId ,String name,String isBatch,String visibleAll,String authList,String nodeList,String userId
			,String templateNo);
	boolean editFlow(String flowId ,String name,String isBatch,String visibleAll,String authList,String nodeList,String userId
			,String templateNo);
	List<FlowListVo> queryFlows(String teamId,String userId);
	List<FlowListVo> queryUseFlows(String teamId,String userId);
	boolean delFlow(String flowId,String userId);
	FlowVo queryFlowById(String flowId,String userId);
	FlowVo queryUseFlowById(String flowId, String userId);
	int commitProcess(String userId, String flowId, String applicationNumber, String coinMark, String cause,
			String departmentId, String categoryId,List<ReceiverMsgPojo> receiverMsg, String receiver, String remark,
			String attachUrl,String airDropTaskId,String templateNo,String coinRateId);
	ProcessVo queryProcessById(String processId, String userId);
	boolean dealProcess(String userId, String processId, Integer dealFlag);
	List<ProcessListVo> queryProcess(String teamId, String userId, String fetch, String offset);
	List<ProcessListVo> queryApproveProcess(String teamId, String userId, String fetch, String offset, String state);
	int saveAddressTemplate(String userId, String teamId, String name, List<AddressTemplateMsgPojo> AddressTemplateMsg);
	List<AddressTemplateListVo> queryTemplateList(String teamId, String userId, String fetch, String offset);
	AddressTemplateVo queryTempalteInfo(String templateId, String userId);
}
