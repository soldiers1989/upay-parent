package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseQueryDto;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 	资金结算登记薄 (T_STL_BOOK)
 * 
 * @author liudan
 * 
 */
public class StlBookListQueryDto extends BaseQueryDto {
    
	/*批次号*/
   private String stlBatchNo;
   /*状态*/
   private String stat;
   /*商户号*/

    private String merNo;

    //二级商户号
    private String   secMerNo;

    public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    private String startDate;
    private String endDate;

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStlBatchNo() {
        return stlBatchNo;
    }

    public void setStlBatchNo(String stlBatchNo) {
        this.stlBatchNo = stlBatchNo;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    private List<Map<String,Object>> transList;

    public List<Map<String, Object>> getTransList() {
        return transList;
    }

    public void setTransList(List<Map<String, Object>> transList) {
        this.transList = transList;
    }



    
    
  

}
