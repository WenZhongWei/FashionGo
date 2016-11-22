package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class ClassifyTwoType {

	public List<ClassifyTwoData> data =new ArrayList<ClassifyTwoData>();
	public String message;
	public String errorcode;
	public List<ClassifyTwoData> getData() {
		return data;
	}
	public void setData(List<ClassifyTwoData> data) {
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
