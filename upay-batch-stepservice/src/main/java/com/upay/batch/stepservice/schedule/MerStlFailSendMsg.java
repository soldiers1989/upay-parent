package com.upay.batch.stepservice.schedule;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.StlBookPo;
/*
 * 当商户结算失败时，自动发送短信提醒    该批量放在结算批量完成后
 */
public class MerStlFailSendMsg extends AbstractStepExecutor<Object, StlBookPo>{
	
	@Resource(name = "SA_GNR_smsSendService")
	private IDipperHandler<Message> smsSendService;
	
	//针对结算不成功的商户发送短信
	@Override
	public void execute(BatchParams batchParams, int index, StlBookPo data,
			Object object) throws BatchException {
		//查询未结算成功的商户结算信息.
		HashMap<String, Object> parm=new HashMap<>();
		parm.put("stat", "0");
		List<StlBookPo> stlBookPoList = daoService.selectList(StlBookPo.class.getName().concat(".queryNoStlMer"),parm);
		
		StringBuffer sb=new StringBuffer();
//		for(StlBookPo StlBookPo:stlBookPoList){
//			sb.append(StlBookPo.getMerNo()+",");
//		}
		if(stlBookPoList!=null&&stlBookPoList.size()>0){
			
			//读取是否发送短信
			GnrParmPo gnrParm=new GnrParmPo();
			gnrParm.setParmId("GET_SMS_FLAG");
			gnrParm=daoService.selectOne(gnrParm);
			//当配置存在，并且发送短信为true时，发送短信
			if(gnrParm!=null&&"true".equals(gnrParm.getParmValue())){
				sb.append("日期："+DateUtil.format(batchParams.getTranDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
				sb.append("  部分商户结算未成功,请即时检查。");
				
				//读取接收短信人
				gnrParm=new GnrParmPo();
				gnrParm.setParmId("MER_STL_FAIL_MOBILE");
				gnrParm=daoService.selectOne(gnrParm);
				
				if(null==gnrParm){
					throw new BatchException("结算失败设置短信接收手机号码。");
				}
				
				HashMap<String,Object> parmMap=new HashMap<String,Object>();
				parmMap.put("tranCode", "610006");
				parmMap.put("branchNo", "1010");
				
				
				parmMap.put("phoneNo", gnrParm.getParmValue());
				parmMap.put("sendMsg", sb.toString());
				
				//发送短信
				 Message message = getMessage(parmMap);
				 try {
					message = this.smsSendService.handle(message);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				 HashMap<String,Object> bodys = (HashMap<String,Object>)message.getTarget().getBodys();
					
				String code = message.getFault().getCode();
				String msg = message.getFault().getMsg();
				
				logger.debug("调用发送短信服务接口：返回代码="+code+"      返回信息="+msg);
				if(CommonConstants_GNR.RSP_CODE_SUCCESS.equals(code)){
					logger.info("商户结算失败发送短信成功。");
				}else{
					logger.error("商户结算失败发送短信失败。");
				}
			}else{
				logger.info("短信配置为不发送短信。。。。");
			}
		}
	}
	 private Message getMessage(Map<String,Object> body){
	        Message message =
	                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
	                        new HashMap<String,Object>(), body), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
	                    new HashMap<String, Object>(), body), FaultFactory.create(
	                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
	        return message;
	    }
}
