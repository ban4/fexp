package com.datou.file.util;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 导出excel工具类 	
 * 		07版	.xlsx
 * 		03版	.xls
 * 
 * @author anbaihong
 * @date 2018年12月26日 上午9:53:23
 * @comment
 */
public class ExportExcelUtil {
	
	/**
	 * 导出excel
	 * 
	 * @author anbaihong
	 * @date 2018年12月26日 下午3:28:21
	 * @param titles		String[] titles = { "身份证号", "手机号", "邮箱", "QQ号" }; 
	 * @param excelname		excel文件名
	 * @param list			
	 * @param response
	 * @throws Exception
	 * @comment
	 */
	public static void exportExcel(String[] titles, String excelname, List<Map<String, String>> list,HttpServletResponse response) throws Exception{
		String ext = excelname.substring(excelname.lastIndexOf("."),excelname.length());
		if(".xlsx".equals(ext)){
			exportExcelXlsx(titles,excelname,list,response);
		}else if(".xls".equals(ext)){
			exportExcelXls(titles,excelname,list,response);
		}else{
			System.out.println("不是excel格式，无法导出");
		}
	}
	
	public static void exportExcelXlsx(String[] titles, String excelname, List<Map<String, String>> list,HttpServletResponse response) throws Exception {
		// 声明一个工作薄
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workBook.createSheet();
		sheet.setDefaultColumnWidth(15);
		// workBook.setSheetName(0,"小表格");
		// 创建表格标题行 第一行
		XSSFRow titleRow = sheet.createRow(0);
		for (int i = 0; i < titles.length; i++) {
			titleRow.createCell(i).setCellValue(titles[i]);
		}
		// 插入需导出的数据
		for (int i = 0; i < list.size(); i++) {
			// 获取每一个map
			Map<String, String> map = list.get(i);
			// 一个map一行数据
			XSSFRow row = sheet.createRow(i + 1);
			for (int j = 0; j < titles.length; j++) {
				// 遍历标题，把key与标题匹配
				String title = titles[j];
				// 判断该内容存在map中
				if (map.containsKey(title)) {
					row.createCell(j).setCellValue(map.get(title));
				}
			}
		}
		//String filename = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xlsx";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + new String(excelname.getBytes("UTF-8"),"iso-8859-1") );
		OutputStream ouputStream = response.getOutputStream();
		workBook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
  		
		//写在D盘下
		/*File file = new File("D:/"+excelname);
		FileOutputStream outStream = new FileOutputStream(file);
		workBook.write(outStream);
		outStream.flush();
		outStream.close();*/
	}

	public static void exportExcelXls(String[] titles, String excelname, List<Map<String, String>> list,HttpServletResponse response) throws Exception {
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet();
		sheet.setDefaultColumnWidth(15);
		// workBook.setSheetName(0,"小表格");
		HSSFRow titleRow = sheet.createRow(0);
		for (int i = 0; i < titles.length; i++) {
			titleRow.createCell(i).setCellValue(titles[i]);
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			HSSFRow row = sheet.createRow(i + 1);
			for (int j = 0; j < titles.length; j++) {
				String title = titles[j];
				if (map.containsKey(title)) {
					row.createCell(j).setCellValue(map.get(title));
				}
			}
		}
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + new String(excelname.getBytes("UTF-8"),"iso-8859-1") );
		OutputStream ouputStream = response.getOutputStream();
		workBook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
		/*File file = new File("D:/"+excelname);
		FileOutputStream outStream = new FileOutputStream(file);
		workBook.write(outStream);
		outStream.flush();
		outStream.close();*/
	}
	
	/**
	 * 读取excel模版   导出excel
	 * 
	 * @author anbaihong
	 * @date 2018年12月26日 下午3:28:21
	 * @param titles		String[] titles = { "身份证号", "手机号", "邮箱", "QQ号" }; 
	 * @param excelname		excel文件名
	 * @param list			
	 * @param request
	 * @param response
	 * @throws Exception
	 * @comment
	 */
	public static void exportTemplateExcel(String[] titles, String excelname, List<Map<String, String>> list,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ext = excelname.substring(excelname.lastIndexOf("."),excelname.length());
		if(".xlsx".equals(ext)){
			exportTemplateExcelXlsx(titles,excelname,list,request,response);
		}else if(".xls".equals(ext)){
			exportTemplateExcelXls(titles,excelname,list,request,response);
		}else{
			System.out.println("不是excel格式，无法导出");
		}
	}	
	
	public static void exportTemplateExcelXlsx(String[] titles, String excelname, List<Map<String, String>> list,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String templatePath = request.getSession().getServletContext().getRealPath("downModel.xlsx");
		XSSFWorkbook workBook = new XSSFWorkbook(new FileInputStream(templatePath));
		XSSFSheet sheet = workBook.getSheetAt(0);
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			//从第几行开始写入数据
			XSSFRow row = sheet.createRow(i + 1);
			for (int j = 0; j < titles.length; j++) {
				String title = titles[j];
				if (map.containsKey(title)) {
					row.createCell(j).setCellValue(map.get(title));
				}
			}
		}
		//String filename = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xlsx";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + new String(excelname.getBytes("UTF-8"),"iso-8859-1") );
		OutputStream ouputStream = response.getOutputStream();
		workBook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
  		
		/*File file = new File("D:/"+excelname);
		FileOutputStream outStream = new FileOutputStream(file);
		workBook.write(outStream);
		outStream.flush();
		outStream.close();*/
	}

	public static void exportTemplateExcelXls(String[] titles, String excelname, List<Map<String, String>> list,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String templatePath = request.getSession().getServletContext().getRealPath("downModel.xls");
		HSSFWorkbook workBook = new HSSFWorkbook(new FileInputStream(templatePath));
		HSSFSheet sheet = workBook.getSheetAt(0);
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			HSSFRow row = sheet.createRow(i + 1);
			for (int j = 0; j < titles.length; j++) {
				String title = titles[j];
				if (map.containsKey(title)) {
					row.createCell(j).setCellValue(map.get(title));
				}
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + new String(excelname.getBytes("UTF-8"),"iso-8859-1") );
		OutputStream ouputStream = response.getOutputStream();
		workBook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
		
		/*File file = new File("D:/"+excelname);
		FileOutputStream outStream = new FileOutputStream(file);
		workBook.write(outStream);
		outStream.flush();
		outStream.close();*/
	}	
	
	
	
}
