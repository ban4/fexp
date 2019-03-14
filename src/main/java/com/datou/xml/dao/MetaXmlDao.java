package com.datou.xml.dao;

import com.datou.xml.model.City;
import com.datou.xml.model.MetaXml;
import com.datou.xml.model.Region;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface MetaXmlDao {
    //int deleteByPrimaryKey(Integer id);

    //int insert(MetaXml record);

    int insertSelective(MetaXml record);

    //MetaXml selectByPrimaryKey(Integer id);

    //int updateByPrimaryKeySelective(MetaXml record);

    //int updateByPrimaryKey(MetaXml record);
    
	List<Region> queryRegion();

	List<City> queryCity();
}