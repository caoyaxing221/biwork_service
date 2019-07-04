package com.biwork.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biwork.entity.Service;
import com.biwork.entity.User;
import com.biwork.po.RespPojo;
import com.biwork.po.UserPojo;
import com.biwork.service.MyService;
import com.biwork.util.AESUtil;
import com.biwork.util.Constants;
import com.biwork.util.JwtUtil;
import com.biwork.util.PropertiesUtil;


public class LoginInterceptor implements HandlerInterceptor{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	MyService myService;
	
	public final static List<String> WHITE_LIST=new LinkedList<String>();
//	static {
//		
//		WHITE_LIST.add(PropertiesUtil.getProperty("wx.notifyRetUrl"));
//		WHITE_LIST.add(PropertiesUtil.getProperty("collectInfoUrl"));
//		WHITE_LIST.add(PropertiesUtil.getProperty("collectInfoUrl2"));
//		WHITE_LIST.add(PropertiesUtil.getProperty("collectInfoUrl3"));
//		
//		
//	}
//	public static void main(String args[]){
//		 System.out.println(WHITE_LIST.indexOf("http://15830i58z2.51mypc.cn/nocard_weixin/trade/orderCallback"));
//	}
	/** 
     * Handler执行之前调用这个方法 
     */ 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		//备份HttpServletRequest
//		 
//		HttpServletRequest httpRequest = (HttpServletRequest)request;
		 //获取请求的URL  
        String url = request.getRequestURI();
        if(WHITE_LIST.indexOf(request.getRequestURL().toString())>=0){
        	return true;
        }
        RespPojo result = new RespPojo();
        //获取Session  
        HttpSession session = request.getSession(true);  
//        logger.info("欢迎进入拦截器----------------");
       
