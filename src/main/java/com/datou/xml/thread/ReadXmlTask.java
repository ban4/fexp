package com.datou.xml.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;

import com.datou.xml.model.Country;
import com.datou.xml.service.XmlService;
import com.datou.xml.system.SystemConfig;
import com.datou.xml.util.spring.SpringContextUtil;

/**
 * @author anbaihong
 * @date 2019年2月22日 下午3:15:43
 * @comment 
 */
public class ReadXmlTask {
	
	XmlService xmlService = SpringContextUtil.getBean("xmlService");
	
	public void server(){
		ReadXmlWork rxw = new ReadXmlWork();
		Thread t = new Thread(rxw);
		t.start();
	}
	
	class ReadXmlWork implements Runnable{
		@Override
		public void run() {
			doReadXml();
		}
	}

	private void doReadXml() {
		try {
			JAXBContext jaxbC = JAXBContext.newInstance(Country.class);
	        Unmarshaller marshaller = jaxbC.createUnmarshaller();
	        InputStream in = new FileInputStream(new File(SystemConfig.getValue("readxml.path")));
	        InputSource source = new InputSource(in);
	        source.setEncoding("UTF-8");
	        Country country = (Country)marshaller.unmarshal(source);
	        
	        int rowNum = xmlService.insertSelective(country);
	        
	        System.err.println("更新xml数据"+rowNum+"条");			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
