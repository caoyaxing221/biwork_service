package com.biwork.service.Impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.biwork.entity.AirdropTask;
import com.biwork.entity.ApprovalCategory;
import com.biwork.entity.Currency;
import com.biwork.entity.Department;
import com.biwork.entity.Flow;
import com.biwork.entity.FlowAuthority;
import com.biwork.entity.FlowNode;
import com.biwork.entity.Team;
import com.biwork.entity.ProcessWithBLOBs;
import com.biwork.entity.ProcessNode;
import com.biwork.exception.BusiException;
import com.biwork.mapper.AirdropTaskMapper;
import com.biwork.mapper.ApprovalCategoryMapper;
import com.biwork.mapper.CurrencyMapper;
import com.biwork.mapper.DepartmentMapper;
import com.biwork.mapper.FlowAuthorityMapper;
import com.biwork.mapper.FlowMapper;
import com.biwork.mapper.FlowNodeMapper;
import com.biwork.mapper.MemberMapper;
import com.biwork.mapper.ProcessMapper;
import com.biwork.mapper.ProcessNodeMapper;
import com.biwork.mapper.TeamMapper;
import com.biwork.po.request.ReceiverMsgPojo;
import com.biwork.service.FlowProcessService;
import com.biwork.util.Constants;
import com.biwork.vo.FlowListVo;
import com.biwork.vo.FlowVo;
import com.biwork.vo.MemberVo;
import com.biwork.vo.ProcessListVo;
import com.biwork.vo.ProcessNodeVo;
import com.biwork.vo.ProcessVo;



