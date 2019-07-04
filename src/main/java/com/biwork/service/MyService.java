package com.biwork.service;

import java.util.List;

import com.biwork.entity.*;
import com.biwork.po.request.CommitOrderPojo;
import com.biwork.vo.MeVo;
import com.biwork.vo.ServiceVo;
import com.biwork.vo.TeamVo;

public interface MyService {	 
	Service query();
	List<ServiceVo> getServiceList( Integer userId);
	Service getService( Integer userId);
	User getUser(String userId);
	MeVo getMe(String userId);
	List<Currency> getCurrency();
	List<TeamVo> getApprovalCategoryList(String teamId,String userId);
	List<TeamVo> getDepartmentList(String teamId,String userId);
	Version getCurrentVersion(String type);

	String getConfiguration(String name);

	List<PaymentType> getPurchaseType();

	List<PaymentOrder> queryOrderList(String userId,String offset, String limit);

	int queryOrderCount(String userId);

	int createOrder(String userId, CommitOrderPojo commitOrderPojo);

	List<Version> getCurrentVersionBoth();
}
