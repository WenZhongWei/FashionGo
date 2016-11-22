package com.maifeng.fashiongo.base;

import android.R.integer;



/**
 * ��Ʒ����ʵ����
 * 
 * @author liekkas
 * 
 */
public class GoodDetailData {

	private String goodsName; // ��Ʒ����
	private String goodsInfo; // ��Ʒ���
	private String goodsCode; // ��Ʒ���
	private String originalPrice; // �г���
	private String price; // �Żݼ�
	private int isCollect; // �Ƿ����ղ�
//	private String totalNum; // �ܿ��
	private int totalNum;
	private int isPackage; // �Ƿ����
	private GoodDetailGoodsImageList goodsImageList; // ��ƷͼƬ(����9��)

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}



//	public String getTotalNum() {
//		return totalNum;
//	}
//
//	public void setTotalNum(String totalNum) {
//		this.totalNum = totalNum;
//	}
	
	

	public int getIsPackage() {
		return isPackage;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public void setIsPackage(int isPackage) {
		this.isPackage = isPackage;
	}

	public GoodDetailGoodsImageList getGoodsImageList() {
		return goodsImageList;
	}

	public void setGoodsImageList(GoodDetailGoodsImageList goodsImageList) {
		this.goodsImageList = goodsImageList;
	}




}
