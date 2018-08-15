package com.biwork.util;

import java.util.Random;

import com.biwork.entity.SMSParam;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * @Description:发送短信消息的类
 *
 */
public class DayuUtil {
	
	private static String url = "https://eco.taobao.com/router/rest";
//	private static String appkey = "23599041";
//	private static String secret = "48840f984500cccdd8ecab05dc6d6014";
	
	private static String appkey ="23716017";
	private static String secret ="59d4f3fd7463dac5a200cd5af1859789";

	private static TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
	
	/**
	 * 
	 * @param para 
	 * @param mobiles 
	 * @return
	 */
	public static String sendSms(String para, String mobiles, String templateCode){
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName("smartshare官方");
		req.setRecNum(mobiles);
		req.setSmsTemplateCode(templateCode);
		req.setSmsParamString(para);
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			return rsp.getBody();
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String sendSms(String code, String mobiles){
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend( "" );
		req.setSmsType("normal");
		req.setSmsFreeSignName("smartshare官方");
		String para = "{\"code\":\""+ code +"\",\"product\":\"快惠钱包\"}";
		req.setRecNum(mobiles);
		req.setSmsTemplateCode("SMS_57285001");
		req.setSmsParamString(para);
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			return rsp.getBody();
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String sendSms(SMSParam smsParam)
	{
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest(); 
		req.setExtend(""); 
		req.setSmsType(smsParam.getSmsType()); 
		req.setSmsFreeSignName(smsParam.getSmsFreeSignName()); 
		req.setSmsParamString(smsParam.getSmsParamString()); 
		req.setRecNum(smsParam.getRecPhone()); 
		req.setSmsTemplateCode(smsParam.getSmsTemplateCode()); 
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try
		{
			rsp = client.execute(req);
			
		} catch (ApiException e)
		{
			e.printStackTrace();
		} 
		return rsp.getBody();
	}
	
	/**
	 * 生成一个随机数字的字符串，number表示几位数字
	 * @param number
	 * @return 数字字符串
	 */
	public  static String getRandomNumber(int number)
	{
		Random rand = new Random();
		// Math.pow(10, number)-1会得到number位数字的最大值，例如为3时，会得到9999
		int [] nums = new int[number];
		String randNum = "";
		for(int i=0;i<nums.length;i++)
		{
			nums[i] = rand.nextInt(10);
			randNum = randNum + nums[i];
		}
		return randNum;
	}
	
	public static void main(String[] args) {
		/*String randNum = getRandomNumber(6);
		// 注册验证码
		String para = "{\"code\":\""+ randNum +"\",\"product\":\"快惠钱包\"}";
//		String mobiles = "18351642207";
		String mobiles = "18210406652";
		SMSParam  sms = new SMSParam();
		sms.setSmsType("normal");
		sms.setSmsFreeSignName("LinkEye官方");
		sms.setRecPhone(mobiles);
//		sms.setSmsTemplateCode(VerifyCodeConstants.REGISTER_VERIFY_CODE);
		sms.setSmsTemplateCode("SMS_57285001");
		sms.setSmsParamString(para);
		String responseMsg = sendSms(sms);*/
	    String randomNumber = getRandomNumber(4);
		String req=sendSms(randomNumber,"18633060826");
		System.out.println(req);
//		System.out.println(responseMsg);
		// 添加提现到银行卡
		//String para1 = "{\"code\":\"4321\"}";
//		DayuUtil.sendSms(para1, mobiles, "SMS_43060003");
		
		/// 找回密码
		//String para2 = "{\"code\":\"4321\",\"acountType\":\"大财神\"}";
//		DayuUtil.sendSms(para2, mobiles, "SMS_43300047");	
	}
}
