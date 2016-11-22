package com.maifeng.fashiongo.base;
public class Detail_AddressType{
	public String errorcode;
	public String message;
	public Detail_AddressData data;
	
	public Detail_AddressData getData() {
		return data;
	}
	public void setData(Detail_AddressData data) {
		this.data = data;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
