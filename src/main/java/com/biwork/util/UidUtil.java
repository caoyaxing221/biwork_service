package com.biwork.util;

import java.util.UUID;

public class UidUtil {

	public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        String uuidStr=str.replace("-", "");
        return uuidStr;
      }
	 public static String getOrderNo(){
		 StringBuffer orderNo=new StringBuffer();
		
		 orderNo.append(PropertiesUtil.getProperty("bank.orderid.prxfix")).append(SerialByFileUtil.get(PropertiesUtil.getProperty("order.no"),false));
		 return orderNo.toString();
	 }
	 public static String getUserNo(){
		 StringBuffer orderNo=new StringBuffer();
		
		 orderNo.append(PropertiesUtil.getProperty("bank.userid.prxfix")).append(SerialByFileUtil.get(PropertiesUtil.getProperty("user.no"),false));
		 return orderNo.toString();
	 }
	 public static void main(String args[]){
		 System.out.println(getUserNo());
	 }
}
