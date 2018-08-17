package com.biwork.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.biwork.mapper.UserMapper;
import com.biwork.entity.User;
import com.biwork.exception.BusiException;

import com.biwork.service.LoginService;

import com.biwork.util.Constants;



@Service("loginService")
public class LoginServiceImpl implements LoginService {
	static Logger log = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private UserMapper accoutMapper;

	
	
	@Override
	public User login(String phone, String password) throws Exception{
		
	    
		 
	    
	    // 查询用户id
		User account = accoutMapper.selectByPhonePassword(phone,password);
		if(account==null){
	    	throw new BusiException(Constants.FAIL_CODE,Constants.ACCOUNT_MAY_NOT_FOUND);
	    }
	    
	    
	   
	    
	    
	   
	    return account;
	}
	@Override
	public User loginByUid(Integer uid) throws Exception{
		
	    
		 
	    
	    // 查询用户id
		User account = accoutMapper.selectByPrimaryKey(uid);
		if(account==null){
	    	throw new BusiException(Constants.FAIL_CODE,Constants.ACCOUNT_MAY_NOT_FOUND);
	    }
	    
	    
	   
	    
	    
	   
	    return account;
	}
	@Override
	public void loginout(String userid) {
		
	}

	@Override
	public boolean forgetPassword(String phone, String password) throws Exception{
		// 查询用户id
	    User account = accoutMapper.selectByPhonePassword(phone,null);
	    if(account==null){
	    	throw new BusiException(Constants.FAIL_CODE,Constants.ACCOUNT_NOT_FOUND);
	    }
	    User account2=new User();
	    account2.setId(account.getId());
	    account2.setPassword(password);
	    Date date = new Date();//获得系统时间.
    	SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
	    String nowTime = sdf.format(date);
        Date time = null;
	
		time = sdf.parse( nowTime );
		account2.setUpdatetime(time);
	    int updateCount =accoutMapper.updateByPrimaryKeySelective(account2);
		return true;
	}

	@Override
	public User checkAccountByPhone(String phone)throws Exception {
		  // 查询用户id
		User account = accoutMapper.selectByPhonePassword(phone,null);
	    if(account==null){
	    	throw new BusiException(Constants.FAIL_CODE,Constants.ACCOUNT_NOT_FOUND);
	    }
	    return account;
	}

	@Override
	public boolean register(String phone, String password) throws Exception{
		// 查询用户id
		User account = accoutMapper.selectByPhonePassword(phone,null);
	    if(account!=null&&account.getPassword()!=null){
	    	throw new BusiException(Constants.FAIL_CODE,Constants.ACCOUNT_AlREAD_EXISTS);
	    }
	    
	  
	   
	   
	    Date nowTime=new Date();
	    
	    if(account==null){
	    	User account_new=new User();  		  
	    		  
	   	    account_new.setInserttime(nowTime);
	   	    account_new.setPassword(password);
	   	    account_new.setPhone(phone);

	   	    accoutMapper.insertSelective(account_new);
		   }else{
			   User account_new=new User();  		  
	    		  
		   	    account_new.setId(account.getId());
		   	    account_new.setPassword(password);
		   	    account_new.setUpdatetime(nowTime);

		   	    accoutMapper.updateByPrimaryKeySelective(account_new);
		   }
	    
	 
	    return true;
	}
	
	@Override
	public User loginOrRegist(String phone) {
		// TODO 查询商户有无账号，有则直接查询返回，无则创建并返回
		User account = accoutMapper.selectByPhonePassword(phone,null);
		if(account != null){
			return account;
		}
		Date nowTime=new Date();
	    User account_new=new User();
	    account_new.setInserttime(nowTime);
	    account_new.setPhone(phone);

	    accoutMapper.insertSelective(account_new);
	    account_new = accoutMapper.selectByPhonePassword(phone,null);
	    return account_new;
		
	}


}
