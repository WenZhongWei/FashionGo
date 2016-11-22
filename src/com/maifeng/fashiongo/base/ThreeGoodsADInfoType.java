package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class ThreeGoodsADInfoType {
	public List<ThreeGoodsADInfoData> data =new ArrayList<ThreeGoodsADInfoData>();
	public String message;
	public String errorcode;
	public List<ThreeGoodsADInfoData> getData() {
		return data;
	}
	public void setData(List<ThreeGoodsADInfoData> data) {
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
