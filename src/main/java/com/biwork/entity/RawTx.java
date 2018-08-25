package com.biwork.entity;

// sendRawTransaction的返回结果为交易hash
public class RawTx {
	private String Hash;
	
	public String getRawTx() {
		return Hash;
	}
	public void setRawTx(String hash) {
		Hash = hash;
	}
}