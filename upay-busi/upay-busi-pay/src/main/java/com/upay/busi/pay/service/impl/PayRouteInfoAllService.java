/**
 * 
 */
package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.BasePo;
import com.upay.busi.pay.service.dto.PayRouteInfoAllDto;
import com.upay.busi.pay.service.dto.PayRouteInfoQryDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.PayRouteInfoPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 资金通道信息查询所有
 * 
 * @author zhanggr
 * 
 */
public class PayRouteInfoAllService extends AbstractDipperHandler<PayRouteInfoAllDto> {
    private static final Logger LOG = LoggerFactory.getLogger(PayRouteInfoAllService.class);
    @Resource
    private IDaoService daoService;


    @Override
    public PayRouteInfoAllDto execute(PayRouteInfoAllDto payRouteInfoAllDto,Message message) throws Exception {

       PayRouteInfoPo payRouteInfoPo=new PayRouteInfoPo();
        List<PayRouteInfoPo> payRouteInfoPos = daoService.selectList(payRouteInfoPo);
        List<Map<String,Object>> payRouteInfosTemp =new ArrayList<>();

        if (payRouteInfoPos.size() != 0) {
            PayRouteInfoAllDto temp=new PayRouteInfoAllDto();
            for (int i = 0; i < payRouteInfoPos.size(); i++) {
                temp.setRouteCode(payRouteInfoPos.get(i).getRouteCode());
                temp.setRouteName(payRouteInfoPos.get(i).getRouteName());
                payRouteInfosTemp.add(BeanCopyUtil.copyBean2MapStrObjNoClass(temp));

            }
            payRouteInfoAllDto.setPayRouteInfos(payRouteInfosTemp);
        }
        if (null != payRouteInfosTemp) {
            payRouteInfoAllDto.setTotalNum(payRouteInfosTemp.size());
        }
        return  payRouteInfoAllDto;
    }
}