@Service("FlowProcessService")
public class FlowProcessServiceImpl implements FlowProcessService {
	static Logger log = LoggerFactory.getLogger(FlowProcessService.class);
	@Autowired
	private FlowMapper flowMapper;
	@Autowired
	private FlowAuthorityMapper flowAuthorityMapper;
	@Autowired
	private FlowNodeMapper flowNodeMapper;
	@Autowired
	private TeamMapper teamMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private CurrencyMapper currencyMapper;
	@Autowired
	private ApprovalCategoryMapper approvalCategoryMapper;
	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private ProcessMapper processMapper;
	@Autowired
	private ProcessNodeMapper processNodeMapper;
	@Autowired
	private AirdropTaskMapper airdropTaskMapper;
	@Override
	public int addFlow(String teamId, String name, String isBatch, String visibleAll, String authList, String nodeList,
			String userId) {
		int flowId=0;
		Team teamDb = teamMapper.selectByPrimaryKey(Integer.parseInt(teamId));
		if(null==teamDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.TEAM_NOT_FOUND);
		}
		if(!teamDb.getCreateUserId().toString().equals(userId)){
			throw new BusiException(Constants.FAIL_CODE,Constants.TEAM_NOT_FOUND);
		}
		FlowListVo flowVo=flowMapper.selectByName(name, Integer.parseInt(teamId));
		if(null!=flowVo){
			throw new BusiException(Constants.FAIL_CODE,Constants.FLOW_ALREADY_EXISTS);
		}
		String []authArr=authList.split("\\|");
		String []nodeArr=nodeList.split("\\|");
		String cUserid="";
		Flow flow =new Flow();
		flow.setCreateUserId(Integer.parseInt(userId));
		flow.setIsBatchTranser(Integer.parseInt(isBatch));
		flow.setName(name);
		flow.setTeamId(Integer.parseInt(teamId));
		flow.setVisibleAll(Integer.parseInt(visibleAll));
		flow.setNodeNum(nodeArr.length+1);
		flowMapper.insertSelective(flow);
		flowId=flow.getId();
		if(Integer.parseInt(visibleAll)==0){
			for(int i=0;i<authArr.length;i++){
				cUserid=authArr[i];
				FlowAuthority fA=new FlowAuthority();
				fA.setFlowId(flowId);
				fA.setUserId(Integer.parseInt(cUserid));
				flowAuthorityMapper.insertSelective(fA);
			}
		}
		int j=0;
		for( j=0;j<nodeArr.length;j++){
			cUserid=nodeArr[j];
			FlowNode fN=new FlowNode();
			fN.setFlowId(flowId);
			fN.setApproverId(Integer.parseInt(cUserid));
			fN.setNo(j+1);
			flowNodeMapper.insertSelective(fN);
		}
		//最后的审批人为管理员
		cUserid=userId;
		FlowNode fN=new FlowNode();
		fN.setFlowId(flowId);
		fN.setApproverId(Integer.parseInt(cUserid));
		fN.setNo(j+1);
		flowNodeMapper.insertSelective(fN);
		return flowId;
	}
	@Override
	public int commitProcess(String userId,String flowId, String applicationNumber, String currencyId,
			String cause, String departmentId, String categoryId,
			ReceiverMsgPojo receiverMsg,String receiver,String remark,String attachUrl
			,String airDropTaskId) {
		int processId=0;
		if(null!=airDropTaskId&&!"".equals(airDropTaskId)){
			AirdropTask taskDB = airdropTaskMapper.selectByPrimaryKey(Integer.parseInt(airDropTaskId));
			if(null==taskDB){
				throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
			}
		}
		ProcessWithBLOBs processDb=processMapper.selectByApplicationNo(applicationNumber);
		if(null!=processDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.ALREADY_EXISTS);
		}
		Flow flowDb =flowMapper.selectByPrimaryKey(Integer.parseInt(flowId));
		if(null==flowDb||flowDb.getState()!=0){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		Integer teamId=flowDb.getTeamId();
		MemberVo memberDb = memberMapper.selectByTeamIdUseId(teamId, userId);
		if( null==memberDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		 Currency currencyDb = currencyMapper.selectByPrimaryKey(Integer.parseInt(currencyId));
		if(null==currencyDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		 Department departmentDb = departmentMapper.selectByPrimaryKey(Integer.parseInt(departmentId));
		if(null==departmentDb||(null!=departmentDb.getTeamId()&&departmentDb.getTeamId()!=teamId)){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		  ApprovalCategory categoryDb = approvalCategoryMapper.selectByPrimaryKey(Integer.parseInt(categoryId));
		if(null==categoryDb||(null!=categoryDb.getTeamId()&&categoryDb.getTeamId()!=teamId)){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		
		ProcessWithBLOBs process =new ProcessWithBLOBs();
		process.setApplicationNumber(applicationNumber);
		process.setCategoryId(Integer.parseInt(categoryId));
		process.setCurrencyId(Integer.parseInt(currencyId));
		process.setCurrentNode(1);
		process.setDepartmentId(Integer.parseInt(departmentId));
		process.setReceiver(receiver);
		process.setSubmitterId(Integer.parseInt(userId));
		process.setTeamId(teamId);
		process.setAttachUrl(attachUrl);
		process.setCause(cause);
		process.setReceiverMsg(JSON.toJSONString(receiverMsg));
		process.setRemark(remark);
		process.setAirDropTaskId(airDropTaskId);
		processMapper.insertSelective(process);
		processId=process.getId();
		processMapper.insertNodes(processId, Integer.parseInt(flowId));
		return processId;
	}
	@Override
	public boolean dealProcess(String userId,String processId,Integer dealFlag) {
		ProcessVo processdb =processMapper.getProcessInfo(Integer.parseInt(processId), Integer.parseInt(userId));
		ProcessWithBLOBs process=new ProcessWithBLOBs();
		ProcessNode processNode=new ProcessNode();		
		if(null==processdb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		if(!processdb.getState().equals("0")){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		if((dealFlag==1||dealFlag==-1)&&processdb.getCanApprove().equals("0")){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
			
		}
		if((dealFlag==-2)&&!processdb.getSubmitterId().equals(userId)){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
			
		}
		if(dealFlag==1||dealFlag==-1){
			
			List<ProcessNodeVo> processNodeVo=processdb.getProcessNode();
			int i=0;
			for( i=0;i<processNodeVo.size();i++){
				if(processNodeVo.get(i).getApproverId().equals(userId)){
					break;
				}
			}
			if(i>processNodeVo.size()){
				throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
				
			}
			processNode.setId(processNodeVo.get(i).getId());
			processNode.setUpdatetime(new Date());
			processNode.setState(dealFlag);
			processNodeMapper.updateByPrimaryKeySelective(processNode);
			//最后一个审核的为管理员
			if(i==processNodeVo.size()&&dealFlag==1){
				process.setId(Integer.parseInt(processdb.getId()));
				process.setUpdatetime(new Date());
				process.setState(dealFlag);
				processMapper.updateByPrimaryKeySelective(process);
			}
			
		}
		if(dealFlag==-2||dealFlag==-1){
			process.setId(Integer.parseInt(processdb.getId()));
			process.setUpdatetime(new Date());
			process.setState(dealFlag);
			processMapper.updateByPrimaryKeySelective(process);
			
		}
		 
		return true;
	}
	@Override
	public boolean editFlow(String flowId, String name, String isBatch, String visibleAll, String authList,
			String nodeList, String userId) {
		Flow flowdb=flowMapper.selectByPrimaryKey(Integer.parseInt(flowId));
		if(null==flowdb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		FlowAuthority fA1=new FlowAuthority();
		fA1.setFlowId(Integer.parseInt(flowId));
		fA1.setUpdatetime(new Date());
		fA1.setState(-1);
		flowAuthorityMapper.updateByFlowIdSelective(fA1);
		FlowNode fN1=new FlowNode();
		fN1.setFlowId(Integer.parseInt(flowId));
		fN1.setUpdatetime(new Date());
		fN1.setState(-1);
		flowNodeMapper.updateByFlowIdSelective(fN1);
		String cUserid="";
		String []authArr=authList.split("\\|");
		String []nodeArr=nodeList.split("\\|");
		Flow flow =new Flow();
		flow.setCreateUserId(Integer.parseInt(userId));
		flow.setIsBatchTranser(Integer.parseInt(isBatch));
		flow.setName(name);
		flow.setId(Integer.parseInt(flowId));
		flow.setVisibleAll(Integer.parseInt(visibleAll));
		flow.setNodeNum(nodeArr.length+1);
		flow.setUpdatetime(new Date());
		flowMapper.updateByPrimaryKeySelective(flow);
		if(Integer.parseInt(visibleAll)==0){
			for(int i=0;i<authArr.length;i++){
				cUserid=authArr[i];
				FlowAuthority fA=new FlowAuthority();
				fA.setFlowId(Integer.parseInt(flowId));
				fA.setUserId(Integer.parseInt(cUserid));
				flowAuthorityMapper.insertSelective(fA);
			}
		}
		int j=0;
		for( j=0;j<nodeArr.length;j++){
			cUserid=nodeArr[j];
			FlowNode fN=new FlowNode();
			fN.setFlowId(Integer.parseInt(flowId));
			fN.setApproverId(Integer.parseInt(cUserid));
			fN.setNo(j+1);
			flowNodeMapper.insertSelective(fN);
		}
		//最后的审批人为管理员
		cUserid=userId;
		FlowNode fN=new FlowNode();
		fN.setFlowId(Integer.parseInt(flowId));
		fN.setApproverId(Integer.parseInt(cUserid));
		fN.setNo(j+1);
		flowNodeMapper.insertSelective(fN);
		return true;
	}
	@Override
	public List<FlowListVo> queryFlows(String teamId, String userId) {
		return flowMapper.getFlowList(userId, Integer.parseInt(teamId));
	}
	@Override
	public boolean delFlow(String flowId, String userId) {
		FlowVo flowdb=flowMapper.getFlowInfo(userId, Integer.parseInt(flowId));
		if(null==flowdb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		Flow flow=new Flow();
		flow.setId(Integer.parseInt(flowId));
		flow.setState(-1);
		flow.setUpdatetime(new Date());
		flowMapper.updateByPrimaryKeySelective(flow);
		return true;
	}
	@Override
	public FlowVo queryFlowById(String flowId, String userId) {
		return flowMapper.getFlowInfo(userId, Integer.parseInt(flowId));
	}
	@Override
	public ProcessVo queryProcessById(String processId, String userId) {
		return processMapper.getProcessInfo(Integer.parseInt(processId),Integer.parseInt(userId) );
	}
	@Override
	public List<ProcessListVo> queryProcess(String teamId, String userId,String fetch,String offset) {
		return processMapper.getProcessList(Integer.parseInt(teamId), Integer.parseInt(fetch)
				, Integer.parseInt(offset), Integer.parseInt(userId));
	}
	@Override
	public List<ProcessListVo> queryApproveProcess(String teamId, String userId,String fetch,String offset,String state) {
		return processMapper.getApproveProcessList(Integer.parseInt(teamId), Integer.parseInt(fetch)
				, Integer.parseInt(offset), Integer.parseInt(userId), Integer.parseInt(state));
	}
	@Override
	public List<FlowListVo> queryUseFlows(String teamId, String userId) {
		MemberVo memberDb = memberMapper.selectByTeamIdUseId(Integer.parseInt(teamId), userId);
		if( null==memberDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		return flowMapper.getUseFlowList(Integer.parseInt(userId), Integer.parseInt(teamId));
	}
	@Override
	public FlowVo queryUseFlowById(String flowId, String userId) {
		Flow flow=flowMapper.selectByPrimaryKey(Integer.parseInt(flowId));
		if( null==flow){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		FlowVo flowVo= flowMapper.getUseFlowInfo(Integer.parseInt(userId), Integer.parseInt(flowId));
		MemberVo memberDb = memberMapper.selectByTeamIdUseId(flow.getTeamId(), userId);
		if( null==memberDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		return flowVo;
	}
	
	
	


}
