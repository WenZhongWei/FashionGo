package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class GetMyOrderType {
	public String errorcode;
	public String message;
	public List<GetMyOrderDaya> data = new ArrayList<GetMyOrderDaya>();
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
	public List<GetMyOrderDaya> getData() {
		return data;
	}
	public void setData(List<GetMyOrderDaya> data) {
		this.data = data;
	}
	
	
}
