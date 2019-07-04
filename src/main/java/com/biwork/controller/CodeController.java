package com.biwork.controller;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biwork.exception.BusiException;
import com.biwork.po.RespPojo;
import com.biwork.util.Base64Util;
import com.biwork.util.Constants;
import com.biwork.util.MD5Util;
import com.biwork.util.RandomCodeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author zh
 *
 */
@Controller
@RequestMapping("/code") 
@Api(value = "/code", description = "图形验证码", produces = MediaType.APPLICATION_JSON_VALUE)
public class CodeController {
	@ApiOperation(value = "获取图形验证码", notes = "获取图形验证码",httpMethod = "GET")
	@RequestMapping("/get") 
    public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException { 
		RandomCodeUtil rdnu = RandomCodeUtil.Instance();
		HttpSession session = req.getSession(); 
		// 取得随机字符串放入Session中
		session.setAttribute("RANDOMCODE", rdnu.getString());
		System.out.println(session.getAttribute("RANDOMCODE"));
		// 禁止图像缓存。  
        resp.setHeader("Pragma", "no-cache"); 
        resp.setHeader("Cache-Control", "no-cache"); 
        resp.setDateHeader("Expires", 0); 
        resp.setContentType("image/jpeg"); 
 
        // 将图像输出到Servlet输出流中。  
        ServletOutputStream sos = resp.getOutputStream(); 
        ImageIO.write(rdnu.getBuffImg(), "jpeg", sos); 
        sos.close(); 
    }
	@ResponseBody
	@ApiOperation(value = "获取图形验证码BASE64", notes = "获取图形验证码BASE64",httpMethod = "GET")
	@RequestMapping("/getImgCode") 
    public RespPojo getImgCode(HttpServletRequest req) throws IOException { 
		RandomCodeUtil rdnu = RandomCodeUtil.Instance();
		HttpSession session = req.getSession(); 
		// 取得随机字符串放入Session中
		session.setAttribute("RANDOMCODE", rdnu.getString());
		System.out.println(session.getAttribute("RANDOMCODE"));
		RespPojo resp=new RespPojo();
		
		resp.setRetCode(Constants.SUCCESSFUL_CODE);
		resp.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
		resp.setData(rdnu.getBase64Img());
        return resp;
    }
}
