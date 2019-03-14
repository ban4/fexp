package com.datou.file.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.datou.file.model.User;
@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    
    int insertSelective(User record);

	List<User> queryList();

	List<User> queryExcelName(String excelId);
}