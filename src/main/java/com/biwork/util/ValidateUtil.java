package com.biwork.util;

import java.util.regex.Pattern;

import org.web3j.utils.Numeric;
 
/**
 * 校验器：利用正则表达式校验邮箱、手机号等
 * 
 * @author liujiduo
 * 
 */
public class ValidateUtil {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";
 
    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9_]{6,16}$";
 
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1[3456789]\\d{9}$";
 
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
 
    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
 
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
 
    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
 
    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    /**
     * 正则表达式：验证金额 
     */
    public static final String REGEX_MONEY = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";
    /**
     * 正则表达式：验证日期 
     */
    public static final String REGEX_DATETIME = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[13579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
    
    /**
     * 正则表达式：验证图片链接 
     */
    public static final String REGEX_IMG="(?i)\\.(gif|png|jpe?g)";
//    以太坊和ERC20地址长度42个字节，前面2个字节是0x, 由大小字母(a-f/A-F)和数字(0-9)组合而成
//    比特币地址长度是33字节， 由字母（a-z/A-Z)和数字（0-9）组成，目前biwork支持 1 开头的地址
    /**
     * 正则表达式：验证ERC20地址
     */
    public static final String ADDRESS_ERC20 = "^[a-fA-F0-9]{40}$";
    /**
     * 正则表达式：验证BTC地址
     */
    public static final String ADDRESS_BTC = "^[a-zA-Z0-9]{34}$";
    /**
     * 校验用户名
     * 
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }
 
    /**
     * 校验密码
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
 
    /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
 
    /**
     * 校验邮箱
     * 
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
 
    /**
     * 校验汉字
     * 
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }
 
    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }
 
    /**
     * 校验URL
     * 
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }
 
    /**
     * 校验IP地址
     * 
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }
    /** 
     * 校验银行卡卡号 
     * 
     * @param cardId 
     * @return 
     */
    public static boolean checkBankCard(String cardId) { 
      char bit = getBankCardCheckCode(cardId 
          .substring(0, cardId.length() - 1)); 
      return cardId.charAt(cardId.length() - 1) == bit; 
    } 
    /** 
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位 
     * 
     * @param nonCheckCodeCardId 
     * @return 
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) { 
      int cardLenth = nonCheckCodeCardId.trim().length(); 
      if (nonCheckCodeCardId == null || cardLenth == 0
          || !nonCheckCodeCardId.matches("\\d+")) { 
        throw new IllegalArgumentException("不是银行卡的卡号!"); 
      } 
      char[] chs = nonCheckCodeCardId.trim().toCharArray(); 
      int luhmSum = 0; 
      for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) { 
        int k = chs[i] - '0'; 
        if (j % 2 == 0) { 
          k *= 2; 
          k = k / 10 + k % 10; 
        } 
        luhmSum += k; 
      } 
      return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0'); 
    }
    public static void main(String[] args) {
        String username = "2018-09-02";
//        System.out.println(ValidateUtil.isIMG("http://10.23.1.99:8988/static/img/201811051139164848113.exe"));
       // System.out.println(ValidateUtil.isChinese(username));
        System.out.println( ValidateUtil.isAddress("0xaa97824094faa3f47bc8da82d13880a015d3df1e"));
        System.out.println(ValidateUtil.isAddress("1C6UYrmbtvdi8dHZNnZD3YoVwit2mccSgw"));
    }
  //金额验证  
    public static boolean isMoney(String str){   
        return  Pattern.matches(REGEX_MONEY,str);
     }  
    //时间验证  
    public static boolean isDateTime(String str){   
        return  Pattern.matches(REGEX_DATETIME,str);
     } 
  //图片链接验证  
    public static boolean isIMG(String str){   
    	String type;
    	  type=str.indexOf(".")!=-1?str.substring(str.lastIndexOf(".")+1, str.length()):null;
          if (type!=null) {// 判断文件类型是否为空
              if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
            	 return true;
              }else {
            	 return false;
                  
              }
          }else{
        	  return false;
          }
     } 
    public static boolean isAddress(String str){   
    	if((str.startsWith("1")&&Pattern.matches(ADDRESS_BTC,str))||(str.startsWith("0x")&&Pattern.matches(ADDRESS_ERC20,Numeric.cleanHexPrefix(str)))){
    		return true;
    	}else{
    		return false;
    	}
     } 
}
