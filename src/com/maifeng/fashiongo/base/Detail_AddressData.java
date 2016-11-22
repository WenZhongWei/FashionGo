package com.maifeng.fashiongo.base;
public class Detail_AddressData{
	public String name;
	public String id;
	public String phone;
	public String province;
	public String city;
	public String area;
	public String address;
	public String isDefault;//是否默认显示地址，0 否 1 是
	public Detail_AddressData(String name,String id,String phone,String province,
			String city,String area,String address,String isDefault){
		super();
		this.name=name;
		this.id=id;
		this.phone=phone;
		this.province=province;
		this.city=city;
		this.area=area;
		this.address=address;
		this.isDefault=isDefault;
	}
	public Detail_AddressData(){
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
}
