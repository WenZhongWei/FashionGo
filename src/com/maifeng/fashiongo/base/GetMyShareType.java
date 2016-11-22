package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class GetMyShareType {
	public String errorcode;
	public String message;
	public List<GetMyShareData> data = new ArrayList<GetMyShareData>();
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
	public List<GetMyShareData> getData() {
		return data;
	}
	public void setData(List<GetMyShareData> data) {
		this.data = data;
	}
	
}
