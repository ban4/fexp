package com.datou.xml.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.datou.xml.system.SystemConfig;
import com.datou.xml.thread.ReadXmlTask;

/**
 * @author anbaihong
 * @date 2019年2月22日 下午3:10:04
 * @comment 
 */
public class XmlFilterListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			SystemConfig.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (SystemConfig.getBooleanValue("task.xml")) {
			new ReadXmlTask().server();
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
