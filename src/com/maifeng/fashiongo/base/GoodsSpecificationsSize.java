package com.maifeng.fashiongo.base;

/**
 * 商品尺寸实体类
 * 
 * @author liekkas
 * 
 */
public class GoodsSpecificationsSize {

	private String specificationsId; // 参数id
	private String size; // 尺码
//	private String num; // 库存
	private int num;

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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

//	public String getNum() {
//		return num;
//	}
//
//	public void setNum(String num) {
//		this.num = num;
//	}

}
