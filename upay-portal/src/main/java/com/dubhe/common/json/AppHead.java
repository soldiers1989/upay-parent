package com.dubhe.common.json;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Hing<xingguang.ren@pactera.com>
 * @since 2014年4月19日
 */
public class AppHead{

	/** 总条数 */
    @JSONField(name = "TOTAL_NUM")
    private Integer totalNum;
    
    /** 当前页 */
    @JSONField(name = "PAGE_INDEX")
    private Integer pageIndex;
    
    /** 当前记录数 */
    @JSONField(name = "CURRENT_NUM")
    private Integer currentNum;

    public AppHead() {
        super();
    }

    public AppHead(Integer totalNum, Integer pageIndex,Integer currentNum) {
        super();
        this.totalNum = totalNum;
        this.pageIndex = pageIndex;
        this.currentNum = currentNum;
    }

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(Integer currentNum) {
		this.currentNum = currentNum;
	}

}
