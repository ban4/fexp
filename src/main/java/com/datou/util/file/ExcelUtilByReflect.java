package com.datou.util.file;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 反射读取excel 实体类 顺序需对应 或 添加excel注解（@ExcelField）  
 * 
 * @author anbaihong
 * @date 2019年3月15日 下午4:59:41
 * @comment 
 */
public class ExcelUtilByReflect<T> {
	
	//获取 文件流
	public void importExcel(FileInputStream file, Class<T> clazz, List<T> ret) throws Exception {
		XSSFWorkbook wb = new XSSFWorkbook(file);
		// 获取第一个sheet页
		XSSFSheet sheet = wb.getSheetAt(0);
		T obj = null;
		// 遍历行 从下表第二行开始（去除提示信息及标题）
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			XSSFRow row = sheet.getRow(i);
			if (null != row) {
				//装载obj
				obj = (T) clazz.newInstance();
				dataObj(obj, row);
				ret.add(obj);
			}
		}
	}

	private static String dataObj(Object obj, XSSFRow row) throws Exception {
		Field[] fields = obj.getClass().getDeclaredFields();
		// excel表格字段顺序要和obj字段顺序对其（如果有多余字段另做特殊下标对应处理）
		for (int j = 0; j < fields.length; j++) {
			String name = fields[j].getName();
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			Method method = obj.getClass().getMethod("set" + name, String.class);
			method.invoke(obj, getVal(row.getCell(j)));
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
		if (null != cell) {
			return cell.getStringCellValue();
		}
		return null;
	}
}
