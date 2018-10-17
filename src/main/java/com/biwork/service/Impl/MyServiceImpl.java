package com.biwork.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biwork.mapper.ApprovalCategoryMapper;
import com.biwork.mapper.CurrencyMapper;
import com.biwork.mapper.DepartmentMapper;
import com.biwork.mapper.MemberMapper;
import com.biwork.mapper.ServiceMapper;
import com.biwork.mapper.UserMapper;
import com.biwork.mapper.VersionMapper;
import com.biwork.entity.Currency;
import com.biwork.entity.User;
import com.biwork.entity.Version;
import com.biwork.exception.BusiException;

import com.biwork.service.MyService;
import com.biwork.util.Constants;
import com.biwork.vo.MeVo;
import com.biwork.vo.MemberVo;
import com.biwork.vo.TeamVo;



@Service("myService")
public class MyServiceImpl implements MyService {
	static Logger log = LoggerFactory.getLogger(MyService.class);
	
	@Autowired
	private ServiceMapper serviceMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CurrencyMapper currencyMapper;
	@Autowired
	private ApprovalCategoryMapper approvalCategoryMapper;
	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private VersionMapper versionMapper;
	@Override
	public com.biwork.entity.Service  query() {		 
	    
	    // 查询用户id
		com.biwork.entity.Service service = serviceMapper.selectByPrimaryKey(1);   
	   
	    return service;
	}



	@Override
	public User getUser(String userId) {
		
		return userMapper.selectByPrimaryKey(Integer.parseInt(userId));
	}



	@Override
	public MeVo getMe(String userId) {
		return userMapper.getUserInfo(Integer.parseInt(userId));
	}
	@Override
	public List<Currency> getCurrency() {
		return currencyMapper.selectCurrencys();
	}
	@Override
	public List<TeamVo> getApprovalCategoryList(String teamId,String userId) {
		MemberVo memberDb = memberMapper.selectByTeamIdUseId(Integer.parseInt(teamId), userId);
		if( null==memberDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		return approvalCategoryMapper.selectApprovalCategoryList(Integer.parseInt(teamId));
	}
	@Override
	public List<TeamVo> getDepartmentList(String teamId,String userId) {
		MemberVo memberDb = memberMapper.selectByTeamIdUseId(Integer.parseInt(teamId), userId);
		if( null==memberDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		return departmentMapper.selectDepartmentList(Integer.parseInt(teamId));
	}
	@Override
	public Version getCurrentVersion(String type) {
		Version version = versionMapper.selectByType(type);
		return version;
	}
}
