package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class GoodsSpecificationsType {
	private String errorcode;
	private String message;
	private List<GoodsSpecificationsData> data = new ArrayList<GoodsSpecificationsData>();

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

	public List<GoodsSpecificationsData> getData() {
		return data;
	}

	public void setData(List<GoodsSpecificationsData> data) {
		this.data = data;
	}

}
