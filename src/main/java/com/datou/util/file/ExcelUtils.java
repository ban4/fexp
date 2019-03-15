package com.datou.util.file;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
/**
 * excel的 导入 、导出 工具类
 * 
 * @author anbaihong
 * @date 2018年8月8日 下午5:54:00
 * @comment 
 */
public class ExcelUtils {
    private final static String excel2003L = ".xls"; // 2003- 版本的excel  
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel  
  
    /**
     * 导入excel：通过流（InputStream）方式读取
     * 
     * @author anbaihong
     * @date 2019年3月15日 下午4:11:26
     * @param in
     * 				文件流
     * @param fileName	
     * 				文件名
     * @return
     * @throws Exception
     * @comment
     */
    public static List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception {  
        List<List<Object>> list = null;  
        // 创建Excel工作薄  
        Workbook work = getWorkbook(in,fileName);  
		if (null == work) {
			throw new Exception("创建Excel工作薄为空！");
		}
        Sheet sheet = null;  
        Row row = null;  
        Cell cell = null;  
        list = new ArrayList<List<Object>>();  
        /*
         * 遍历Excel中所有的sheet ：
         * 		for (int i = 0; i < work.getNumberOfSheets(); i++) {  
         * 如果就一个sheet页（防止多个空sheet页）：
         * 		for (int i = 0; i < 1; i++) {  
         */
        for (int i = 0; i < work.getNumberOfSheets(); i++) {  
            sheet = work.getSheetAt(i);  
            if (sheet == null) {  
                continue;  
            }
            int maxColumn = sheet.getRow(1).getLastCellNum();	//最大列
            // 遍历当前sheet中的所有行  
            // 包涵头部，所以要小于等于最后一列数,这里也可以在初始值加上头部行数，以便跳过头部  
            for (int j = 2; j <= sheet.getLastRowNum(); j++) {  
                // 读取一行  
                row = sheet.getRow(j);  
                // 去掉空行和表头   根据excel 模版 去掉前两行（j = 2）
                if (row == null) {  
                    continue;  
                }  
                // 遍历所有的列  
                List<Object> li = new ArrayList<Object>();  
                for (int y = 0; y < maxColumn; y++) {     //row.getLastCellNum(); 最大列数  
					cell = row.getCell(y); 	// 且 考虑到导入的excel单元格内可以不写东西得情况
					if (cell == null) { 	// 所以直接用 明确的数值来确定遍历的次数
						li.add(""); 		// 这里添加了单元格中空白的判断，如果空白默认赋值为null,避免后面mybatis里面出现错误
					} else {
						li.add(getCellValue(cell));  
					} 
                }  
                list.add(li);  
            }  
        }  
        return list;  
    }  
  
    /** 
     * 描述：根据文件后缀，自适应上传文件的版本 
     */  
	public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
		Workbook wb = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (excel2003L.equals(fileType)) {
			wb = new HSSFWorkbook(inStr); // 2003-
		} else if (excel2007U.equals(fileType)) {
			wb = new XSSFWorkbook(inStr); // 2007+
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return wb;
	}  
  
    /** 
     * 描述：对表格中数值进行格式化 
     */  
    public static Object getCellValue(Cell cell) {  
        Object value = null;  
        DecimalFormat df = new DecimalFormat("0"); // 格式化字符类型的数字  
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化  
        DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字  
        switch (cell.getCellType()) {  
        case Cell.CELL_TYPE_STRING:  
            value = cell.getRichStringCellValue().getString();  
            break;  
        case Cell.CELL_TYPE_NUMERIC:  
            if ("General".equals(cell.getCellStyle().getDataFormatString())) {  
                value = df.format(cell.getNumericCellValue());  
            } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {  
                value = sdf.format(cell.getDateCellValue());  
            } else {  
                value = df2.format(cell.getNumericCellValue());  
            }  
            break;  
        case Cell.CELL_TYPE_BOOLEAN:  
            value = cell.getBooleanCellValue();  
            break;  
        case Cell.CELL_TYPE_BLANK:  
            value = "";  
            break;  
        default:  
            break;  
        }  
        return value; 
    } 
    
  
    
    
    /**
     * 导出excel
     * 
     * @author anbaihong
     * @date 2019年3月15日 下午4:18:53
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
	 * @date 2019年3月15日 下午4:23:33
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
