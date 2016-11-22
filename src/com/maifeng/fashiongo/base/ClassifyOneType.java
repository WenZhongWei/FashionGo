package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class ClassifyOneType {
	public List<ClassifyOneData> data = new ArrayList<ClassifyOneData>();
	public String message;
	public String errorcode;
	public List<ClassifyOneData> getData() {
		return data;
	}
	public void setData(List<ClassifyOneData> data) {
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
