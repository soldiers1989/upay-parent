package com.upay.batch.stepservice.schedule;


import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.po.gnr.GnrSysinfoPo;
import com.upay.commons.constants.DataBaseConstants_BATCH;


/**
 * 日切结束
 * 
 * @author wh_zouyw
 * @version v1.0
 * @CreateDate: 2015-01-09
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:yyyy-mm-dd
 * @UpdateRemark:修改具体的内容；
 */
public class CutEnd extends AbstractStepExecutor<Object, Object> {

    
    @Override
    public void execute(BatchParams batchParams, int index, Object data, Object object) throws BatchException {
    	logger.info("批量-核算-日切结束-->开始");
        // 取当前系统信息
        GnrSysinfoPo gnrSysinfo = this.daoService.selectOne(new GnrSysinfoPo());
        if (gnrSysinfo == null) {
            throw new BatchException("系统信息表参数未配置");
        }

        // 检查当前系统状态是否为C-日切
        if (!DataBaseConstants_BATCH.SYS_STAT_CUT.equals(gnrSysinfo.getSysStat())) {
            throw new BatchException("当前系统状态是[" + gnrSysinfo.getSysStat() + "],系统状态必须是["
                    + DataBaseConstants_BATCH.SYS_STAT_CUT + "]才能执行日切结束");
        }

        GnrSysinfoPo gnrSysinfo4Upd = new GnrSysinfoPo();
        gnrSysinfo4Upd.setId(gnrSysinfo.getId());
        // 更新系统状态（C改为N）
        gnrSysinfo4Upd.setSysStat(DataBaseConstants_BATCH.SYS_STAT_NORMAL);
        // 保存日切结束执行结果信息
        this.daoService.update(gnrSysinfo4Upd);
    }


    @Override
    public void updateObject(BatchParams batchParams, Object object) {
    	/*GnrParmPo po=new GnrParmPo();
		po.setParmId(DataBaseConstants_AMG.GNR_PARAM_ID_TIMEOUT);
		po = daoService.selectOne(po);
		long sleepTime;
		String paramValue = po.getParmValue();
		if(po != null && StringUtils.isNotBlank(paramValue)){
			sleepTime = Long.parseLong(paramValue);
		}else{
			//未配置则默认10秒钟
			sleepTime = DataBaseConstants_AMG.AMG_WAIT_TIME_LAG;
		}
		sleepTime *= 1000;
        try {
            Thread.sleep(sleepTime); // 延迟N秒，等候在途交易
            this.logger.info("日切结束执行完成。");
        } catch (InterruptedException e) {
            this.logger.info("日切结束执行完成，等候在途交易延时失败。");
            e.printStackTrace();
        }*/
    }
}
