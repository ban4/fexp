package com.datou.file.model;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import com.github.crab2died.annotation.ExcelField;

/**
 * excel
 * @author 
 */
public class Excel implements Serializable {
    private Integer id;

    @ExcelField(title = "身份证号",order = 1)
    @Pattern(regexp="^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)$",message="请填写正确的18位身份证号")
    //cardid = "";  用于反射导出excel时 需要=""  其余不需要
    private String cardid = "";

    @ExcelField(title = "手机号",order = 2)
    @Pattern(regexp="^1[34578]\\d{9}$",message="请填写正确的11位手机号")
    private String phone = "";

    @ExcelField(title = "邮箱",order = 3)
    @Pattern(regexp="^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$",message="邮箱格式不正确")
    private String email = "";

    @ExcelField(title = "QQ号",order = 4)
    @Pattern(regexp="[1-9][0-9]{4,14}",message="请填写正确的QQ号")
    private String qq = "";

    private String excelid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getExcelid() {
        return excelid;
    }

    public void setExcelid(String excelid) {
        this.excelid = excelid;
    }

	@Override
	public String toString() {
		return "Excel [id=" + id + ", cardid=" + cardid + ", phone=" + phone + ", email=" + email + ", qq=" + qq
				+ ", excelid=" + excelid + "]";
	}
    
    
}