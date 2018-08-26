package com.biwork.service;

import com.biwork.entity.Service;
import com.biwork.entity.User;
import com.biwork.vo.MeVo;

public interface MyService {	 
	Service query();
	User getUser(String userId);
	MeVo getMe(String userId);
}
