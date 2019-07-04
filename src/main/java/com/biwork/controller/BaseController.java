package com.biwork.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biwork.exception.BusiException;
import com.biwork.po.RespPojo;


@ControllerAdvice
public class BaseController {

  private static final Logger log =
      LoggerFactory.getLogger(BaseController.class);


  @ExceptionHandler(BusiException.class)
  @ResponseBody
  public Object handleAnyException(BusiException e) {
    log.error("code:{} msg :{}",e.getCode(),e.getMsg());
    RespPojo rtn = new RespPojo();
    String code = "9999";
    String msg = "系统异常";
    BusiException b = (BusiException) e;
    code = b.getCode();
    msg = b.getMsg();
    rtn.setRetCode(code);
    rtn.setRetMsg(msg);
    return rtn;
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseBody
  public Object handleAnyException(RuntimeException e) {
    log.error(e.getMessage(), e);
    RespPojo rtn = new RespPojo();
    String code = "9999";
    String msg = "系统异常";
    rtn.setRetCode(code);
    rtn.setRetMsg(msg);
    return rtn;
  }
  
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public Object handleAnyException(Exception e) {
    log.error(e.getMessage(), e);
    RespPojo rtn = new RespPojo();
    String code = "9999";
    String msg = "系统异常";
    rtn.setRetCode(code);
    rtn.setRetMsg(msg);
    return rtn;
  }
}
