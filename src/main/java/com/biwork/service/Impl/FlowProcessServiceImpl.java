package com.biwork.service.Impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biwork.entity.Flow;
import com.biwork.entity.FlowAuthority;
import com.biwork.entity.FlowNode;
import com.biwork.entity.Team;
import com.biwork.exception.BusiException;
import com.biwork.mapper.FlowAuthorityMapper;
import com.biwork.mapper.FlowMapper;
import com.biwork.mapper.FlowNodeMapper;
import com.biwork.mapper.TeamMapper;
import com.biwork.service.FlowProcessService;
import com.biwork.util.Constants;
import com.biwork.vo.FlowListVo;
import com.biwork.vo.FlowVo;



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
	
	
	
	


}
