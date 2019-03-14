package com.datou.xml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author anbaihong
 * @date 2019年2月22日 下午3:13:03
 * @comment 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class City {
	@XmlAttribute(name="CityId")
	private String cityId;
	@XmlAttribute(name="CHName")
	private String chname;
	@XmlAttribute(name="ENName")
	private String enname;
	@XmlAttribute(name="Province")
	private String province;
	
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getChname() {
		return chname;
	}
	public void setChname(String chname) {
		this.chname = chname;
	}
	public String getEnname() {
		return enname;
	}
	public void setEnname(String enname) {
		this.enname = enname;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	
}
