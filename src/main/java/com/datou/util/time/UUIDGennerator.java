package com.datou.util.time;

import java.util.UUID;

/**
 * UUID
 * 
 * @author anbaihong
 * @date 2019年3月15日 下午12:01:00
 * @comment 
 */
public class UUIDGennerator {
	
	public static String get32UUID(){
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

}
