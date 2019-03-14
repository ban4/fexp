package com.datou.xml.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datou.xml.dao.MetaXmlDao;
import com.datou.xml.model.City;
import com.datou.xml.model.Country;
import com.datou.xml.model.MetaXml;
import com.datou.xml.model.Region;
import com.datou.xml.service.XmlService;

/**
 * @author anbaihong
 * @date 2019年2月22日 下午3:14:33
 * @comment 
 */
@Service("xmlService")
public class XmlServiceImpl implements XmlService{
	
	@Autowired
	private MetaXmlDao metaXmlDao;
	
	@Override
	public int insertSelective(Country country) {
		int rowNum = 0;
		List<Region> regionsList = country.getRegion();
		for (Region region : regionsList) {
			MetaXml metaXml = new MetaXml();
			metaXml.setRegionId(region.getRid());
			metaXml.setRegionChname(region.getChname());
			metaXml.setRegionEnname(region.getEnname());
			for (City city : region.getCity()) {
				metaXml.setCityId(city.getCityId());
				metaXml.setCityChname(city.getCityId());
				metaXml.setCityEnname(city.getEnname());
				metaXml.setCityProvince(city.getProvince());
				
				rowNum += metaXmlDao.insertSelective(metaXml);
			}
		}
		return rowNum;
	}

	@Override
	public Country queryXml() {
		Country country = new Country();
		country.setCHName("中国");
		country.setENName("China");
		
		List<Region> regList = metaXmlDao.queryRegion();
		for (Region region : regList) {
			String pre = region.getRid().substring(0, 3);
			
			List<City> cityList = metaXmlDao.queryCity();
			List<City> addList = new ArrayList<>();
			for (City city : cityList) {
				if (city.getCityId().contains(pre)) {
					addList.add(city);
				}
			}
			region.setCity(addList);
		}
		country.setRegion(regList);
		return country;
	}

}
