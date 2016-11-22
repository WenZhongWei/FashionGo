package com.maifeng.fashiongo.base;

import java.util.ArrayList;
import java.util.List;

public class ClassifyThreeType {
public List<ClassifyThreeData> data=new ArrayList<ClassifyThreeData>();
public String errorcode;
public String message;
public List<ClassifyThreeData> getData() {
	return data;
}
public void setData(List<ClassifyThreeData> data) {
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
