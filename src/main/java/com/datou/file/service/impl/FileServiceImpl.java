package com.datou.file.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datou.file.dao.ExcelDao;
import com.datou.file.dao.UserDao;
import com.datou.file.model.Excel;
import com.datou.file.model.User;
import com.datou.file.service.FileService;
import com.datou.file.util.ExcelUtil;

/**
 * @author anbaihong
 * @date 2018年12月6日 下午4:23:06
 * @comment 
 */
@Service("fileService")
public class FileServiceImpl implements FileService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ExcelDao excelDao;
	
	//excelid生成策略 根据时间生成
	public String excelid(){
		long l = System.currentTimeMillis();
		Date date = new Date(l);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String id = dateFormat.format(date);
		return id;
	}
	
	public String addFile(User u, String url) {
		int result = 0;
		String eid = excelid();
		File file = new File(url);
		
		if(!file.isDirectory() && file.exists()){
			FileInputStream in = null;
			try {
				in = new FileInputStream(file);
				List<Excel> excList = new ArrayList<Excel>();
				ExcelUtil<Excel> excUtil = new ExcelUtil<Excel>();
				
				//反射解析excel	
				String s = excUtil.readExcel(in, Excel.class, excList);
				if (StringUtils.isBlank(s)) {
					int counts = 0;
					List<Excel> tempList = new ArrayList<Excel>();
					if (excList.size() > 0) {
						for (int i = 0; i < excList.size(); i++) {
							excList.get(i).setExcelid(eid);
							tempList.add(excList.get(i));
							counts++;
							if (counts % 3000 == 0) {
								result = excelDao.insertBatch(tempList);
								tempList.clear();
							}
						}
						// 除不尽 剩余的数据进行插入
						if (0 != tempList.size()) {
							result = excelDao.insertBatch(tempList);
							tempList.clear();
						}
					}
				} else {
					return s;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (result > 0){
			u.setExcelid(eid);
			result = userDao.insertSelective(u);
		}
		if (result > 0) {
			return "1";
		} else {
			return null;
		}
		
	}

	public List<User> queryList() {
		return userDao.queryList();
	}
	

	public List<User> queryExcelName(String excelId) {
		return userDao.queryExcelName(excelId);
	}

	public List<Map<String, String>> exportExcel(String excelId) {
        List<Excel> list = excelDao.queryExportExcel(excelId);  
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();  
        for (Excel sk : list) {
            Map<String, String> map = new HashMap<String, String>();  
            //map的key 一定与 controller  titles对应
            map.put("身份证号", sk.getCardid());
            map.put("手机号", sk.getPhone());
            map.put("邮箱", sk.getEmail());
            map.put("QQ号", sk.getQq());
            
            mapList.add(map);  
        }  
        return mapList; 
	}

	public List<Excel> queryExcel(String excelId) {
		List<Excel> list = excelDao.queryExportExcel(excelId);  
		return list;
	}


	

}
