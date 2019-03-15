package com.datou.util.page;

import org.apache.commons.lang.StringUtils;

/**
 * 分页操作
 * 
 * @author anbaihong
 * @date 2019年3月15日 下午1:33:08
 * @comment 
 */
public class PageUtil {
	/**
	 * 获取当前页
	 * 
	 * @param currentPage
	 * @return
	 */
	public static int getPageNum(String currentPage) {
		int pageNo = 0;
		if (StringUtils.isEmpty(currentPage)) {
			pageNo = 1;
		} else {
			pageNo = Integer.parseInt(currentPage);
		}
		return pageNo;
	}

	/**
	 * 每页条数
	 * 
	 * @param pageSize
	 * @return
	 */
	public static int getPageSize(String pageSize) {
		int pSize = 0;
		if (StringUtils.isEmpty(pageSize)) {
			pSize = 15;
		} else {
			pSize = Integer.parseInt(pageSize);
		}
		return pSize;
	}
}
