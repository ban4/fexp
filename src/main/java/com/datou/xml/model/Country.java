package com.datou.xml.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author anbaihong
 * @date 2019年2月22日 下午3:13:03
 * @comment 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="country")
public class Country {
	
	@XmlAttribute(name="CHName")
	private String CHName = "中国";
	@XmlAttribute(name="ENName")
	private String ENName = "China";
	@XmlElement(name="region")
	private List<Region> region;
	
	public String getCHName() {
		return CHName;
	}
	public void setCHName(String cHName) {
		CHName = cHName;
	}
	public String getENName() {
		return ENName;
	}
	public void setENName(String eNName) {
		ENName = eNName;
	}
	public List<Region> getRegion() {
		return region;
	}
	public void setRegion(List<Region> region) {
		this.region = region;
	}
	
	
}
