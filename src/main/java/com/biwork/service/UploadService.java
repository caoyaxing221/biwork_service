package com.biwork.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.biwork.po.RespPojo;

public interface UploadService {

  RespPojo upLoad(String base64);

  RespPojo upLoad(MultipartFile file) throws IOException;

}
