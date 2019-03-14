package com.datou.file.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.datou.file.model.User;
import com.datou.file.service.FileService;
import com.datou.file.util.ExportExcelUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 对EXCEL进行操作：
 * 		读取input框上传excel  通过二进制流读取excel  暂存临时路径
 * 		解析excel文件	
 * 		生成excel文件
 * 
 * 对应数据库：
 * 		user	excel
 * 
 * @author anbaihong
 * @date 2019年2月23日 上午10:15:14
 * @comment
 */
@Controller
@RequestMapping(value="/file")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	//跳转文件下载及上传页面
	@RequestMapping("/upload")
	public String upload(){
		return "ajaxfile";
	}
	
	//1.读取ajaxFileUpload中input域中文件，上传临时目录
	@RequestMapping(value="/tempFile",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String tempFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
		String name = file.getOriginalFilename() + "_=_split_=_" + UUID.randomUUID().toString();
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		String url = request.getSession().getServletContext().getRealPath(File.separator+"temp");
		File fdir = new File(url);
		if (!fdir.exists()) {
			fdir.mkdirs();
		} else {
			File[] subFiles = fdir.listFiles();
			for (File subFile : subFiles) {
				if (subFile.isFile()) {
					subFile.delete();
				}
			}
		}
		file.transferTo(new File(url+"/" + name + "." + ext));
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", "temp/"+name+"."+ext);
		//response.getWriter().write(resultJson.toString()); return null;
		return resultJson.toString();
	}
	//2.读取二进制流 还原文件 上传临时目录
	@RequestMapping("/readIoFile")
	public String readIoFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String param = request.getParameter("param");	//通过json发送ajax至后台的二进制流
		String excelName = request.getParameter("excelName");
		String name = excelName + "_=_split_=_" + UUID.randomUUID().toString();
		String url = request.getSession().getServletContext().getRealPath(File.separator+"temp");
		File fdir = new File(url);
		if (!fdir.exists()) {
			fdir.mkdirs();
		} else {
			File[] subFiles = fdir.listFiles();
			for (File subFile : subFiles){
				if (subFile.isFile()){
					subFile.delete();
				}
			}
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			ByteArrayInputStream byteis = new ByteArrayInputStream(param.getBytes("ISO-8859-1"));
			bis = new BufferedInputStream(byteis);
			bos = new BufferedOutputStream(new FileOutputStream(new File(url+"/"+name+".xlsx")));
			byte[] buffer = new byte[1024];
			int length = bis.read(buffer);
			while(length!=-1){
				bos.write(buffer,0,length);
				length = bis.read(buffer);
			}
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				bos.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", "temp/"+name+".xlsx");
		response.getWriter().write(resultJson.toString());
		return null;
	}
	/**
	 * 上传 用户信息 及 excel 信息
	 * 
	 * @author anbaihong
	 * @date 2018年12月6日 下午4:25:38
	 * @param request
	 * @param response
	 * @param usrname
	 * @param password
	 * @param excelFile
	 * @return
	 * @comment
	 */
	@RequestMapping(value="/addFile",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String addFile(HttpServletRequest request,HttpServletResponse response,
			String username,String password,String excelFile){
		
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setExcelname( excelFile.split("/")[1].split("_=_split_=_")[0] );
		
		String base = request.getSession().getServletContext().getRealPath(File.separator+"/");
		String url = base + excelFile;
		
		String s = fileService.addFile(u,url);
		JSONObject result = new JSONObject();
		if (StringUtils.isBlank(s)) {
			result.put("result", 0);
		} else if ("1".equals(s)) {
			result.put("result", 1);
		} else {
			result.put("result", s);
		}
		return result.toJSONString();
	}	
	
	//跳转列表
	@RequestMapping("/list")
	public String goList(){
		return "list";
	}
	
	@RequestMapping(value="/queryList",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String queryList(@RequestParam(value="pn",defaultValue="1")Integer pn){
		PageHelper.startPage(pn, 10);
		List<User> user = fileService.queryList();
		PageInfo<User> page = new PageInfo<User>(user, 5);
		JSONObject result = new JSONObject();
		result.put("pageInfo", page);
		return result.toString();
	}
	
	@RequestMapping("/exportExcel")
	@ResponseBody
    public void exportExcel(String excelId,HttpServletRequest request,HttpServletResponse response) throws Exception {  
        List<Map<String, String>> list = fileService.exportExcel(excelId); 
        String[] titles = { "身份证号", "手机号", "邮箱", "QQ号" };  
        List<User> user = fileService.queryExcelName(excelId);
        //ExportExcelUtil.exportExcel(titles, user.get(0).getExcelname(), list, response);
        //按模版导出 
        ExportExcelUtil.exportTemplateExcel(titles, user.get(0).getExcelname(), list, request, response);
    }  
	
	//poi封装导出
	/*	@RequestMapping("/exportExcel")
    public ModelAndView exportExcel(ModelMap map,String excelId) throws Exception {  
        List<Map<String, String>> list = fileService.exportExcel(excelId); 
        String[] titles = { "身份证号", "手机号", "邮箱", "QQ号" };  
        List<User> user = fileService.queryExcelName(excelId);
        ViewExcel excel = new ViewExcel(titles,use r.get(0).getExcelname());
        map.put("excelList", list);  
        return new ModelAndView(excel, map); 
    } */
	
	//反射方式	不读取模版 导出excel  	com.github.crab2died.annotation.ExcelField
/*	@RequestMapping("/exportExcel")
	@ResponseBody
	private void exportExcel(HttpServletRequest request,HttpServletResponse response,String excelId){
		List<Excel> excelList = fileService.queryExcel(excelId);
		
		try {
			List<User> user = fileService.queryExcelName(excelId);
			String filename = user.get(0).getExcelname();	// 正常.xlsx
			//String filename = "D:\\a.xlsx";
			ExcelUtils.getInstance().exportObjects2Excel(excelList, Excel.class, true, null, true, filename);
			//String templatePath = request.getSession().getServletContext().getRealPath("downModel.xlsx");
			//String templatePath = "/downModel.xlsx";
			//ExcelUtils.getInstance().exportObjects2Excel(templatePath, 0, excelList, null, Excel.class, false, "D:\\a.xlsx");
			FileInputStream fis = new FileInputStream(new File(filename));
			byte[] buffer = readInputStream(fis);
			
			response.reset();
			response.setHeader("Content-disposition", "attachment;filename="+new String(filename.getBytes("UTF-8"),"iso-8859-1") );
			response.addHeader("Content-Length", "" + buffer.length);
	        response.setContentType("application/octet-stream;charset=UTF-8");  
	          
	        OutputStream ouputStream = new BufferedOutputStream(response.getOutputStream());
	        ouputStream.write(buffer);
	        ouputStream.flush();  
	        ouputStream.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private byte[] readInputStream(InputStream is) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		is.close();
		return outStream.toByteArray();
	}
*/	
}
