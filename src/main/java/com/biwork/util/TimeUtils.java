package com.biwork.util;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeUtils {
	/**
	 * 
	 * 方法描述：获得当前日期 格式yyyyMMdd
	 * 创建人：huangxue
	 * 创建时间：2017-3-14 下午4:12:42
	 * @return: String
	 * 修改备注：
	 * @version
	 */
	public static String getDate8(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
		return simpleDateFormat.format(new Date());
	}
	/**
	 * 方法描述：获得当前时间 格式HHmmss
	 * 创建人：huangxue
	 * 创建时间：2017-3-14 下午4:13:51
	 * @return: String
	 * 修改备注：
	 * @version
	 */
	public static String getTime(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HHmmss");
		return simpleDateFormat.format(new Date());
	}
	
	public static String getTime(SimpleDateFormat sdfTarget,SimpleDateFormat sdfOrgn,String date) throws ParseException{
		return sdfOrgn.format(sdfTarget.parse(date));
	}
	
	/**
	 * 
	 * 方法描述：获得当前时间 格式yyyyMMddHHmmss
	 * 创建人：huangxue
	 * 创建时间：2017-4-17 下午9:44:43
	 * @return: String
	 * 修改备注：
	 * @version
	 */
	public static String getDate14(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		return simpleDateFormat.format(new Date());
	}
	
	public static String getDateTimeString(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date());
	}
	public static Date getDateTime() throws ParseException{
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.parse(simpleDateFormat.format(new Date()));
	}
	
	public static Date getDateTime(String datetimeStr) throws ParseException{
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.parse(datetimeStr);
	}
	/**
	 * 
	 * 方法描述：获得两个日期的月份差  格式yyyy-MM
	 * 创建人：caoyaxing
	 * 创建时间：2017年10月24日 19:45:36
	 * @return: int
	 * 修改备注：
	 * @version
	 */
	 public static int getMonthDiff(String startDateStr,String endDateStr) throws ParseException {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	        //String str1 = "2012-02";
	        //String str2 = "2010-01";
	        Calendar bef = Calendar.getInstance();
	        Calendar aft = Calendar.getInstance();
	        bef.setTime(sdf.parse(startDateStr));
	        aft.setTime(sdf.parse(endDateStr));
	      
	       // System.out.println(  aft.get(Calendar.YEAR));
	        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
	        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
	        return month + result;   
	    }
	 public static long getStartTime(){  
		    Calendar todayStart = new GregorianCalendar();
		    TimeZone zone = TimeZone.getTimeZone("GMT+8:00"); 
		    todayStart.setTimeZone(zone);
		    todayStart.set(Calendar.HOUR_OF_DAY, 0);  
		    todayStart.set(Calendar.MINUTE, 0);  
		    todayStart.set(Calendar.SECOND, 0);  
		    todayStart.set(Calendar.MILLISECOND, 0);  
		    //System.out.println("今天起始时间： "+todayStart.getTime());
		    return todayStart.getTime().getTime();  
		}
	 public static long getYesterdayStartTime(){  
		 Calendar yesterdayStart=Calendar.getInstance();
		 yesterdayStart.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		 yesterdayStart.add(Calendar.DATE,-1);
		 yesterdayStart.set(Calendar.HOUR_OF_DAY, 0);
		 yesterdayStart.set(Calendar.MINUTE, 0);  
		 yesterdayStart.set(Calendar.SECOND, 0);  
		 yesterdayStart.set(Calendar.MILLISECOND, 0);
		 Date time=yesterdayStart.getTime();
		     //System.out.println("昨天开始时间： "+yesterdayStart.getTime());
		     return yesterdayStart.getTime().getTime();
		     
		 } 
	 public static long getStringDate(String strDate){  
		  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
	        //必须捕获异常
	        try {
	            Date date=simpleDateFormat.parse(strDate);
	           return date.getTime();
	        } catch(ParseException px) {
	            px.printStackTrace();
	            return 0;
	        }
		     
		 } 
	 public static void main(String[] args) throws ParseException {
		System.out.println(getDate8());  
	    }
	 
	 
	
}
