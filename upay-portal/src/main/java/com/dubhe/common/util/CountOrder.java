package com.dubhe.common.util;


/**
 * 
 * 
 * @author Hing <xingguang.ren@pactera.com>
 *
 */
public class CountOrder {
   private int mysqlstart;
   private int mysqllimit;
    private int start=0;
    private int limit = 10;
    private int end;
    private String sort;
    private String order="desc";
    //sort:positionName,positionNo
    //order:asc
    
    public int getStart() {
    	
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getMysqlstart() {
		return mysqlstart;
	}

	public void setMysqlstart(int mysqlstart) {
		this.mysqlstart = mysqlstart;
	}

	public int getMysqllimit() {
		return mysqllimit;
	}

	public void setMysqllimit(int mysqllimit) {
		this.mysqllimit = mysqllimit;
	}

}
