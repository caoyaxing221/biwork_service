package com.biwork.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.biwork.po.RespPojo;

public interface UploadService {

  RespPojo upLoad(String base64);
  RespPojo upLoadLocal(String base64,HttpServletRequest request );
  RespPojo upLoad(MultipartFile file) throws IOException;

}
