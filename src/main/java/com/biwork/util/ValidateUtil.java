package com.biwork.util;

import java.util.regex.Pattern;
 
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
    public static final String REGEX_MOBILE = "^1[34578]\\d{9}$";
 
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
        String username = "fdsdfsdj";
        System.out.println(ValidateUtil.isUsername(username));
        System.out.println(ValidateUtil.isChinese(username));
    }
  //金额验证  
    public static boolean isMoney(String str){   
        return  Pattern.matches(REGEX_MONEY,str);
     }  
}
