package com.biwork.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.biwork.entity.*;
import com.biwork.mapper.*;
import com.biwork.po.request.CommitOrderPojo;
import com.biwork.util.DayuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biwork.exception.BusiException;

import com.biwork.service.MyService;
import com.biwork.util.Constants;
import com.biwork.vo.MeVo;
import com.biwork.vo.MemberVo;
import com.biwork.vo.ServiceVo;
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
	@Autowired
	private ConfigurationMapper configurationMapper;
	@Autowired
	private PaymentTypeMapper paymentTypeMapper;
	@Autowired
	private PaymentOrderMapper paymentOrderMapper;
	@Override
	public com.biwork.entity.Service  query() {		 
	    
	    // 查询用户id
		com.biwork.entity.Service service = serviceMapper.selectByPrimaryKey(1);   
	   
	    return service;
	}

	public List<ServiceVo> getServiceList( Integer userId) {
		 List<ServiceVo> service = serviceMapper.selectListByUserId(userId);  
		return service;
	}
	public  com.biwork.entity.Service getService( Integer userId) {
		  com.biwork.entity.Service service = serviceMapper.selectByUserId(userId);  
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
	@Override
	public String getConfiguration(String name) {
		ConfigurationExample configurationExample = new ConfigurationExample();
		ConfigurationExample.Criteria criteria = configurationExample.createCriteria();
		criteria.andNameEqualTo(name);
		criteria.andStateEqualTo(0);
		return configurationMapper.selectByExample(configurationExample).get(0).getValue();
	}
	@Override
	public List<PaymentType> getPurchaseType() {
		PaymentTypeExample paymentTypeExample = new PaymentTypeExample();
		PaymentTypeExample.Criteria criteria = paymentTypeExample.createCriteria();
		criteria.andStateEqualTo(0);
		return paymentTypeMapper.selectByExample(paymentTypeExample);
	}
	@Override
	public List<PaymentOrder> queryOrderList(String userId,String offset,String limit) {
		PaymentOrderExample paymentOrderExample = new PaymentOrderExample();
		paymentOrderExample.setStart(Integer.parseInt(offset));
		paymentOrderExample.setLimit(Integer.parseInt(limit));
		paymentOrderExample.or().andUserIdEqualTo(Integer.parseInt(userId));
		return paymentOrderMapper.selectByExample(paymentOrderExample);
	}
	@Override
	public int queryOrderCount(String userId) {
		PaymentOrderExample paymentOrderExample = new PaymentOrderExample();
		paymentOrderExample.or().andUserIdEqualTo(Integer.parseInt(userId));
		return paymentOrderMapper.countByExample(paymentOrderExample);
	}
	@Override
	public int createOrder(String userId, CommitOrderPojo commitOrderPojo) {
		PaymentOrderExample paymentOrderExample = new PaymentOrderExample();
		paymentOrderExample.or().andOrderNoEqualTo(commitOrderPojo.getOrderNo());
		List<PaymentOrder> paymentOrderList = paymentOrderMapper.selectByExample(paymentOrderExample);
		if( paymentOrderList.size()>=0){
			throw new BusiException(Constants.FAIL_CODE,Constants.ALREADY_EXISTS);
		}
		PaymentTypeExample paymentTypeExample = new PaymentTypeExample();
		PaymentTypeExample.Criteria pCriteria = paymentTypeExample.createCriteria();
		pCriteria.andStateEqualTo(0);
		pCriteria.andIdEqualTo(Integer.parseInt(commitOrderPojo.getPurchaseTypeId()));
		List<PaymentType> paymentTypeList = paymentTypeMapper.selectByExample(paymentTypeExample);
		if( paymentTypeList.size()==0){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		PaymentType paymentType=paymentTypeList.get(0);
		ConfigurationExample configurationExample = new ConfigurationExample();
		ConfigurationExample.Criteria cCriteria = configurationExample.createCriteria();
		cCriteria.andStateEqualTo(0);
		cCriteria.andNameEqualTo("receiveCoinAddress");
		String receiveCoinAddress=configurationMapper.selectByExample(configurationExample).get(0).getValue();

		PaymentOrder order=new PaymentOrder();
		order.setInsertTime(new Date());
		order.setUserId(Integer.parseInt(userId));
		order.setOrderNo(commitOrderPojo.getOrderNo());
		order.setPaymentDiscount(paymentType.getDiscount());
		order.setPaymentId(paymentType.getId());
		order.setPaymentMoney(paymentType.getMoney());
		order.setPaymentName(paymentType.getName());
		order.setPaymentUnit(paymentType.getUnit());
		order.setReceiveCoinAddress(receiveCoinAddress);
		order.setUserCoinAddress(commitOrderPojo.getUserCoinAddress());
		return paymentOrderMapper.insertSelective(order);

	}
	public List<Version> getCurrentVersionBoth() {
		 List<Version> version = versionMapper.selectBoth();
		return version;
	}
}