        if(session.getAttribute("User") != null){  
//        	logger.info("session非空,bye{}",session);
            return true;  
        }else{
//        	 Service service=myService.query();
//             if(service.getExpireDate().compareTo(new Date())<0){
//             	result.setRetCode(Constants.SERVICE_TIMOUT_CODE);
//                 result.setRetMsg(Constants.SERVICE_TIMOUT_MESSAGE);
//                 response.setContentType("text/html;charset=UTF-8");// 解决中文乱码  
//                 String str=JSON.toJSONString(result);
//                 try {  
//                     PrintWriter writer = response.getWriter();  
//                     writer.write(str);  
//                     writer.flush();  
//                     writer.close();  
//                     return false;
//                 } catch (Exception e) {  
//                     
//                     logger.error("会话处理异常{}"+e);
//                 }  
//             }
        	//JSONObject json=null;
//        	try {  
//        		
//		        BufferedReader  streamReader = new BufferedReader(new InputStreamReader(httpRequest.getInputStream(), "UTF-8"));  
//		            StringBuilder responseStrBuilder = new StringBuilder();  
//		            String inputStr;  
//		            while ((inputStr = streamReader.readLine()) != null) responseStrBuilder.append(inputStr);
//		            
//		            json = JSONObject.parseObject (responseStrBuilder.toString());  
//		        } catch (Exception e) {  
//		            e.printStackTrace();  
//		        } request=httpRequest;
        	if(request.getHeader("Authorization")!=null){
        		//||json.getString("uid")!=null
        		String token=request.getHeader("Authorization");
        		if(null!=JwtUtil.verifyToken(token)){
        			User user=myService.getUser(JwtUtil.getAppUID(token).toString());
        			if(null==user||user.getState()!=0){
        				  result.setRetCode(Constants.FAIL_CODE);
        	              result.setRetMsg(Constants.ACCOUNT_NOT_FOUND);
        	              response.setContentType("text/html;charset=UTF-8");// 解决中文乱码  
        	              String str=JSON.toJSONString(result);
        	              try {  
        	                  PrintWriter writer = response.getWriter();  
        	                  writer.write(str);  
        	                  writer.flush();  
        	                  writer.close();  
        	                  return false;
        	              } catch (Exception e) {  
        	                  
        	                  logger.error("会话处理异常{}"+e);
        	              }  
        			}
//        			if(JwtUtil.getRoleID(token).toString().equals("0")){
//        				Service service=myService.getService(Integer.parseInt(JwtUtil.getAppUID(token).toString()));
//        	             if(service.getExpireDate().compareTo(new Date())<0){
//        	             	result.setRetCode(Constants.SERVICE_TIMOUT_CODE);
//        	                 result.setRetMsg(Constants.SERVICE_TIMOUT_MESSAGE);
//        	                 response.setContentType("text/html;charset=UTF-8");// 解决中文乱码  
//        	                 String str=JSON.toJSONString(result);
//        	                 try {  
//        	                     PrintWriter writer = response.getWriter();  
//        	                     writer.write(str);  
//        	                     writer.flush();  
//        	                     writer.close();  
//        	                     return false;
//        	                 } catch (Exception e) {  
//        	                     
//        	                     logger.error("会话处理异常{}"+e);
//        	                 }  
//        	             }
//        			}
        			UserPojo up = new UserPojo();
        			
        			
        			up.setUserid(JwtUtil.getAppUID(token).toString());
        			up.setRoleid(JwtUtil.getRoleID(token).toString());
        			session.setAttribute("User",up);
        			return true;
        		}
    			
        	}
        	
        }
      //URL:login.jsp是公开的;这个demo是除了login.jsp是可以公开访问的，其它的URL都进行拦截控制  
        if(url.indexOf("login")>=0||url.indexOf("code")>=0 || url.indexOf("v1") >= 0
        		|| url.indexOf("/airDropTask/query") >= 0|| url.indexOf("/airDropTask/addAddress") >= 0|| url.indexOf("/airDropTask/addressUpload") >= 0

        		|| url.indexOf("/myService/getCurrentVersion") >= 0|| url.indexOf("/upload") >= 0){

            return true;  
        }else{
              result.setRetCode(Constants.SESSION_TIMOUT_CODE);
              result.setRetMsg(Constants.SESSION_TIMOUT_MESSAGE);
              response.setContentType("text/html;charset=UTF-8");// 解决中文乱码  
              String str=JSON.toJSONString(result);
              try {  
                  PrintWriter writer = response.getWriter();  
                  writer.write(str);  
                  writer.flush();  
                  writer.close();  
                  return false;
              } catch (Exception e) {  
                  
                  logger.error("会话处理异常{}"+e);
              }  
        }
      
        //不符合条件的，跳转到登录界面  
        // 判断是否为ajax请求  
//        if (request.getHeader("x-requested-with") != null  
//                && request.getHeader("x-requested-with")  
//                        .equalsIgnoreCase("XMLHttpRequest")) {  
//        	response.addHeader("sessionstatus", "timeOut");  
//        	response.addHeader("loginPath", "/login/index" );  
//        	return false; 
//        } else {
        	
        	
        	
           
       // }
        request.getRequestDispatcher("/login.jsp").forward(request, response);  
		return false;  
      
	}

	 /** 
     * Handler执行之后，ModelAndView返回之前调用这个方法 
     */  
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	 /** 
     * Handler执行完成之后调用这个方法 
     */  
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	/** 
    * Description: 复制输入流</br> 
    *  
    * @param inputStream 
    * @return</br> 
    */  
   public static InputStream cloneInputStream(ServletInputStream inputStream) {  
       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
       byte[] buffer = new byte[1024];  
       int len;  
       try {  
           while ((len = inputStream.read(buffer)) > -1) {  
               byteArrayOutputStream.write(buffer, 0, len);  
           }  
           byteArrayOutputStream.flush();  
       }  
       catch (IOException e) {  
           e.printStackTrace();  
       }  
       InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());  
       return byteArrayInputStream;  
   }  
	public static void main(String args[]){
		
	}

}
