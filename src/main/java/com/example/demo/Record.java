package com.example.demo;

public class Record {
	private String transId;
	private String transTms;
	private String rcNum;
	private String clientId;
	
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getTransTms() {
		return transTms;
	}
	public void setTransTms(String transTms) {
		this.transTms = transTms;
	}
	public String getRcNum() {
		return rcNum;
	}
	public void setRcNum(String rcNum) {
		this.rcNum = rcNum;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@Override
	public String toString() {
		return transId + "," + transTms + "," + rcNum + "," + clientId;
	}

 
}

