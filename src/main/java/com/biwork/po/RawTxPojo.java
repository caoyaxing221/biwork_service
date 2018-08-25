package com.biwork.po;

// sendRawTransaction的返回结果为交易hash
public class RawTxPojo {
	private String Hash;
	
	public String getRawTx() {
		return Hash;
	}
	public void setRawTx(String hash) {
		Hash = hash;
	}
}