package com.maifeng.fashiongo.base;

/**
 * 商品尺寸实体类
 * 
 * @author Administrator
 * 
 */

public class GoodSizeData {

	private String specificationsId; // 参数id
	private String size; // 尺码
	private String num; // 库存

	public String getSpecificationsId() {
		return specificationsId;
	}

	public void setSpecificationsId(String specificationsId) {
		this.specificationsId = specificationsId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

}
