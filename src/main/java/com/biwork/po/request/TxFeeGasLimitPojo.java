package com.biwork.po.request;

import java.math.BigInteger;

public class TxFeeGasLimitPojo {
	private String formAddress;
	private String toAddress;
	private BigInteger value;
	private BigInteger nonce;
	private BigInteger gasPrice;
	private String data; 
	private BigInteger gas;
	public String getFormAddress() {
		return formAddress;
	}
	public void setFormAddress(String formAddress) {
		this.formAddress = formAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public BigInteger getValue() {
		return value;
	}
	public void setValue(BigInteger value) {
		this.value = value;
	}
	public BigInteger getNonce() {
		return nonce;
	}
	public void setNonce(BigInteger nonce) {
		this.nonce = nonce;
	}
	public BigInteger getGasPrice() {
		return gasPrice;
	}
	public void setGasPrice(BigInteger gasPrice) {
		this.gasPrice = gasPrice;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public BigInteger getGas() {
		return gas;
	}
	public void setGas(BigInteger gas) {
		this.gas = gas;
	}
}
