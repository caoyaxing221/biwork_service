package com.biwork.entity;

/**
 * 短信参数
 * @author wangruidong
 *
 */
public class SMSParam
{
	// 短信的类型，默认为normal
	private String smsType;
	// 短信的签名
	private String smsFreeSignName;
	// 短信的参数
	private String smsParamString;
	// 收短信的手机号
	private String recPhone;
	/**
	 * 短信的内容模板
	 * @see VerifyCodeConstants
	 */
	private String smsTemplateCode;

	public String getSmsType()
	{
		return smsType;
	}

	public void setSmsType(String smsType)
	{
		this.smsType = smsType;
	}

	public String getSmsFreeSignName()
	{
		return smsFreeSignName;
	}

	public void setSmsFreeSignName(String smsFreeSignName)
	{
		this.smsFreeSignName = smsFreeSignName;
	}

	public String getSmsParamString()
	{
		return smsParamString;
	}

	public void setSmsParamString(String smsParamString)
	{
		this.smsParamString = smsParamString;
	}

	public String getRecPhone()
	{
		return recPhone;
	}

	public void setRecPhone(String recPhone)
	{
		this.recPhone = recPhone;
	}

	public String getSmsTemplateCode()
	{
		return smsTemplateCode;
	}

	public void setSmsTemplateCode(String smsTemplateCode)
	{
		this.smsTemplateCode = smsTemplateCode;
	}
}
