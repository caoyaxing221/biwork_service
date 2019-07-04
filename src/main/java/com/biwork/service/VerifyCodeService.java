package com.biwork.service;

public interface VerifyCodeService {

	
	boolean verifyCode(String phone,String code,String type);
	boolean getCode(String phone, String type) throws Exception;
}
