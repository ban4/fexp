package com.datou.util.file;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public final class XmlUtils {

	private XmlUtils(){
		
	}
	
	/**
	 * 解析xml文件	 生成 对应 javabean 对象
	 * 
	 * @author anbaihong
	 * @date 2019年3月15日 下午3:32:37
	 * @param xmlPath
	 * @param clazz
	 * @return
	 * @comment
	 */
	public static Object xmlToJavaBean(String xmlPath, Class<?> clazz) {
		try {
			JAXBContext jaxbC = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jaxbC.createUnmarshaller();
			Object object = (Object) unmarshaller.unmarshal(new File(xmlPath));
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析xml文件	 生成 对应 javabean 对象
	 * 
	 * @author anbaihong
	 * @date 2019年3月15日 下午3:40:01
	 * @param is
	 * @param clazz
	 * @return
	 * @comment
	 */
	public static Object xmlToJavaBean(InputStream is, Class<?> clazz) {
		try {
			JAXBContext jaxbC = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jaxbC.createUnmarshaller();
			Object object = (Object) unmarshaller.unmarshal(is);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 实体类 生成 xml
	 * 
	 * @author anbaihong
	 * @date 2019年3月15日 下午3:44:35
	 * @param obj
	 * @param clazz
	 * @return
	 * @comment
	 */
	public static String beanToXml(Object obj, Class<?> clazz, String path) {
		try {
			JAXBContext jaxbC = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jaxbC.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(obj, new File(path));
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 实体类转换javabean
	 * 
	 * @author anbaihong
	 * @date 2019年3月15日 下午3:58:45
	 * @param obj
	 * @param clazz
	 * @return
	 * @comment
	 */
	public static String beanToXml(Object obj, Class<?> clazz){
		try {
			JAXBContext jaxbC = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jaxbC.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(obj, stringWriter);
			return stringWriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	
	
    /**
     * XML转对象
     * @param clazz 对象类
     * @param str xml字符串
     * @param <T> T
     * @return
     */
	public static <T> T parseFromXml(Class<T> clazz, String xml) {
		// 创建解析XML对象
		XStream xStream = new XStream(new DomDriver());
		// 处理注解
		xStream.processAnnotations(clazz);
		@SuppressWarnings("unchecked")
		// 将XML字符串转为bean对象
		T t = (T) xStream.fromXML(xml);
		return t;
	}

    /**
     * 对象转xml
     * @param obj 对象
     * @return
     */
	public static String toXml(Object obj) {
		XStream xStream = new XStream(new DomDriver());
		xStream.processAnnotations(obj.getClass());
		return xStream.toXML(obj);
	}

	
	
}