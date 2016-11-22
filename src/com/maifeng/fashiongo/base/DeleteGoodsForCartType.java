package com.maifeng.fashiongo.base;

public class DeleteGoodsForCartType {
	public String[] data;

	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
	public int errorcode;
	public String message;

	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
