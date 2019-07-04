package com.biwork.service.Impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.biwork.util.PropertiesUtil;
import com.biwork.po.RespPojo;
import com.biwork.service.UploadService;
import com.biwork.util.Base64Util;
import com.biwork.util.Constants;
import com.biwork.util.Fastdfs;
@Service("uploadService")
public class UploadServiceImpl implements UploadService {

  private Logger logger = LoggerFactory.getLogger(getClass());
  @Override
  public RespPojo upLoad(String base64) {
    RespPojo result = new RespPojo();
    result.setRetCode(Constants.SUCCESSFUL_CODE);
    result.setRetMsg(Constants.SUCCESSFUL_MESSAGE);

    base64 = base64.substring(base64.indexOf(",")+1,base64.length());
    String accessUrl = Fastdfs.upload2(Base64Util.byteToBase64Decoding(base64));
    logger.info("上传服务器返回的图片url地址: {}",accessUrl);
    if (StringUtils.isBlank(accessUrl)) {
      result.setRetCode(Constants.FAIL_CODE);
      result.setRetMsg("网络异常 请重新尝试");
      return result;
    }
    accessUrl = PropertiesUtil.getProperty("fastDfsUrl") + accessUrl;
    Map<String, String> map = new HashMap<String,String>();
    map.put("imageUrl", accessUrl);
    result.setData(map);
    return result;
  }
@Override
public RespPojo upLoad(MultipartFile file) throws IOException {
	RespPojo result = new RespPojo();
    result.setRetCode(Constants.SUCCESSFUL_CODE);
    result.setRetMsg(Constants.SUCCESSFUL_MESSAGE);
//    String s = Base64Util.byteToBase64Encoding(file.getBytes());
//    logger.info("base64Str{}",s);
    String accessUrl = Fastdfs.upload2(file.getBytes());
    logger.info("上传服务器返回的图片url地址: {}",accessUrl);
    if (StringUtils.isBlank(accessUrl)) {
      result.setRetCode(Constants.FAIL_CODE);
      result.setRetMsg("网络异常 请重新尝试");
      return result;
    }
    accessUrl = PropertiesUtil.getProperty("fastDfsUrl") + accessUrl;
    Map<String, String> map = new HashMap<String,String>();
    map.put("imageUrl", accessUrl);
    result.setData(map);
    return result;
}
public RespPojo upLoadLocal(String base64,HttpServletRequest request) {
    RespPojo result = new RespPojo();
    result.setRetCode(Constants.SUCCESSFUL_CODE);
    result.setRetMsg(Constants.SUCCESSFUL_MESSAGE);

    base64 = base64.substring(base64.indexOf(",")+1,base64.length());
    // 生成文件名
    String files = new SimpleDateFormat("yyyyMMddHHmmssSSS")
            .format(new Date())
            + (new Random().nextInt(9000) % (9000 - 1000 + 1) + 1000)
            + ".png";
    // 生成文件路径
    String filename =request.getSession().getServletContext().getRealPath("/").replace("biwork_service", "")+ PropertiesUtil.getProperty("uploadPath") + files;     
    try {
        // 生成文件         
        File imageFile = new File(filename);
        imageFile.createNewFile();
           if(!imageFile.exists()){
               imageFile.createNewFile();
            }
            OutputStream imageStream = new FileOutputStream(imageFile);
            
            byte[] b=Base64Util.byteToBase64Decoding(base64);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            imageStream.write(b);
            imageStream.flush();
            imageStream.close();                    
    } catch (Exception e) {         
        e.printStackTrace();
    }

    logger.info("上传返回的图片url地址: {}",filename);
    if (StringUtils.isBlank(filename)) {
      result.setRetCode(Constants.FAIL_CODE);
      result.setRetMsg("网络异常 请重新尝试");
      return result;
    }

    Map<String, String> map = new HashMap<String,String>();
    map.put("imageUrl",  PropertiesUtil.getProperty("host")+ PropertiesUtil.getProperty("uploadPath")+files);
    result.setData(map);
    return result;
  }
}
