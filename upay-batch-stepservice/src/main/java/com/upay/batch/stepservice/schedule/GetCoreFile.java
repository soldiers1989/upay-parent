/**
 * 
 */
package com.upay.batch.stepservice.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.upay.commons.util.FileUtil;
import com.upay.commons.util.FtpUtil;
import com.pactera.dipper.batch.service.IStepService;
/**
 *  获取 核心对账文件 
 * @author lb
 *
 */
public class GetCoreFile  extends AbstractStepExecutor<Object, Object>{
	 private final static Logger logger = LoggerFactory.getLogger(GetCoreFile.class);
//	 @Resource(name="coreCliDipperHandler")
//	 private IDipperHandler<Message> coreCliDipperHandler;
	@Override
	public void execute(BatchParams batchParams, int index, Object data,
			Object object) throws BatchException {
		
		System.out.println("哈 哈 哈 哈哈 哈 哈 ");
		
        //到核心取对账的文件    该文件包括 支付和充值，提现的交易明细
		//第一步 调用核心的接口获取对账的文件名
//        Message msg=MessageFactory.create(IdGenerateFactory.generateId(), "corecli", "XML", "UTF-8",
//                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
//                        new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
//                        "交易成功"));
//        Map<String, Object> body=(Map<String, Object>) msg.getTarget().getBodys();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
//        Date date =batchParams.getPreDate();
//        body.put("tranCode", "08010");// 内部交易代码，获取模板用
//        body.put("machineDate", dateFormat.format(date));
//        body.put("machineTime", timeFormat.format(date));
//        body.put("bizDate", dateFormat.format(date));
//        body.put("channelId", "74");
//        body.put("bizSerialNo", batchParams.getBatchNo());
//        body.put("bankDate", dateFormat.format(date)); // 业务日期
//        body.put("bizType", "0"); // 0-借记卡快捷支付 1-贷记卡快捷支付
//        body.put("setAccount", "1010001300116000011"); // 内部账户
//		try {
//			Message handle = coreCliDipperHandler.handle(msg);
//			Map<String,Object> bodys = (Map<String, Object>) handle.getTarget().getBodys();
//			String ftpFileName = (String) bodys.get("fileName");
//			if(StringUtils.isNotBlank(ftpFileName)){
//				//第二步   根据接口返回的文件 名 从FTP server 下载文件
//				String localPath = null;
//				String fileName = null;
//				String remotePath = null;
//				String password = null;
//				String username = null;
//				int port = 0;
//				String url = null;
//				boolean downFile = FtpUtil.downFile(url, port, username, password, remotePath, fileName, localPath);
//				if(!downFile){
//					//下载失败提示信息
//					logger.error("从FTP Server获取对账文件失败");
//					throw new BatchException("从FTP Server获取对账文件失败");
//				}else{
//					//第三步 下载成功   读取文件内容存入数据库
//					
//				}
//			}else{
//				logger.error("未取得日期为:"+dateFormat.format(date)+"的对账文件");
//				throw new BatchException("未取得日期为:"+dateFormat.format(date)+"的对账文件");
//			}
//		} catch (Exception e) {
//			logger.error("批量==============获取核心对账文件出现错误",e);
//			e.printStackTrace();
//		}
	}
}
