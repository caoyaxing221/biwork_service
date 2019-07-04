package com.biwork.exception;

public class BusiException extends RuntimeException {

  private static final long serialVersionUID = -8114812180865393929L;


  private String code;

  private String msg;


  public BusiException() {}


  public BusiException( String newErrorNo,  String errorMsg) {
    super(errorMsg);
    this.code = newErrorNo;
    this.msg = errorMsg;
  }

  public BusiException( String newErrorNo,  String errorMsg,
      Throwable cause) {
    super(errorMsg, cause);
    this.code = newErrorNo;
    this.msg = errorMsg;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}
