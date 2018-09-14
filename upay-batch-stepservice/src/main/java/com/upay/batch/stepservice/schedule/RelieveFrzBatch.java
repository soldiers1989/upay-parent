package com.upay.batch.stepservice.schedule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.acc.AccFrzBookPo;
import com.upay.dao.po.acc.AccFrzListPo;
import com.upay.dao.po.acc.AccVbookPo;


/**
 * 解冻
 * 
 * @author liyulong
 * 
 */
public class RelieveFrzBatch extends AbstractStepExecutor<Object, AccFrzBookPo> {
    @Resource
    private IDaoService daoService;


    @Override
    public int getTotalResult(BatchParams batchParams, Object object) throws BatchException {
        Map<String, Object> selectMap = new HashMap<String, Object>();
        // 查询锁定条数
        selectMap.put("isUnfrzs", new String[] { "0", "2" });
        return daoService.selectList(AccFrzBookPo.class.getName() + ".lists", selectMap).size();
    }


    @Override
    public List<AccFrzBookPo> getDataList(BatchParams batchParams, int offset, int pageSize, Object object)
            throws BatchException {
        Map<String, Object> selectMap = new HashMap<String, Object>();
        // 查询锁定条数
        selectMap.put("isUnfrzs", new String[] { "0", "2" });
        List<AccFrzBookPo> selectList =
                daoService.selectList(AccFrzBookPo.class.getName() + ".lists", selectMap);
        return selectList;
    }


    @Override
    public void execute(BatchParams batchParams, int index, AccFrzBookPo accFrzBookPo, Object object)
            throws BatchException {
        // 如果锁定时间过去，解付
        Date sysTime = SysInfoContext.getSysDate(); // 系统日期
        if (null != accFrzBookPo && StringUtils.isNotBlank(accFrzBookPo.getUserId())) {
            String userId = accFrzBookPo.getUserId();// 用户ID
            String frzNo = accFrzBookPo.getFrzNo();// 冻结编号
            if (StringUtils.isBlank(frzNo)) {
                throw new BatchException("冻结编号不能为空");
            }
            AccVbookPo accVbookPo3 = new AccVbookPo();
            accVbookPo3.setUserId(userId);
            accVbookPo3 = daoService.selectOne(accVbookPo3);
            if (accVbookPo3 == null) {
                throw new BatchException("该用户未开通虚拟账户");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("frzNo", frzNo);
            map.put("frzTypes", new String[] { "0", "1" });
            AccFrzListPo accFrzListPo = new AccFrzListPo();
            List<AccFrzListPo> accFrzListPos = new ArrayList<AccFrzListPo>();
            accFrzListPos = daoService.selectList(AccFrzListPo.class.getName() + ".order", map);
            if (accFrzListPos != null & accFrzListPos.size() > 0) {// 取出编号最大的记录
                accFrzListPo = accFrzListPos.get(0);
                if (sysTime.compareTo(accFrzListPo.getFrzEndDate()) > 0) {
                    // 插入新数据账户冻结明细登记薄
                    AccFrzListPo accFrzListPo2 = new AccFrzListPo();
                    accFrzListPo2.setFrzNo(accFrzListPo.getFrzNo());
                    accFrzListPo2.setFrzOrderNum(accFrzBookPo.getFrzOrderNum() + 1);
                    accFrzListPo2.setFrzType("2");// 解冻
                    accFrzListPo2.setFrzStat("0");// 正常
                    if (accFrzListPo.getFrzStat().equals("4")) {// 金额冻结
                        accFrzListPo2.setFrzAmt(accVbookPo3.getFrzBal());
                        accFrzListPo2.setUnfrzAmt(accVbookPo3.getFrzBal());// 解冻金额
                    }
                    accFrzListPo2.setFrzActiveDate(accFrzListPo.getFrzActiveDate());
                    accFrzListPo2.setFrzEndDate(accFrzListPo.getFrzEndDate());
                    accFrzListPo2.setFrzFileNo(accFrzListPo.getFrzFileNo());
                    accFrzListPo2.setFrzAuthority(accFrzListPo.getFrzAuthority());
                    accFrzListPo2.setFrzAuthorityName(accFrzListPo.getFrzAuthorityName());
                    accFrzListPo2.setFrzAuthorityCode(accFrzListPo.getFrzAuthorityCode());
                    accFrzListPo2.setFrzOper(accFrzListPo.getFrzOper());
                    accFrzListPo2.setFrzCertNo(accFrzListPo.getFrzCertNo());
                    accFrzListPo2.setFrzCertType(accFrzListPo.getFrzCertType());
                    accFrzListPo2.setFrzMode(accFrzListPo.getFrzMode());
                    accFrzListPo2.setPartFrzTime(accFrzListPo.getPartFrzTime());
                    accFrzListPo2.setFrzReason("到期批量解冻");
                    accFrzListPo2.setPartFrzTime(accFrzListPo.getPartFrzTime());
                    accFrzListPo2.setOper("SYSTEM");
                    accFrzListPo2.setOperTime(SysInfoContext.getSysTime());
                    daoService.insert(accFrzListPo2);

                    // 更新账户冻结登记簿
                    accFrzBookPo.setIsUnfrz("1");// 已解冻
                    accFrzBookPo.setRemainFrzAmt(new BigDecimal(0));
                    accFrzBookPo.setFrzOrderNum(accFrzBookPo.getFrzOrderNum() + 1);
                    AccFrzBookPo accFrzBookPo2 = new AccFrzBookPo();
                    accFrzBookPo2.setFrzNo(frzNo);
                    daoService.update(accFrzBookPo, accFrzBookPo2);

                    // 更新虚拟账户信息表
                    AccVbookPo accVbookPo = new AccVbookPo();
                    accVbookPo.setFrzStat("0");// 正常
                    accVbookPo.setFrzBal(new BigDecimal(0));
                    AccVbookPo accVbookPo2 = new AccVbookPo();
                    accVbookPo2.setUserId(userId);
                    daoService.update(accVbookPo, accVbookPo2);

                }
            }
        }
    }
}
