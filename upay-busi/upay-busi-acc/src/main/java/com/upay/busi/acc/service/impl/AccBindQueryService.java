package com.upay.busi.acc.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.busi.acc.service.dto.AccBindInfoDto;
import com.upay.busi.acc.service.dto.AccBindQueryDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccBindBookPo;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.pay.ChannelLogoBookPo;
import com.upay.dao.po.pay.PayCardbinInfoPo;
import com.upay.dao.po.pay.PayRouteCtlInfoPo;


/**
 * 查询绑卡信息
 * 
 * @author shangqiankun
 * @version 创建时间：2016年7月21日 上午10:09:59
 */
public class AccBindQueryService extends AbstractDipperHandler<AccBindQueryDto> {

    private final static Logger log = LoggerFactory.getLogger(BindBookService.class);
    @Resource
    private IDaoService daoService;


    @Override
    public AccBindQueryDto execute(AccBindQueryDto dto, Message message) throws Exception {
        log.info("<----------Accbind query---------->");
        String userId = dto.getUserId();
        if (StringUtils.isNotBlank(userId)) {
            AccVbookPo acc = new AccVbookPo();
            acc.setUserId(userId);
            acc.setVacctStat(DataBaseConstans_ACC.ACC_V_BOOK_VSTAT_NOMAL);
            acc = daoService.selectOne(acc);
            if (acc != null) {
                List<Map<String, Object>> dtoList = new ArrayList<Map<String, Object>>();
                AccBindBookPo bind = new AccBindBookPo();
                // List<AccBindInfoDto> dtoList = new
                // ArrayList<AccBindInfoDto>();
                String queryFlag = dto.getQueryFlag();
                bind.setBindAcctType(queryFlag);// 根据前端传入的绑定卡类型查询
                bind.setVacctNo(acc.getVacctNo());
                bind.setBindStat(DataBaseConstans_ACC.BIND_STAT_BINDED_ACTIVATED);
                List<AccBindBookPo> bindList = daoService.selectList(bind);
                if (bindList != null && bindList.size() > 0) {
                    /**
                     * 静态资源服务器域名
                     */
                    String imgDomainName = (String) DipperParm.getParmByKey(CmparmConstants.IMG_DOMAIN_NAME);

                    SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMD);
                    SimpleDateFormat simTime =
                            new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
                    for (AccBindBookPo b : bindList) {
                        AccBindInfoDto info = new AccBindInfoDto();
                        PayCardbinInfoPo card = new PayCardbinInfoPo();
                        card.setCardBin(b.getCardBin());
                        card = daoService.selectOne(card);
                        if (card == null) {
                            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "卡bin：" + b.getCardBin());
                        }

                        ChannelLogoBookPo logo = new ChannelLogoBookPo();
                        logo.setLogoId(card.getLogoId());
                        logo.setChannelId(dto.getChnlId());
                        logo = daoService.selectOne(logo);
                        if (logo == null) {
                            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "logo：" + card.getLogoId());
                        }
                        info.setLogoClass(imgDomainName + logo.getLogoClass());// 图片LOGO的服务器地址(全路径)
                        info.setLogoName(logo.getLogoName());
                        if (b.getBindTime() != null) {
                            info.setBindTime(sim.format(b.getBindTime()));
                        }

                        // 设置资金通道
                        PayRouteCtlInfoPo ctrlInfoPo = new PayRouteCtlInfoPo();
                        ctrlInfoPo.setCardBin(b.getCardBin());
                        ctrlInfoPo.setRouteStat(DataBaseConstants_PAY.ROUTE_STAT_NORMAL);
                        ctrlInfoPo.addOrder(Order.asc("payPrity"));
                        List<PayRouteCtlInfoPo> routeInfoList = daoService.selectList(ctrlInfoPo);
                        if (null != routeInfoList && routeInfoList.size() > 0) {
                            info.setRouteCode(routeInfoList.get(0).getRouteCode());
                        }

                        info.setVbindBankName(b.getVbindBankName());
                        info.setBindChnlId(b.getBindChnlId());
                        info.setVacctNo(b.getVacctNo());
                        info.setDefaultFlag(b.getDefaultFlag());
                        info.setBindAcctType(b.getBindAcctType());
                        info.setVbindBankFlag(b.getVbindBankFlag());
                        info.setVbindBankCode(b.getVbindBankCode());
                        info.setVbindBankName(b.getVbindBankName());
                        info.setVbindAcctNo(b.getVbindAcctNo());
                        info.setVbindOpenCode(b.getVbindOpenCode());
                        info.setBindStat(b.getBindStat());
                        info.setVbindFlag(b.getVbindFlag());
                        info.setThirdAuthChnl(b.getThirdAuthChnl());
                        info.setTransferVerifyAmt(b.getTransferVerifyAmt() == null ? "0" : b
                            .getTransferVerifyAmt().doubleValue() + "");
                        if (b.getTransferVerifyDate() != null) {
                            info.setTransferVerifyDate(sim.format(b.getTransferVerifyDate()));
                        }
                        if (b.getActiveTime() != null) {
                            info.setActiveTime(simTime.format(b.getActiveTime()));
                        }
                        if (b.getUnbindTime() != null) {
                            info.setUnbindTime(simTime.format(b.getUnbindTime()));
                        }
                        info.setUnbindChnlId(b.getUnbindChnlId());
                        info.setUnbindReasonFlag(b.getUnbindReasonFlag());
                        Map<String, Object> subDtoMap = BeanCopyUtil.copyBean2MapStrObjNoClass(info);
                        dtoList.add(subDtoMap);
                    }

                    dto.setvAcctNo(acc.getVacctNo());
                    dto.setBindCardList(dtoList);
                    dto.setCardNum(bindList.size());
                } else {
                    dto.setBindCardList(null);
                    dto.setCardNum(0);
                }
            } else {
                dto.setBindCardList(null);
                dto.setCardNum(0);
            }

        } else {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "用户id");
        }
        return dto;
    }

}
