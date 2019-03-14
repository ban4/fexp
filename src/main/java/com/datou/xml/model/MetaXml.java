package com.datou.xml.model;

import java.io.Serializable;

public class MetaXml implements Serializable {
    private Integer id;

    /**
     * 地区编码
     */
    private String regionId;

    /**
     * 地区中文名
     */
    private String regionChname;

    /**
     * 地区英文名
     */
    private String regionEnname;

    /**
     * 城市编码
     */
    private String cityId;

    /**
     * 城市中文名
     */
    private String cityChname;

    /**
     * 城市英文名
     */
    private String cityEnname;

    /**
     * 所属省份
     */
    private String cityProvince;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionChname() {
        return regionChname;
    }

    public void setRegionChname(String regionChname) {
        this.regionChname = regionChname;
    }

    public String getRegionEnname() {
        return regionEnname;
    }

    public void setRegionEnname(String regionEnname) {
        this.regionEnname = regionEnname;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityChname() {
        return cityChname;
    }

    public void setCityChname(String cityChname) {
        this.cityChname = cityChname;
    }

    public String getCityEnname() {
        return cityEnname;
    }

    public void setCityEnname(String cityEnname) {
        this.cityEnname = cityEnname;
    }

    public String getCityProvince() {
        return cityProvince;
    }

    public void setCityProvince(String cityProvince) {
        this.cityProvince = cityProvince;
    }
}