package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class ShoppingcarType {
	
	public String errorcode;
	public String message;
	public List<ShoppingcarData> data = new ArrayList<ShoppingcarData>();
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
	public List<ShoppingcarData> getData() {
		return data;
	}
	public void setData(List<ShoppingcarData> data) {
		this.data = data;
	}
}
