package cn.digitalpublishing.springmvc.form.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class LAccessForm extends BaseForm{
	
	private String year="2013";//起始年
	
	private String endyear;//结束年

	private String[] month2Display;
	
	private String startMonth="01";
	
	private String endMonth="12";
	
	private int startYear=2013;
	
	private int endYear=2030;
	
	private String startyear;//年(YYYY)
	private String endtyear;//年(YYYY)
	
	private List<String> yearList = new ArrayList<String>();
	
	private List<String> monthList = new ArrayList<String>();
	private Integer type;//类型：1、访问记录统计 2、浏览统计3、搜索统计
	private Integer pubtype;//1-图书;2-期刊
	private Integer languagetype;//1-中文图书统计;2-外文图书统计 
	private Integer access;//1-成功;2-拒访
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String[] getMonth2Display() {
		return month2Display;
	}
	public void setMonth2Display(String[] month2Display) {
		this.month2Display = month2Display;
	}
	public String getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public String getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public List<String> getYearList() {
		return yearList;
	}
	public void setYearList(List<String> yearList) {
		this.yearList = yearList;
	}
	public List<String> getMonthList() {
		return monthList;
	}
	public void setMonthList(List<String> monthList) {
		this.monthList = monthList;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPubtype() {
		return pubtype;
	}
	public void setPubtype(Integer pubtype) {
		this.pubtype = pubtype;
	}
	public Integer getLanguagetype() {
		return languagetype;
	}
	public void setLanguagetype(Integer languagetype) {
		this.languagetype = languagetype;
	}
	public Integer getAccess() {
		return access;
	}
	public void setAccess(Integer access) {
		this.access = access;
	}
	public String getEndyear() {
		return endyear;
	}
	public void setEndyear(String endyear) {
		this.endyear = endyear;
	}
	public String getStartyear() {
		return startyear;
	}
	public void setStartyear(String startyear) {
		this.startyear = startyear;
	}
	public String getEndtyear() {
		return endtyear;
	}
	public void setEndtyear(String endtyear) {
		this.endtyear = endtyear;
	}
	
	
}
