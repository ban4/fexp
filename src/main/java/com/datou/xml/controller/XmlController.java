package com.datou.xml.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;
import com.datou.xml.model.Country;
import com.datou.xml.service.XmlService;

/**
 * 对XML进行操作：
 * 		readXml：	读取 解析 存储 xml内容
 * 		writeXml：	生成xml文件
 * 
 * 读取xml：
 * 		country.xml
 * 
 * 对应数据库：
 * 		meta_xml
 * 
 * @author anbaihong
 * @date 2019年2月22日 下午3:07:36
 * @comment 
 */
@Controller
@RequestMapping(value="/xml")
public class XmlController {
	
	@Autowired
	private XmlService xmlService;
	
	//通过配置文件config.xml 获取读取、生成路径 
	@Value("${readxml.path}")
	private String readxml_path;
	@Value("${writexml.path}")
	private String writexml_path;
	
	/**
	 * 读取 解析 xml 存储xml内容
	 * 
	 * @author anbaihong
	 * @date 2019年2月22日 下午5:19:35
	 * @return
	 * @comment
	 */
	@RequestMapping(value="/readXml",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String readXml(){
		int rowNum = 0;
		try {
			//解析xml 生成实体类bean
			JAXBContext jaxbC = JAXBContext.newInstance(Country.class);
			Unmarshaller marshaller = jaxbC.createUnmarshaller();
			//InputStream in = new FileInputStream(new File("D:/country.xml"));
			InputStream in = new FileInputStream(new File(readxml_path));
			InputSource source = new InputSource(in);
			source.setEncoding("UTF-8");
			Country country = (Country) marshaller.unmarshal(source);
			
			//操作实体类 存入数据库
			rowNum = xmlService.insertSelective(country);
			
		} catch (Exception e) {
			//log.info("更新xml异常:"+e) 
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject();
		json.put("code", "更新xml数据"+rowNum+"条");
		return json.toString();
	}
	
	/**
	 * 生成xml文件
	 * 
	 * @author anbaihong
	 * @date 2019年2月22日 下午5:20:12
	 * @return
	 * @comment
	 */
	@RequestMapping(value="/writeXml",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String writeXml(){
		//查询数据   拼凑xml格式实体类
		Country country = xmlService.queryXml();
		
		JSONObject json = new JSONObject();
		
		try {
			//生成xml
			JAXBContext jaxbC = JAXBContext.newInstance(country.getClass());
			Marshaller marshaller = jaxbC.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			//File file = new File("D:/newCountry.xml");
			File file = new File(writexml_path);
			marshaller.marshal(country, file);
			
			json.put("code", "成功");
		} catch (Exception e) {
			// log.info("生成xml异常:"+e)
			e.printStackTrace();
		}
		
		return json.toString();
	}

}
