package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class Goods_GetAddressType {
	public String errorcode;
	public String message;
	public List<Goods_GetAddressData> data = new ArrayList<Goods_GetAddressData>();
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
	public List<Goods_GetAddressData> getData() {
		return data;
	}
	public void setData(List<Goods_GetAddressData> data) {
		this.data = data;
	}
	
}
