package com.upay.busi.gnr.service.dto;


import java.util.List;

import com.upay.commons.dto.BaseDto;


/**
 * 获取地区码对应地区集合
 * @author freeplato
 *
 */
public class AreaCodeListGetDto extends BaseDto {

	/**
	 * 地区代码
	 */
	private String areaCode;
	/**
     * 县地区代码名称
     */
    private String countyAreaName;
	public String getCountyAreaName() {
        return countyAreaName;
    }

    public void setCountyAreaName(String countyAreaName) {
        this.countyAreaName = countyAreaName;
    }

    /**
     * 省地区代码
     */
    private String  provinceAreaCode;
    /**
     * 省地区代名称
     */
    private String  provinceAreaName;
    public String getProvinceAreaCode() {
        return provinceAreaCode;
    }

    public void setProvinceAreaCode(String provinceAreaCode) {
        this.provinceAreaCode = provinceAreaCode;
    }

    public String getProvinceAreaName() {
        return provinceAreaName;
    }

    public void setProvinceAreaName(String provinceAreaName) {
        this.provinceAreaName = provinceAreaName;
    }

    public String getCityAreaCode() {
        return cityAreaCode;
    }

    public void setCityAreaCode(String cityAreaCode) {
        this.cityAreaCode = cityAreaCode;
    }

    public String getCityAreaName() {
        return cityAreaName;
    }

    public void setCityAreaName(String cityAreaName) {
        this.cityAreaName = cityAreaName;
    }

    /**
     * 市地区代码
     */
    private String  cityAreaCode;
    /**
     * 市地区名称
     */
    private String  cityAreaName;
	
	/**
	 * 省集合
	 */
	private List<AreaCodeGetSubDto> provinceList;

	/**
	 * 市集合
	 */
	private List<AreaCodeGetSubDto> cityList;

	/**
	 * 区集合
	 */
	private List<AreaCodeGetSubDto> countyList;



	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public List<AreaCodeGetSubDto> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<AreaCodeGetSubDto> provinceList) {
		this.provinceList = provinceList;
	}

	public List<AreaCodeGetSubDto> getCityList() {
		return cityList;
	}

	public void setCityList(List<AreaCodeGetSubDto> cityList) {
		this.cityList = cityList;
	}

	public List<AreaCodeGetSubDto> getCountyList() {
		return countyList;
	}

	public void setCountyList(List<AreaCodeGetSubDto> countyList) {
		this.countyList = countyList;
	}

}
