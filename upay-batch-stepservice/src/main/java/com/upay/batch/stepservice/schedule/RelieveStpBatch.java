package com.upay.batch.stepservice.schedule;

import java.util.Calendar;
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
import com.upay.commons.util.DateUtil;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.acc.AccStpBookPo;
import com.upay.dao.po.acc.AccVbookPo;


/**
 * 解付
 * 
 * @author liyulong
 * 
 */
public class RelieveStpBatch extends AbstractStepExecutor<Object, AccStpBookPo> {
    @Resource
    private IDaoService daoService;


    @Override
    public int getTotalResult(BatchParams batchParams, Object object) throws BatchException {
        Map<String, Object> selectMap = new HashMap<String, Object>();
        selectMap.put("stpMode", 1);// 临时止付
        selectMap.put("stpStats", new String[] { "1", "2" });
        // AccStpBookPo accStpBookPo = new AccStpBookPo();
        // // 查询锁定条数
        // accStpBookPo.setStpStat("(1,2)");
        // accStpBookPo.setStpMode("1");// 临时止付
        return daoService.selectList(AccStpBookPo.class.getName() + ".lists", selectMap).size();
    }


    @Override
    public List<AccStpBookPo> getDataList(BatchParams batchParams, int offset, int pageSize, Object object)
            throws BatchException {
        Map<String, Object> selectMap = new HashMap<String, Object>();
        selectMap.put("stpMode", 1);// 1：临时止付
        selectMap.put("stpStats", new String[] { "1", "2" });
        // AccStpBookPo accStpBookPo = new AccStpBookPo();
        // // 查询锁定记录
        // accStpBookPo.setStpStat("(1,2)");
        // accStpBookPo.setStpMode("1");// 临时止付
        List<AccStpBookPo> selectList =
                daoService.selectList(AccStpBookPo.class.getName() + ".lists", selectMap, offset, pageSize);
        return selectList;
    }


    @Override
    public void execute(BatchParams batchParams, int index, AccStpBookPo accStpBookPo, Object object)
            throws BatchException {
        // 如果锁定时间过去，解付
        Date sysTime = SysInfoContext.getSysTime(); // 系统时间
        if (null != accStpBookPo && StringUtils.isNotBlank(accStpBookPo.getUserId())) {
            Date stpTime = accStpBookPo.getStpTime(); // 止付时间
            int partStpTime = accStpBookPo.getPartStpTime();// 止付时效
            Date relieveTime = DateUtil.add(stpTime, Calendar.HOUR, partStpTime);// 解付时间
            if (sysTime.compareTo(relieveTime) >= 0) {
                accStpBookPo.setStpStat("0");// 正常：0
                accStpBookPo.setPayAmt(accStpBookPo.getStpAmt());
                accStpBookPo.setUnstpTime(sysTime);
                accStpBookPo.setUnstpReason("到期批量解付");
                AccStpBookPo accStpBookPo2 = new AccStpBookPo();
                accStpBookPo2.setStpNo(accStpBookPo.getStpNo());
                daoService.update(accStpBookPo, accStpBookPo2);

                AccVbookPo accVbookPo = new AccVbookPo();
                accVbookPo.setStopStat("0");// 正常：0
                AccVbookPo accVbookPo2 = new AccVbookPo();
                accVbookPo2.setUserId(accStpBookPo.getUserId());
                daoService.update(accVbookPo, accVbookPo2);
            }
        }
    }
}
