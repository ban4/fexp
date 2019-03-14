package com.datou.xml.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author anbaihong
 * @date 2019年2月22日 下午3:13:03
 * @comment 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Region {
	@XmlAttribute(name="RId")
	private String rid;
	@XmlAttribute(name="CHName")
	private String chname;
	@XmlAttribute(name="ENName")
	private String enname;
	@XmlElement(name="city")
	private List<City> city;
	
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
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
	public List<City> getCity() {
		return city;
	}
	public void setCity(List<City> city) {
		this.city = city;
	}
	
	
}
