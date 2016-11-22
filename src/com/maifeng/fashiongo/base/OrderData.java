package com.maifeng.fashiongo.base;

import java.io.Serializable;


public class OrderData implements Serializable{
	String imageUrl;
	String name;
	String price;
	String number;
	
	public OrderData(String imageUrl,String name,String price,String number) {
		this.imageUrl = imageUrl;
		this.name = name;
		this.price = price;
		this.number = number;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	

}
