package com.biwork.service;

import java.util.List;

import com.biwork.entity.Currency;
import com.biwork.entity.Service;
import com.biwork.entity.User;
import com.biwork.vo.MeVo;
import com.biwork.vo.TeamVo;

public interface MyService {	 
	Service query();
	User getUser(String userId);
	MeVo getMe(String userId);
	List<Currency> getCurrency();
	List<TeamVo> getApprovalCategoryList(String teamId,String userId);
	List<TeamVo> getDepartmentList(String teamId,String userId);
}
