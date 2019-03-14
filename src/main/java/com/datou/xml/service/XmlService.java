package com.datou.xml.service;

import com.datou.xml.model.Country;

/**
 * @author anbaihong
 * @date 2019年2月22日 下午3:14:00
 * @comment 
 */
public interface XmlService {

	int insertSelective(Country country);

	Country queryXml();

}
