package com.biwork.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biwork.mapper.MemberInviteMapper;
import com.biwork.mapper.MemberMapper;
import com.biwork.mapper.UserMapper;
import com.biwork.entity.Member;
import com.biwork.entity.MemberInvite;
import com.biwork.entity.User;
import com.biwork.exception.BusiException;

import com.biwork.service.LoginService;
import com.biwork.util.AESUtil;
import com.biwork.util.Constants;
import com.biwork.util.PropertiesUtil;
import com.biwork.util.UidUtil;
import com.biwork.vo.MemberVo;
import com.taobao.api.internal.toplink.embedded.websocket.util.StringUtil;



@Service("loginService")
public class LoginServiceImpl implements LoginService {
	static Logger log = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private UserMapper accoutMapper;
	@Autowired
	MemberInviteMapper memberInviteMapper;
	@Autowired
	MemberMapper memberMapper;
	
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
	   	    account_new.setToken(UidUtil.getUUID());
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
	public User loginOrRegist(String phone,String inviteCode,String name) {
		// TODO 查询商户有无账号，有则直接查询返回，无则创建并返回
		User account = accoutMapper.selectByPhonePassword(phone,null);
		if(account != null){
			//不考虑已经注册成功通过邀请链接登录的
			return account;
		}
		Date nowTime=new Date();
	    User account_new=new User();
	    account_new.setInserttime(nowTime);
	    account_new.setPhone(phone);
	    account_new.setToken(UidUtil.getUUID());
	    accoutMapper.insertSelective(account_new);
	    account_new = accoutMapper.selectByPhonePassword(phone,null);
//	    if(!StringUtils.isBlank(inviteCode)&&!StringUtils.isBlank(name)){
//	    	String inviteInfo="";
//	    	try {
//				 inviteInfo = AESUtil.AESDncode(PropertiesUtil.getProperty("aeskey"), inviteCode);
//			} catch (Exception e) {
//				throw new BusiException(Constants.FAIL_CODE,e.getMessage());
//			}
//	    	String userId=inviteInfo.split("\\|")[1];
//	    	String teamId=inviteInfo.split("\\|")[0];
//	    	//插入同意加入团队以及团队邀请记录
//	    	MemberVo mVo=memberInviteMapper.selectByPhone(phone,Integer.parseInt(teamId));
//	    	MemberInvite minvite=new MemberInvite();
//	    	if (null!=mVo){
//	    		
//				minvite.setInviterId(Integer.parseInt(userId));
//				minvite.setName(name);
//				minvite.setPhone(phone);
//				minvite.setTeamId(Integer.parseInt(teamId));
//				minvite.setState(1);
//				 memberInviteMapper.insertSelective(minvite);
//	    	}
//	    	
//			 Member member=new Member();
//				member.setInviterId(null==mVo?minvite.getId():Integer.parseInt(mVo.getId()));
//				member.setName(name);
//				member.setPhone(phone);
//				member.setTeamId(Integer.parseInt(teamId));
//				member.setUserId(account_new.getId());
//				memberMapper.insertSelective(member);
//				// 如果没有默认团队，设为默认
//				if(null==account_new.getDefaultTeamId()){
//					User user=new User();
//					user.setId(account_new.getId());
//					user.setDefaultTeamId(Integer.parseInt(teamId));
//					user.setUpdatetime(new Date());
//					accoutMapper.updateByPrimaryKeySelective(user);
//				}
//	    }
	    return account_new;
		
	}
	@Override
	public User invite(String phone,String inviteCode,String name) {
		// TODO 查询商户有无账号，有则直接查询返回，无则创建并返回
		User account = accoutMapper.selectByPhonePassword(phone,null);
		if(account != null){
			//不考虑已经注册成功通过邀请链接登录的
			return account;
		}
		Date nowTime=new Date();
	    User account_new=new User();
	    account_new.setInserttime(nowTime);
	    account_new.setPhone(phone);
	    account_new.setToken(UidUtil.getUUID());
	    accoutMapper.insertSelective(account_new);
	    account_new = accoutMapper.selectByPhonePassword(phone,null);
	    if(!StringUtils.isBlank(inviteCode)&&!StringUtils.isBlank(name)){
	    	String inviteInfo="";
	    	try {
				 inviteInfo = AESUtil.AESDncode(PropertiesUtil.getProperty("aeskey"), inviteCode);
			} catch (Exception e) {
				throw new BusiException(Constants.FAIL_CODE,e.getMessage());
			}
	    	String userId=inviteInfo.split("\\|")[1];
	    	String teamId=inviteInfo.split("\\|")[0];
	    	//插入同意加入团队以及团队邀请记录
	    	MemberVo mVo=memberInviteMapper.selectByPhone(phone,Integer.parseInt(teamId));
	    	MemberInvite minvite=new MemberInvite();
	    	if (null==mVo){
	    		
				minvite.setInviterId(Integer.parseInt(userId));
				minvite.setName(name);
				minvite.setPhone(phone);
				minvite.setTeamId(Integer.parseInt(teamId));
				minvite.setState(1);
				 memberInviteMapper.insertSelective(minvite);
	    	}
	    	MemberVo mVo2=memberMapper.selectByPhone(phone,Integer.parseInt(teamId));
	    	
	    	if (null==mVo2){
	    		
	    		Member member=new Member();
				member.setInviterId(Integer.parseInt(userId));
				member.setName(name);
				member.setPhone(phone);
				member.setTeamId(Integer.parseInt(teamId));
				member.setUserId(account_new.getId());
				member.setInviteTableId(mVo==null?minvite.getId():Integer.parseInt(mVo.getId()));
				memberMapper.insertSelective(member);
				// 如果没有默认团队，设为默认
				if(null==account_new.getDefaultTeamId()){
					User user=new User();
					user.setId(account_new.getId());
					user.setDefaultTeamId(Integer.parseInt(teamId));
					user.setUpdatetime(new Date());
					accoutMapper.updateByPrimaryKeySelective(user);
				}
	    	}
			 
	    }
	    return account_new;
		
	}

}
