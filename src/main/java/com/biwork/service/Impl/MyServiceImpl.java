package com.biwork.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biwork.mapper.ServiceMapper;
import com.biwork.mapper.UserMapper;
import com.biwork.entity.User;
import com.biwork.exception.BusiException;

import com.biwork.service.MyService;
import com.biwork.util.Constants;
import com.biwork.vo.MeVo;



@Service("myService")
public class MyServiceImpl implements MyService {
	static Logger log = LoggerFactory.getLogger(MyService.class);
	
	@Autowired
	private ServiceMapper serviceMapper;
	@Autowired
	private UserMapper userMapper;
	
	
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
	


}
