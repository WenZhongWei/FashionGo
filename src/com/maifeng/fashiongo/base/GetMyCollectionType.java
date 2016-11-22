package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class GetMyCollectionType {
	public String errorcode;
	public String message;
	public List<GetMyCollectionData> data = new ArrayList<GetMyCollectionData>();
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
	public List<GetMyCollectionData> getData() {
		return data;
	}
	public void setData(List<GetMyCollectionData> data) {
		this.data = data;
	}

	
}
