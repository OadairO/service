package com.supermap.too;

/**
 * easyui的datagrid向后台传递参数使用的model
 * 
 * @author
 * 
 */
public class TableVoo {

	   private int code;
	   private String msg;//
	   private Object count;//总条数
	   private Object data;//数据集合
	   private int page = 1;//当前页
	   private int limit = 10;//每页数量
	   private String sortValue;//排序值
	   private String sort = "ASC";//排序字段
	   private String kstime;//开始时间
	   private String jstime;//结束时间
	   
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getCount() {
		return count;
	}
	public void setCount(Object count) {
		this.count = count;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getKstime() {
		return kstime;
	}
	public void setKstime(String kstime) {
		this.kstime = kstime;
	}
	public String getJstime() {
		return jstime;
	}
	public void setJstime(String jstime) {
		this.jstime = jstime;
	}
	public String getSortValue() {
		return sortValue;
	}
	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	   
}
