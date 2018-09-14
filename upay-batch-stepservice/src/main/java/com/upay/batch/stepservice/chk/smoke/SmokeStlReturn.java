/**
 * 
 */
package com.upay.batch.stepservice.chk.smoke;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.FtpUtil;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.pay.SmokeStlDetailPo;

/**
 * 返回转账回盘文件 供烟草下载
 * 
 * @author lb
 * 
 */
public class SmokeStlReturn extends AbstractStepExecutor<Object, Object> {
	private final static Logger logger = LoggerFactory.getLogger(SmokeStlReturn.class);
	@Resource
    IDaoService daoService;
	
	private String serverUrl;
	private String serverPort;
	private String userName;
	private String password;
	private String remotePath;
	
	@Override
	public void execute(BatchParams batchParams, int index, Object data,Object object) throws BatchException {
		
		
		String preDayStr = DateUtil.format(batchParams.getPreDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD);
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("stlStartDate", DateUtil.format(batchParams.getTranDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		map.put("stlEndDate", DateUtil.format(DateUtil.add(batchParams.getTranDate(), Calendar.DAY_OF_MONTH, 1), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		List<SmokeStlDetailPo> smokeStlList = daoService.selectList(SmokeStlDetailPo.class.getName() + ".selectSmokeStlByDate",map);
		String fileName="STL_"+preDayStr+"_RET.txt";
		if(smokeStlList.size()>0){
				StringBuffer sb=new StringBuffer();
				for(SmokeStlDetailPo smokeStlPo:smokeStlList){
					
					sb.append(smokeStlPo.getSeqNo()+"|");
					sb.append(smokeStlPo.getTransType()+"|");;
					sb.append(smokeStlPo.getBankFlag()+"|"); 
					Date upayDate = smokeStlPo.getUpayDate();
					if(upayDate!=null){
						sb.append(DateUtil.format(upayDate, DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD)+"|");
					}
					sb.append(smokeStlPo.getUpaySeq()+"|");
					sb.append(smokeStlPo.getPayerMerNo()+"|");
					Date merDate = smokeStlPo.getMerDate();
					if(merDate!=null){
						sb.append(DateUtil.format(merDate, DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD)+"|");
					}
					MerAcctInfoPo merAcctInfoPo=new MerAcctInfoPo();
					merAcctInfoPo.setMerNo(smokeStlPo.getPayerMerNo());
					merAcctInfoPo = daoService.selectOne(merAcctInfoPo);
					if(merAcctInfoPo==null){
						new BatchException("商户:"+smokeStlPo.getPayerMerNo()+" 商户账务信息表信息未配置!");
					}else{
						
						sb.append(merAcctInfoPo.getStlAcctType()+"|");
					}
					
					sb.append(smokeStlPo.getMerSeq()+"|");
					sb.append(smokeStlPo.getPayerCardType()+"|");
					sb.append(smokeStlPo.getPayerAcctNo()+"|");
					sb.append(smokeStlPo.getPayerName()+"|");
					sb.append(smokeStlPo.getPayeeMerNo()+"|");
					sb.append(smokeStlPo.getPayeeCardType()+"|");
					sb.append(smokeStlPo.getPayeeAcctNo()+"|");
					sb.append(smokeStlPo.getPayeeName()+"|");
					sb.append(smokeStlPo.getTransAmt()+"|");
					sb.append(smokeStlPo.getFeeAmt()+"|");
					String remark=smokeStlPo.getRemark1();
					if(StringUtils.isBlank(remark)){
						remark="";
					}
					sb.append(remark+"|");
					sb.append(smokeStlPo.getResult());
					sb.append("\n");
				}
				logger.info("文件内容:::::"+sb.toString());
				ByteArrayInputStream bais=new ByteArrayInputStream(sb.toString().getBytes());
				FtpUtil.upFile(serverUrl, Integer.valueOf(serverPort), userName, password,remotePath, bais,fileName);
		}else{
			logger.info(DateUtil.format(batchParams.getTranDate(), "yyyy-MM-dd")+"没有烟草转账记录");
		}
	}

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
	
}
