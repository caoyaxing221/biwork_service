package com.biwork.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.biwork.po.RespPojo;
import com.biwork.po.request.AddAddressPojo;
import com.biwork.po.request.UploadPhotoPojo;
import com.biwork.service.UploadService;
import com.biwork.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Controller
@Api(value = "/upload", description = "上传图片")
public class UploadController extends BaseController {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private UploadService uploadService;

  @ResponseBody
  @RequestMapping("/upload")
  @ApiOperation(value = "上传图片", notes = "上传图片",httpMethod = "POST")
  
  public RespPojo upload(HttpServletRequest request,@RequestBody
			@ApiParam(name="图片base64",value="传入json格式",required=true) UploadPhotoPojo req,
      HttpServletResponse response) {
    String base64 = req.getPicUrl();
//    logger.info("上传上来的base64数据: {}",base64);
    RespPojo result = uploadService.upLoadLocal(base64, request);
    return result;
  }
  /**
   * 图片文件上传
   */
  @ResponseBody
  @RequestMapping(value = "/photoUpload",method = RequestMethod.POST,consumes="multipart/*",headers="content-type=multipart/form-data")
  @ApiOperation(value = "上传图片", notes = "上传图片")
  public RespPojo photoUpload(@ApiParam(value="上传的文件",required=true)MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException{
		RespPojo resp=new RespPojo();
      if (file!=null) {// 判断上传的文件是否为空
          String path=null;// 文件路径
          String type=null;// 文件类型
          String fileName=file.getOriginalFilename();// 文件原名称
          logger.info("上传的文件原名称:"+fileName);
          // 判断文件类型
          type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
          if (type!=null) {// 判断文件类型是否为空
              if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
            	  resp = uploadService.upLoad(file);
              }else {
            	  resp.setRetCode(Constants.PARAMETER_CODE);
    			  resp.setRetMsg("不是我们想要的文件类型,请按要求重新上传");
    			  return resp;
                  
              }
          }else{
        	  resp.setRetCode(Constants.PARAMETER_CODE);
			  resp.setRetMsg("文件类型为空");
			  return resp;
          }
      }else {
    	  resp.setRetCode(Constants.PARAMETER_CODE);
		  resp.setRetMsg("没有找到相对应的文件");
		  return resp;
      }
      return resp;
  }
  
}
