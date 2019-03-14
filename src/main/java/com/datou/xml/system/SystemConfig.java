package com.datou.xml.system;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.sun.org.apache.xpath.internal.operations.Bool;

import sun.util.logging.resources.logging;

/**
 * 用于web.xml初始化时读取系统配置文件。
 * 
 * @author anbaihong
 * @date 2019年2月22日 下午3:14:57
 * @comment 
 */
public class SystemConfig {
	
	private static Map<String,String> sysConfMap = new HashMap<>();
	
	public static void load() throws Exception{
		sysConfMap.clear();
		load("config.properties");
	}

	private static void load(String configFile) throws Exception {
		
		InputStream inputStream = null;
		try {
			String filePath = SystemConfig.class.getResource("/").getPath() + configFile;
			inputStream = new BufferedInputStream(new FileInputStream(filePath));
			Properties props = new Properties();
			props.clear();
			props.load(inputStream);
			Iterator<Object> iter = props.keySet().iterator();
			while(iter.hasNext()){
				String key = String.valueOf(iter.next());
				sysConfMap.put(key, props.getProperty(key));
			}
			
		} catch (Exception e) {
			// log.info("加载"+configFile+"出现异常，请检查配置");
			throw new Exception(e);
		} finally {
			if (null != inputStream) {
				inputStream.close();
				inputStream = null;
			}
		}
	}
	
	public static String getValue(String key){
		return sysConfMap.get(key).trim();
	}
	
	public static boolean getBooleanValue(String key){
		boolean value = false;
		try {
			value = Boolean.parseBoolean(sysConfMap.get(key));
		} catch (Exception e) {
			//log.error("无法转换config.properties文件的"+key+"为Boolean类型");
		}
		return value;
	}
	
	
}
