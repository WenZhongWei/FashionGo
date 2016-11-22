package com.maifeng.fashiongo.base;





public class RegisterType {
	
	public RegisterData data ;
	public String message;
	public String errorcode;
	
	public RegisterData getData() {
		return data;
	}
	public void setData(RegisterData data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	
}
