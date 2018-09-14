/**
 * 
 */
package com.upay.batch.stepservice.chk.smoke;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Calendar;
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
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.exception.PersistenceException;
import com.pactera.dipper.page.QueryResult;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.FtpUtil;
import com.upay.dao.po.chk.StlBookPo;
import com.upay.dao.po.pay.SmokeStlDetailPo;
import com.upay.dao.po.pay.SmokeStlPo;

/**
 * 获取烟草转账明细文件
 * 
 * @author lb
 * 
 */
public class GetSmokeFile extends AbstractStepExecutor<Object, Object> {
	private final static Logger logger = LoggerFactory
			.getLogger(GetSmokeFile.class);
	@Resource
	private IDaoService daoService;
	private BufferedReader buffRead;

	private String serverUrl;
	private String serverPort;
	private String userName;
	private String password;
	private String remotePath;
	private String localPath;

	@Override
	public void execute(BatchParams batchParams, int index, Object data,
			Object object) throws BatchException {
		
		//判断是否是指定日期执行
    	if(batchParams.getParameter().containsKey("tranDate")){
    		Date tranDate = (Date)batchParams.getParameter().get("tranDate");
    		Date preDate = (Date)batchParams.getParameter().get("preDate");
    		batchParams.setTranDate(tranDate);
    		batchParams.setPreDate(preDate);
    	}
		
		//检查当日是否己清算过，如果未清算则不能做转账操作
		StlBookPo book=new StlBookPo();
		book.setStlDate(batchParams.getTranDate());
		book.setStat(DataBaseConstants_BATCH.STL_STAT_COMPLETED);
		QueryResult<StlBookPo> queryResult = daoService.selectQueryResult(book, 0, 0);
		long totalrecord = queryResult.getTotalrecord();
		logger.info("当日商户的结算数记录为。。。。。。。。。。"+totalrecord);
		if(totalrecord<=0){
			logger.info("当日未做商户的结算不能做转账操作。。。。。。。。。。");
			return;
		}
		
		logger.info("从FTP 服务器获取烟草清算转账文件。。。。。。。。。。");
		String preDay = DateUtil.format(batchParams.getPreDate(),
				DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD);
		String fileName = "STL_" + preDay + ".txt";
		HashMap<String, BigDecimal> transMap=new HashMap<String,BigDecimal>();
		boolean downFile = FtpUtil.downFile(serverUrl,Integer.valueOf(serverPort), userName, password, remotePath,fileName, localPath);
		// boolean downFile =true;
		if (downFile) {
			logger.info("下载文件成功:::::::" + localPath);
			
			//查看当日记录是否己存在
//			String transDate = DateUtil.format(batchParams.getTranDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD);
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("stlStartDate",transDate);
//			map.put("stlEndDate", DateUtil.format(DateUtil.add(
//					batchParams.getTranDate(), Calendar.DAY_OF_MONTH, 1),DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
////			long result=daoService.selectQueryResult(SmokeStlPo.class.getName() + ".selectSmokeStlByTransDate", map,0,0).getTotalrecord();
//			List<SmokeStlDetailPo> smokeStlDetailList = daoService.selectList(SmokeStlDetailPo.class.getName() + ".selectSmokeStlByDate", map);
//			if(smokeStlDetailList!=null&&smokeStlDetailList.size()>0){
//				//为了保证数据完整性，对于己导入的数据先删除，再重新导入
//				logger.info("日期:"+transDate+"     己导入烟草批量转账记录     删除操作");
//				for(SmokeStlDetailPo smokeStlDetail:smokeStlDetailList){
//					daoService.delete(smokeStlDetail);
//				}
//			}
			InputStreamReader isr = null;
			try {
				File file = new File(localPath + fileName);
				if(!file.exists()){
					//如果当日转账文件不存在，则返回继续下一步
					logger.info("::::::" + preDay+"   转账文件不存在");
					return;
				}
				isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
				buffRead = new BufferedReader(isr);
				String msg = null;
				while (null != (msg = buffRead.readLine())) {
					logger.info("文件内容:::::::" + msg);
					if (StringUtils.isNotBlank(msg)) {
						String[] stlData = msg.split("\\|");
						String seqNo = stlData[0];
						String transType = stlData[1];
						String bankFlag = stlData[2];
						String payerMerNoStr = stlData[3];
						String merDate = stlData[4];
						String merSeq = stlData[5];
						String payerCardType = stlData[6];
						String payerAcctNo = stlData[7];
						String payerName = stlData[8];
						String payeeMerNo = stlData[9];
						String payeeCardType = stlData[10];
						String payeeAcctNo = stlData[11];
						String payeeName = stlData[12];
						BigDecimal transAmt = BigDecimal.ZERO;
						BigDecimal feeAmt = BigDecimal.ZERO;
						
						
						if (StringUtils.isNotBlank(stlData[13])) {
							transAmt = new BigDecimal(stlData[13]);
						}
						if (StringUtils.isNotBlank(stlData[14])) {
							feeAmt = new BigDecimal(stlData[14]);
						}
						
						String upaySeq=stlData[15];
						String reslut=stlData[16];
						String bankId=stlData[17];
								
						//商户流水己导入过的数据不再重复导入
						SmokeStlDetailPo chkSmokeStlPo = new SmokeStlDetailPo();
						chkSmokeStlPo.setMerSeq(merSeq);
						chkSmokeStlPo=daoService.selectOne(chkSmokeStlPo);
						if(chkSmokeStlPo==null){
							SmokeStlDetailPo smokeStlPo = new SmokeStlDetailPo();
							smokeStlPo.setSeqNo(seqNo);
							smokeStlPo.setTransType(transType);
							smokeStlPo.setBankFlag(bankFlag);
							smokeStlPo.setPayerMerNo(payerMerNoStr);
							smokeStlPo.setMerDate(DateUtil.parse(merDate,
									DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD));
							smokeStlPo.setMerSeq(merSeq);
							smokeStlPo.setPayerCardType(payerCardType);
							smokeStlPo.setPayerAcctNo(payerAcctNo);
							smokeStlPo.setPayerName(payerName);
							smokeStlPo.setPayeeMerNo(payeeMerNo);
							smokeStlPo.setPayeeCardType(payeeCardType);
							smokeStlPo.setPayeeAcctNo(payeeAcctNo);
							smokeStlPo.setPayeeName(payeeName);
							smokeStlPo.setTransAmt(transAmt);
							smokeStlPo.setFeeAmt(feeAmt);
							
							smokeStlPo.setUpaySeq(upaySeq);
							smokeStlPo.setResult(reslut);
							smokeStlPo.setRemark2(bankId);

							daoService.insert(smokeStlPo);
						}else{
							logger.info("商户流水："+merSeq+"    己导入不需重复导入============");
						}
						
						BigDecimal transferAmt = (BigDecimal)transMap.get(payerMerNoStr);
						if(null==transferAmt){
							transMap.put(payerMerNoStr, transAmt);
						}else{
							transferAmt=transferAmt.add(transAmt);
							transMap.put(payerMerNoStr, transferAmt);
						}
						// stlList.add(smokeStlPo);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new BatchException(preDay+" 导入烟草转账数据失败:" + e.getMessage());
				
			}
			
//			if (null!=transMap&&transMap.size()>0) {
////				Date date = DateUtil.parse(transDate, DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD);
//				// 登记需要转账的烟草商户
//				for (Map.Entry<String, BigDecimal> entry : transMap.entrySet()) {
//					SmokeStlPo smokeStl=new SmokeStlPo();
//					smokeStl.setMerNo(entry.getKey());
//					smokeStl.setTransAmt(entry.getValue());
//					smokeStl.setTransDate(batchParams.getTranDate());
//					daoService.insert(smokeStl);
//				}
//			}else{
//				logger.info("日期::::::"+transDate+":::::没有需要转账的烟草商户");
//			}
		} else {
			logger.info("下载文件失败:::::::" + fileName);
			throw new BatchException(preDay+"下载烟草转账文件失败:::::::" + fileName);
		}
	}

//	 public static void main(String[] args) {
//	 boolean downFile = FtpUtil.downFile("197.4.11.15", 21, "test",
//	 "Tyzfptyy1tstkf#15",
//	 "/home/test/", "STL_20170220.txt", "D:/tst.txt");
//	 boolean downFile = FtpUtil.downFile("197.5.11.24", 21, "appsvr",
//	 "appsvr",
//	 "/home/zxin10/portal_file/SMOKE_STL/", "STL_20170222.txt",
//	 "D:/FTPServer");
//	 System.out.println(downFile);
//	 GetSmokeFile f = new GetSmokeFile();
//	 f.execute(null, 0, null, null);
//		 File file=new File("D:/1.pfx");
//		 System.out.println(file.exists());
//	 }

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

}
