package com.biwork.entity;

public class ProcessWithBLOBs extends Process {
    private String cause;

    private String receiverMsg;

    private String remark;

    private String attachUrl;
    
    private String airDropTaskId;

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    public String getReceiverMsg() {
        return receiverMsg;
    }

    public void setReceiverMsg(String receiverMsg) {
        this.receiverMsg = receiverMsg == null ? null : receiverMsg.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl == null ? null : attachUrl.trim();
    }

	public String getAirDropTaskId() {
		return airDropTaskId;
	}

	public void setAirDropTaskId(String airDropTaskId) {
		this.airDropTaskId = airDropTaskId;
	}
}