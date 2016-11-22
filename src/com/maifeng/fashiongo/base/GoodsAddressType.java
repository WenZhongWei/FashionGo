package com.maifeng.fashiongo.base;

public class GoodsAddressType {
	/**
	 * 新增地址封装类
	 */
	private Long id;//主键id
	private String tv_name;//姓名
	private String tv_phone;//电话
	private String tv_address;//地址
	
	public GoodsAddressType(){
		
	}
	public GoodsAddressType(Long id,String tv_name,String tv_phone,String tv_address){
		this.id=id;
		this.tv_name=tv_name;
		this.tv_phone=tv_phone;
		this.tv_address=tv_address;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTv_name() {
		return tv_name;
	}
	public void setTv_name(String tv_name) {
		this.tv_name = tv_name;
	}
	public String getTv_phone() {
		return tv_phone;
	}
	public void setTv_phone(String tv_phone) {
		this.tv_phone = tv_phone;
	}
	public String getTv_address() {
		return tv_address;
	}
	public void setTv_address(String tv_address) {
		this.tv_address = tv_address;
	}
	@Override
	public String toString() {
		return "GoodsAddressType [id=" + id + ", tv_name=" + tv_name
				+ ", tv_phone=" + tv_phone + ", tv_address=" + tv_address + "]";
	}
}
