package com.datou.file.model;

import java.io.Serializable;

/**
 * user
 * @author 
 */
public class User implements Serializable {
    /**
     * 主键自增，无意义
     */
    private Integer id;

    private String username;

    private String password;

    /**
     * excelid用于关联
     */
    private String excelid;
    
    private String excelname;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExcelid() {
        return excelid;
    }

    public void setExcelid(String excelid) {
        this.excelid = excelid;
    }

	public String getExcelname() {
		return excelname;
	}

	public void setExcelname(String excelname) {
		this.excelname = excelname;
	}
    
}