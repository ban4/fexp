package com.datou.file.util;

import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




/**
 * @author anbaihong
 * @date 2018年12月6日 下午5:19:30
 * @comment 
 */
public class ExcelUtil<T> {

	
	//获取 文件流
	public String readExcel(FileInputStream file,Class<T> clazz,List<T> ret) throws Exception{
		XSSFWorkbook wb = new XSSFWorkbook(file);
		//获取第一个sheet页
		XSSFSheet sheet = wb.getSheetAt(0);
		T obj = null;
		//遍历行 从下表第二行开始（去除提示信息及标题）
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			XSSFRow row = sheet.getRow(i);
			if (null != row){
				obj = clazz.newInstance();
				String s = dataObj(obj,row);
				if (StringUtils.isBlank(s)){
					ret.add(obj);
				} else{
					return s;
				}
				
			}
		}
		return null;
	}

	private static String dataObj(Object obj, XSSFRow row) throws Exception {
		Field[] fields = obj.getClass().getDeclaredFields();
		//excel表格字段顺序要和obj字段顺序对其（如果有多余字段另做特殊下标对应处理）
		for (int j = 1; j < fields.length-1; j++){
			String name = fields[j].getName();
			
			String s = (String) getVal(row.getCell(j-1));
			if (StringUtils.isNotBlank(s)){
				Annotation[] annot = fields[j].getAnnotations();
				Pattern pattern =  (Pattern) annot[1];
				if(!s.matches( pattern.regexp())){
					String result = "字段:"+(String) getVal(row.getCell(j-1)) +"填写有误，"+ pattern.message();
					return result;
				}
			}
			
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			Method method = obj.getClass().getMethod("set"+name, String.class);
			method.invoke(obj, getVal(row.getCell(j-1)));
		}
		return null;
	}

	/**
	 * 处理 val（暂时只处理String，可以自己添加需要的val类型）
	 * 
	 * @author anbaihong
	 * @date 2018年12月6日 下午5:32:43
	 * @param cell
	 * @return
	 * @comment
	 */
	private static Object getVal(XSSFCell cell) {
		if (null != cell){
			return cell.getStringCellValue();
		}
		return null;
	}
	
}
