package com.biwork.service.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biwork.entity.User;
import com.biwork.entity.VerifyCode;
import com.biwork.exception.BusiException;
import com.biwork.mapper.UserMapper;
import com.biwork.mapper.VerifyCodeMapper;
import com.biwork.service.VerifyCodeService;
import com.biwork.util.Constants;
import com.biwork.util.DayuUtil;
/**
 * 
* @ClassName: VerifyCodeServiceImpl 
* @Description: 生成及校验验证码类
* @author cyx
* @date 2018年8月17日
*
 */
@Service("verifyCodeService")
public class VerifyCodeServiceImpl implements VerifyCodeService {

	//TODO 短信频次限制
	static Logger log = LoggerFactory.getLogger(VerifyCodeService.class);
	@Autowired
	private VerifyCodeMapper verifyCodeMapper;
	@Autowired
	private UserMapper accoutMapper;
	@Override
	public boolean getCode(String phone, String type)throws Exception {
		if("register".equals(type)){
			User account = accoutMapper.selectByPhonePassword(phone,null);
		    if(account!=null&&account.getPassword()!=null){
		    	throw new BusiException(Constants.FAIL_CODE,Constants.ACCOUNT_AlREAD_EXISTS);
		    }
		}
		
		Date date = new Date();//获得系统时间.
    	SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        String nowTime = sdf.format(date);
        Date time = null;
		try {
			time = sdf.parse( nowTime );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//设置超时时间
		Date date1=new Date(System.currentTimeMillis() +30*60*1000);
		String expireTime = sdf.format(date1);
        Date expTime = null;
		try {
			expTime = sdf.parse( expireTime );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String code=DayuUtil.getRandomNumber(6);
		VerifyCode vCode=new VerifyCode();
		vCode.setPhone(phone);
		vCode.setCode(code);
		vCode.setType(type);
		
		vCode.setState(0);
		vCode.setInserttime(time);
		vCode.setExpiretime(date1);
		verifyCodeMapper.insert(vCode);
		DayuUtil.sendSms(code, phone);
		return true;
	}

	@Override
	public boolean verifyCode(String phone, String code, String type) {
		
		Date date = new Date();//获得系统时间.
    	SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        String nowTime = sdf.format(date);
        Date time = null;
		try {
			time = sdf.parse( nowTime );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		VerifyCode vCode=verifyCodeMapper.selectByCode(phone,code,type,time);
		if (vCode==null){
			return false;
//			return true;
		}
		VerifyCode vCode1=new VerifyCode();
		
		vCode1.setState(1);
		vCode1.setId(vCode.getId());
		vCode1.setUpdatetime(time);
		verifyCodeMapper.updateByPrimaryKeySelective(vCode1);
		return true;
	}

}
