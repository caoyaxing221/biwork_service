package com.biwork.util;

import java.util.Random;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.biwork.entity.SMSParam;


/**
 * @Description:发送短信消息的类
 *
 */
public class DayuUtil {
	
	private static String url = "https://eco.taobao.com/router/rest";
//	private static String appkey = "23599041";
//	private static String secret = "48840f984500cccdd8ecab05dc6d6014";
	
	private static String appkey ="LTAInEIHUeTavgHp";
	private static String secret ="3Bw6582oOjum5H2SZc1pUPzMQiv0Vn";

	
	
	public static String sendSms(String code, String mobiles){
		//设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		//初始化ascClient需要的几个参数
		final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		//替换成你的AK
		final String accessKeyId =appkey;//你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = secret;//你的accessKeySecret，参考本文档步骤2
		//初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
		accessKeySecret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IAcsClient acsClient = new DefaultAcsClient(profile);
		 //组装请求对象
		 SendSmsRequest request = new SendSmsRequest();
		 //使用post提交
		 request.setMethod(MethodType.POST);
		 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
		 request.setPhoneNumbers(mobiles);
		 //必填:短信签名-可在短信控制台中找到
		 request.setSignName("BIWORK");
		 //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
		 request.setTemplateCode("SMS_150577633");
		 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		 request.setTemplateParam( "{\"code\":\""+ code +"\",\"product\":\"BIWORK\"}");
		 //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		 //request.setSmsUpExtendCode("90997");
		 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		 request.setOutId("yourOutId");
		//请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = null;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
		//请求成功
			return "OK";
		}
		return sendSmsResponse.toString();
	}
	public static String teamAddSms(String name,String inviterName,String teamName, String mobiles){
		//设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		//初始化ascClient需要的几个参数
		final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		//替换成你的AK
		final String accessKeyId =appkey;//你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = secret;//你的accessKeySecret，参考本文档步骤2
		//初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
		accessKeySecret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IAcsClient acsClient = new DefaultAcsClient(profile);
		 //组装请求对象
		 SendSmsRequest request = new SendSmsRequest();
		 //使用post提交
		 request.setMethod(MethodType.POST);
		 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
		 request.setPhoneNumbers(mobiles);
		 //必填:短信签名-可在短信控制台中找到
		 request.setSignName("BIWORK");
		 //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
		 request.setTemplateCode("SMS_151768147");
		 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		 request.setTemplateParam( "{\"name\":\""+ name +"\",\"code\":\""+ inviterName +"\",\"team\":\""+ teamName +"\",\"product\":\"BIWORK\"}");
		 //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		 //request.setSmsUpExtendCode("90997");
		 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		 request.setOutId("yourOutId");
		//请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = null;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
		//请求成功
			return "OK";
		}
		return sendSmsResponse.toString();
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
//		String req=sendSms(randomNumber,"18611348220");
	    String req=teamAddSms("曹亚星", "杨亮", "吃羊肉", "18633060826");
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
