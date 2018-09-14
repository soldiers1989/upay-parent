package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.busi.pay.service.dto.StlBookListDto;
import com.upay.busi.pay.service.dto.StlBookListQueryDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.StlBookPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by xuxin 资金结算登记薄查询 下载
 */
public class StlBookListQueryDownloadService extends AbstractDipperHandler<StlBookListQueryDto> {
    private static final Logger LOG = LoggerFactory.getLogger(StlBookListQueryDto.class);

    @Resource
    private IDaoService daoService;


    @Override
    public StlBookListQueryDto execute(StlBookListQueryDto stlBookListQueryDto, Message message)
            throws Exception {
        LOG.info("=====资金结算登记薄 下载开始====");
        List<Map<String, Object>> transList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stat", stlBookListQueryDto.getStat());
        map.put("stlBatchNo", stlBookListQueryDto.getStlBatchNo());
        //设置系统时间
        stlBookListQueryDto.setSysDate(new Date());
        if (null != stlBookListQueryDto.getStartDate() && null != stlBookListQueryDto.getEndDate()) {
            SimpleDateFormat sdfFormat = new SimpleDateFormat(DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdfFormat.parse(stlBookListQueryDto.getStartDate()));
            cal.set(Calendar.HOUR, 00);
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.SECOND, 00);
            stlBookListQueryDto.setQueryStart(cal.getTime());
            cal.setTime(sdfFormat.parse(stlBookListQueryDto.getEndDate()));
            cal.set(Calendar.HOUR, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            stlBookListQueryDto.setQueryEnd(cal.getTime());
        }
        map.put("orderStartDate", stlBookListQueryDto.getQueryStart());
        map.put("orderEndDate", stlBookListQueryDto.getQueryEnd());
        LOG.info(" queryBegin = " + stlBookListQueryDto.getQueryStart() + " queryEnd = "
                + stlBookListQueryDto.getQueryEnd());
        String merNo = stlBookListQueryDto.getMerNo();
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "商户号不能为空");
        }
        map.put("merNo", merNo);
        String secMerNo = stlBookListQueryDto.getSecMerNo();
        map.put("secMerNo", secMerNo);


        //根据stlDate 排序
        List<Map<String, String>> orderByList = new ArrayList<Map<String, String>>();
        Map<String, String> orderByStlDate = new HashMap<String, String>();
        orderByStlDate.put("columnName", "STL_DATE");
        orderByStlDate.put("sort", "desc");
        orderByList.add(orderByStlDate);
        map.put("orderBy", orderByList);

        List<StlBookPo> listPayOrderListPo =
                daoService.selectList(StlBookPo.class.getName() + ".selectList_Extra", map);
        if (null != listPayOrderListPo && listPayOrderListPo.size() > 0) {
            for (StlBookPo stlBookPo : listPayOrderListPo) {
                StlBookListDto stlBookListDto=new StlBookListDto();
                BeanCopyUtil.copyBean2Bean(StlBookPo.class,stlBookPo,StlBookListDto.class,stlBookListDto);
                stlBookListDto.setStlDate( DateUtil.format(stlBookPo.getStlDate(),"yyyy-MM-dd HH:mm:ss"));
                String stat = stlBookPo.getStat();
                if(StringUtils.isNotBlank(merNo)){
                    if("0".equals(stat)){
                        stlBookListDto.setStat("登记");
                    }else  if("1".equals(stat)){
                        stlBookListDto.setStat("完成（入账)");
                    }else if("2".equals(stat)){
                        stlBookListDto.setStat("处理中");
                    }
                }
               /*保留两位小数*/
                BigDecimal payAmt = stlBookPo.getPayAmt();
                BigDecimal payFee = stlBookPo.getPayFee();
                BigDecimal revAmt = stlBookPo.getRevAmt();
                BigDecimal revFee = stlBookPo.getRevFee();
                BigDecimal barAmt = stlBookPo.getBarAmt();
                if(payAmt!=null){
                    stlBookListDto.setPayAmt(payAmt.setScale(2,BigDecimal.ROUND_HALF_UP));
                }else{
                    stlBookListDto.setPayAmt(new BigDecimal(0.00).setScale(2,BigDecimal.ROUND_HALF_UP));
                }
                if(payFee!=null){
                    stlBookListDto.setPayFee(payFee.setScale(2,BigDecimal.ROUND_HALF_UP));
                }else{
                    stlBookListDto.setPayFee(new BigDecimal(0.00).setScale(2,BigDecimal.ROUND_HALF_UP));
                }
                if(revAmt!=null){
                    stlBookListDto.setRevAmt(revAmt.setScale(2,BigDecimal.ROUND_HALF_UP));
                }else{
                    stlBookListDto.setRevAmt(new BigDecimal(0.00).setScale(2,BigDecimal.ROUND_HALF_UP));
                }
                if(revFee!=null){
                    stlBookListDto.setRevFee(revFee.setScale(2,BigDecimal.ROUND_HALF_UP));
                }else{
                    stlBookListDto.setRevFee(new BigDecimal(0.00).setScale(2,BigDecimal.ROUND_HALF_UP));
                }
                if(barAmt!=null){
                    stlBookListDto.setBarAmt(barAmt.setScale(2,BigDecimal.ROUND_HALF_UP));
                }else{
                    stlBookListDto.setBarAmt(new BigDecimal(0.00).setScale(2,BigDecimal.ROUND_HALF_UP));
                }

                transList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(stlBookListDto));
            }
            stlBookListQueryDto.setTransList(transList);
        }
        stlBookListQueryDto.setTotalNum(listPayOrderListPo.size());
        LOG.info("=====资金结算登记薄 下载结束====");
        return stlBookListQueryDto;
    }
}
