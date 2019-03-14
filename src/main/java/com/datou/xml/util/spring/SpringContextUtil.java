package com.datou.xml.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring应用上下文环境
 * 		必须实现ApplicaionContextAware接口
 * 		通过传递applicationContext参数初始化成员变量applicationContext
 * 		必须  @Component 
 * @author anbaihong
 * @date 2019年2月22日 下午3:16:15
 * @comment 
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}
	
	

}
