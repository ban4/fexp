package com.datou.file.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.datou.file.model.Excel;

@Repository
public interface ExcelDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Excel record);

    int insertSelective(Excel record);

    Excel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Excel record);

    int updateByPrimaryKey(Excel record);

    //插入
	int insertBatch(List<Excel> tempList);

	List<Excel> queryExportExcel(String excelId);
}