package com.biwork.service;

import com.biwork.entity.User;

public interface LoginService {

	 User login(String phone,String password) throws Exception;
	 void loginout(String userid);
	 boolean forgetPassword(String phone,String password) throws Exception;
	 User checkAccountByPhone(String phone)throws Exception;
	 boolean register(String phone,String password)throws Exception;
	User loginOrRegist(String phone,String inviteCode,String name);
	User loginByUid(Integer uid) throws Exception;
	User invite(String phone, String inviteCode, String name);
}
