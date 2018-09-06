package com.biwork.service.Impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    base64 = base64.replaceAll("data:image/jgp;base64,", "");
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

}
