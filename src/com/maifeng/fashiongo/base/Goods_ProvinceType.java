package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class Goods_ProvinceType {
	public String errorcode;//���ص�ʧ����Ϣ
	public String message;//���صĳɹ���Ϣ
	public List<Goods_ProvinceData> data = new ArrayList<Goods_ProvinceData>();
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
	public List<Goods_ProvinceData> getData() {
		return data;
	}
	public void setData(List<Goods_ProvinceData> data) {
		this.data = data;
	}
	
}
