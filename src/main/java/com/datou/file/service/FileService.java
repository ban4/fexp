package com.datou.file.service;

import java.util.List;
import java.util.Map;

import com.datou.file.model.Excel;
import com.datou.file.model.User;

/**
 * @author anbaihong
 * @date 2018年12月6日 下午4:23:06
 * @comment 
 */
public interface FileService {

	String addFile(User u, String url);

	List<User> queryList();

	List<Map<String, String>> exportExcel(String excelId);

	List<Excel> queryExcel(String excelId);

	List<User> queryExcelName(String excelId);

}
