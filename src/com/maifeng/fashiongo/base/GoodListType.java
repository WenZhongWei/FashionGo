package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

import android.R.string;

public class GoodListType {
	public List<GoodListData> data = new ArrayList<GoodListData>();
	private String errorcode; // 错误代码
	private String message; // 提示信息

	public List<GoodListData> getData() {
		return data;
	}

	public void setData(List<GoodListData> data) {
		this.data = data;
	}

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

}
