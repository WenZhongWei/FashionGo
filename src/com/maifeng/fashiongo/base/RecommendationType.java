package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class RecommendationType {
	public String errorcode;
	public String message;
	public List<RecommendationData> data = new ArrayList<RecommendationData>();
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
	public List<RecommendationData> getData() {
		return data;
	}
	public void setData(List<RecommendationData> data) {
		this.data = data;
	}
	
	
	

}
