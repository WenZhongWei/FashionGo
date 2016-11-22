package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class BannerType {
	public List<BannerData> data =new ArrayList<BannerData>();
	public String message;
	public String errorcode;
	public List<BannerData> getData() {
		return data;
	}
	public void setData(List<BannerData> data) {
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
