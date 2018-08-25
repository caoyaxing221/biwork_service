package com.biwork.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biwork.mapper.ServiceMapper;
import com.biwork.exception.BusiException;

import com.biwork.service.MyService;
import com.biwork.util.Constants;



@Service("myService")
public class MyServiceImpl implements MyService {
	static Logger log = LoggerFactory.getLogger(MyService.class);
	
	@Autowired
	private ServiceMapper serviceMapper;

	
	
	@Override
	public com.biwork.entity.Service  query() {		 
	    
	    // 查询用户id
		com.biwork.entity.Service service = serviceMapper.selectByPrimaryKey(1);   
	   
	    return service;
	}
	


}
