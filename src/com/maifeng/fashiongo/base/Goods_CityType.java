package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class Goods_CityType {
	public String errorcode;//���ص�ʧ����Ϣ
	public String message;//���صĳɹ���Ϣ
	public List<Goods_CityData> data = new ArrayList<Goods_CityData>();
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
	public List<Goods_CityData> getData() {
		return data;
	}
	public void setData(List<Goods_CityData> data) {
		this.data = data;
	}
	
}
