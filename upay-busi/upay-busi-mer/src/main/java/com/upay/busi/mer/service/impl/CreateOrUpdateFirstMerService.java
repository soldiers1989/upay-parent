/**
 * 
 */
package com.upay.busi.mer.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.mer.service.dto.CreateOrUpdateFirstMerDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.Md5Util;
import com.upay.dao.ISequenceService;
import com.upay.dao.ParmsContext;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.mer.MerApplyBookPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.mer.MerChannelMenuPo;
import com.upay.dao.po.mer.MerOpenPayPo;
import com.upay.dao.po.mer.MerPlatSettingPo;
import com.upay.dao.po.mer.MerTransCtrlPo;
import com.upay.dao.po.mer.MerTransLimitPo;
import com.upay.dao.po.mer.MerTransTemplatePo;
import com.upay.dao.po.pay.ChannelMenuBookPo;
import com.upay.dao.po.trans.TransTemplateCtrlPo;
import com.upay.dao.po.trans.TransTemplatePo;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrComRegRecPo;
import com.upay.dao.po.usr.UsrRegInfoPo;

/**
 * @author Administrator
 *登记一级商户信息
 */
public class CreateOrUpdateFirstMerService extends AbstractDipperHandler<CreateOrUpdateFirstMerDto>{
	private static final Logger log = LoggerFactory .getLogger(CreateOrUpdateFirstMerService.class);
	@Resource
	private IDaoService daoService;
	@Autowired
	private ISequenceService sequenceService;
	@Override
	public CreateOrUpdateFirstMerDto execute(CreateOrUpdateFirstMerDto dto,
			Message arg1) throws Exception {
		//参数效验
		checkParm(dto);
		
		//查检商户信息和用户信息是否存在
		String merNo=dto.getMerNo();
		MerBaseInfoPo mer=null;
		UsrRegInfoPo reg=null; 
		if(StringUtils.isNotBlank(merNo)){
			mer=new MerBaseInfoPo();
			mer.setMerNo(merNo);
			mer=daoService.selectOne(mer);
			if(null==mer){
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "商户【"+merNo+"】不存在]");
			}else{
				if(StringUtils.isBlank(mer.getUserId())){
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "商户【"+merNo+"】对应的用户ID不存在]");
				}else{
					reg=new UsrRegInfoPo();
					reg.setUserId(mer.getUserId());
					reg=daoService.selectOne(reg);
				}
			}
		}
		
		//不需要检查邮箱和手机号是唯一
//		if(StringUtils.isNotBlank(dto.getContactMobile())&&"A".equals(dto.getOperateFlag())){
//			UsrRegInfoPo usrReg=new UsrRegInfoPo();
//			usrReg.setMobile(dto.getContactMobile());
//			usrReg=daoService.selectOne(usrReg);
//			if(usrReg!=null){
//				ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"联系人手机号码["+dto.getContactMobile()+"]重复");
//			}
//			
//			usrReg=new UsrRegInfoPo();
//			usrReg.setMobile(dto.getContactEmail());
//			usrReg=daoService.selectOne(usrReg);
//			if(usrReg!=null){
//				ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"联系人邮箱["+dto.getContactMobile()+"]重复");
//			}
//		}
		
		
		
		//登记用户信息
		addUserInfo(dto,reg);
		//登记商户信息
		addMerInfo(dto,mer);
		return dto;
	}
	//登记用户信息
	private void addUserInfo(CreateOrUpdateFirstMerDto dto,UsrRegInfoPo reg){
		//用户注册信息登记
		String userId="";
		if(reg==null){//新增
			// 登记企业注册信息表：已生成用户
			UsrComRegRecPo usrComRegRecPo = new UsrComRegRecPo();
			usrComRegRecPo.setGenerateFlag(DataBaseConstants_USR.GENERATE_FLAG_YES);
			usrComRegRecPo.setComEmail(dto.getContactEmail());
			usrComRegRecPo.setSendNum(1);
			usrComRegRecPo.setTransCode(DataBaseConstants_USR.TRANS_CODE_SENDMAIL);
			usrComRegRecPo.setSid(Md5Util.toMD5(dto.getContactEmail() + "1"));
			 Date valiedDate = SysInfoContext.getSysTime();
			// 邮箱失效时效
	        int valiedTerm = new BigDecimal(ParmsContext.getParmByKey(CmparmConstants.VALIED_TERM).toString()).intValue();
	        valiedDate = DateUtil.add(valiedDate, Calendar.HOUR, valiedTerm);
	        usrComRegRecPo.setValiedDate(valiedDate);
	        usrComRegRecPo.setLastSendTime(SysInfoContext.getSysTime());
			
						
			reg = new UsrRegInfoPo();
			userId= sequenceService.generateUserId();
			GnrParmPo gnrParm=new GnrParmPo();
			gnrParm.setParmId(DateBaseConstants_MER.MER_DEFAULT_PASSWORD);
			gnrParm = daoService.selectOne(gnrParm);
			if(gnrParm!=null){
				reg.setLoginPwd(gnrParm.getParmValue());//设置商户登录的默认密码
			}
			reg.setUserId(userId);
			
			GnrParmPo parmPo = new GnrParmPo();
			parmPo.setParmId(CommonConstants_GNR.DEFAULT_HEAD_PIC);
			parmPo = daoService.selectOne(parmPo);
			if (null != parmPo) {
				reg.setHeadPic(parmPo.getParmValue());// 设置默认头像
			}
			
			reg.setUserNickName(dto.getUserNickName());
			String userName = getUserName();
			reg.setUserName(userName);
			reg.setRegTime(dto.getSysTime());
			reg.setRegChnlId(dto.getChnlId());
			reg.setActiveTime(dto.getSysTime());
			reg.setActiveChnlId(dto.getChnlId());
			reg.setMobile(dto.getContactMobile());
			reg.setComEmail(dto.getContactEmail());
			
			reg.setUserCertLevel(DataBaseConstants_USR.USER_CERT_LEVEL_MOBILE);
			reg.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
			reg.setUserLockFlag(DataBaseConstants_USR.USER_LOCK_FLAG_INIT);
			reg.setRegType(DataBaseConstants_USR.USER_REG_TYPE_MEMBER);
			reg.setBlacklistFlag(DataBaseConstants_USR.BLACK_LIST_FLAG_NO);
//			reg.setMerLevel(DataBaseConstants_USR.MER_LEVER_1);
			reg.setMerLevel(DataBaseConstants_USR.MER_LEVER_0);
			
			// 添加个人基本信息
			UsrBaseInfoPo base = new UsrBaseInfoPo();
			base.setUserId(userId);
			base.setCertName(dto.getContact());
			base.setSex(dto.getSex());
			base.setLastUpdateTime(dto.getSysTime());
			base.setAddressReal(dto.getAddress());
			base.setCertType(dto.getEgalPersonIdType());
			base.setCertNo(dto.getEgalPersonIdNo());
			
			daoService.insert(usrComRegRecPo);
			daoService.insert(reg);
			daoService.insert(base);
			dto.setUserId(userId);
		}else{//修改
			reg.setUserNickName(dto.getUserNickName());
//			reg.setUserName(dto.getContactEmail());
			reg.setRegTime(dto.getSysTime());
			reg.setRegChnlId(dto.getChnlId());
			reg.setActiveTime(dto.getSysTime());
			reg.setActiveChnlId(dto.getChnlId());
			reg.setMobile(dto.getContactMobile());
			reg.setComEmail(dto.getContactEmail());
			
			UsrRegInfoPo whereReg = new UsrRegInfoPo();
			whereReg.setId(reg.getId());
			
			
			//修改个人基本信息
			UsrBaseInfoPo base = new UsrBaseInfoPo();
			base.setCertName(dto.getContact());
			base.setSex(dto.getSex());
			base.setLastUpdateTime(dto.getSysTime());
			base.setAddressReal(dto.getAddress());
			base.setCertType(dto.getEgalPersonIdType());
			base.setCertNo(dto.getEgalPersonIdNo());
			
			UsrBaseInfoPo whereBase = new UsrBaseInfoPo();
			whereBase.setUserId(reg.getUserId());
			
			daoService.update(reg, whereReg);
			daoService.update(base, whereBase);
			dto.setUserId(reg.getUserId());
		}
		
		
	}
	//登记商户信息
	private void addMerInfo(CreateOrUpdateFirstMerDto dto,MerBaseInfoPo merBaseInfo){
			String merNo=null;
			Date now = SysInfoContext.getSysDate();
	        // 检查参数
	        String merName = dto.getMerName();
	        String merWithoutPwdSign = dto.getMerWithoutPwdSign();
	        String payopenflag = dto.getPayopenflag();
	        String merBusiType = dto.getMerBusiType();
	        String contact = dto.getContact();
	        String contactTel = dto.getContactTel();
	        String contactMobile = dto.getContactMobile();
	        String contactEmail = dto.getContactEmail();
	        String merTel = dto.getMerTel();
	        String websiteScop = dto.getWebsiteScop();
	        String companyName = dto.getCompanyName();
	        String egalPersonName = dto.getEgalPersonName();
	        String egalPersonIdType = dto.getEgalPersonIdType();
	        String egalPersonIdNo = dto.getEgalPersonIdNo();
	        String companyIdType = dto.getCompanyIdType();
	        String companyIdNo = dto.getCompanyIdNo();
	        String orgDeptNo = dto.getOrgDeptNo();
	        String busiLicenseId = dto.getBusiLicenseId();
	        String userId = dto.getUserId();
	        
	        
			if(null==merBaseInfo){//新增
//				merBaseInfo=new MerBaseInfoPo();
//				merBaseInfo.setMerName(merName);
//				merBaseInfo=daoService.selectOne(merBaseInfo);
//				if(null!=merBaseInfo){
//					ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"商户名称【"+merName+"】己存在!");
//				}
				
//				merBaseInfo=new MerBaseInfoPo();
				GnrParmPo parmPo=new GnrParmPo();
				parmPo.setParmId("MER_UAT_FLAG");
				parmPo=daoService.selectOne(parmPo);
				if(null==parmPo){
					 //一级商户号 生成规则：前三位为MER加6位序号，序号值长度小于6位左补0，示例：MER000001，递增值为1
					merNo="MER" + sequenceService.generateMerNoSeq();//商户号
				}else{
					//一级商户号 生成规则：前三位为MER加6位序号，序号值长度小于6位左补0，示例：MER000001，递增值为1
					merNo="UAT" + sequenceService.generateMerNoSeq();//商户号
				}
				
				
				
		        // 在商户表和商户申请表中查找是否已经成为一级商户
		        Map<String, Object> whereMap = new HashMap<String, Object>();
		        whereMap.put("userId", userId);
		        MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
		        merBaseInfoPo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap);
		        if (null != merBaseInfoPo) {
		            ExInfo.throwDipperEx(AppCodeDict.BISMER0001);
		        }
		        // 在商户表和商户申请表中查找是否有相同userId
		        MerApplyBookPo merApplyBookPo = new MerApplyBookPo();
		        merApplyBookPo = daoService.selectOne(MerApplyBookPo.class.getName() + ".selectOneMer", whereMap);
		        if (null != merApplyBookPo && !DateBaseConstants_MER.MER_APPLY_STAT_NO.equals(merApplyBookPo.getApplyState())) {
		            ExInfo.throwDipperEx(AppCodeDict.BISMER0001);
		        }
	
		        MerApplyBookPo merApplyBook = new MerApplyBookPo();
	
		        // 获取商户申请编号
		        String merApplyNo = sequenceService.generateMerApplyNo();
		        dto.setMerApplyNo(merApplyNo);
		        merApplyBook.setMerApplyNo(merApplyNo);
		        merApplyBook.setMerName(merName);
		        merApplyBook.setMerWithoutPwdSign(merWithoutPwdSign);
		        merApplyBook.setPayOpenFlag(payopenflag);
		        merApplyBook.setMerBusiType(merBusiType);
		        merApplyBook.setContact(contact);
		        merApplyBook.setContactTel(contactTel);
		        merApplyBook.setContactMobile(contactMobile);
		        merApplyBook.setContactEmail(contactEmail);
		        merApplyBook.setMerTel(merTel);
		        merApplyBook.setMerFax(dto.getMerFax());
		        merApplyBook.setMerAddr(dto.getMerAddr());
		        merApplyBook.setMerPostalCode(dto.getMerPostalCode());
		        merApplyBook.setWebsiteCode(dto.getWebsiteCode());
		        merApplyBook.setWebsiteName(dto.getWebsiteName());
		        merApplyBook.setWebsiteDomain(dto.getWebsiteDomain());
		        merApplyBook.setWebsiteScop(websiteScop);
		        merApplyBook.setCompanyName(companyName);
		        merApplyBook.setEgalPersonName(egalPersonName);
		        merApplyBook.setEgalPersonIdType(egalPersonIdType);
		        merApplyBook.setEgalPersonIdNo(egalPersonIdNo);
		        merApplyBook.setCompanyIdType(companyIdType);
		        merApplyBook.setCompanyIdNo(companyIdNo);
		        merApplyBook.setOrgDeptNo(orgDeptNo);
		        merApplyBook.setBusiLicenseId(busiLicenseId);
//		        merApplyBook.setWfStatus("45");
		        merApplyBook.setWfStatus(DateBaseConstants_MER.MER_WF_STAT);
		        merApplyBook.setApplyDate(now);
//		        merApplyBook.setApplyState(DateBaseConstants_MER.MER_APPLY_STAT_YES);
		        merApplyBook.setApplyState(DateBaseConstants_MER.MER_APPLY_STAT_WAIT);
		        merApplyBook.setUserId(userId);
		        merApplyBook.setExtensionParty(dto.getExtensionParty());
		        merApplyBook.setAliasName(dto.getAliasName());
		        merApplyBook.setQrcodeUsrWechat(DateBaseConstants_MER.QRCODE_USR_WAY_DIRECT);
		        merApplyBook.setQrcodeUsrAlipay(DateBaseConstants_MER.QRCODE_USR_WAY_DIRECT);
		        //merApplyBook.setQrcodeUsrWay(DateBaseConstants_MER.QRCODE_USR_WAY_DIRECT);
		        
		        String promoterIphone = dto.getPromoterIphone();
		        HashMap<String,Object> deptNo=getUnitCodeByMobile(promoterIphone);
		        String promoterDeptNo="";
		        String promoterDeptName="";
		        if(deptNo!=null&&deptNo.size()>0){
					promoterDeptNo=(String)deptNo.get("UNIT_CODE_");
					promoterDeptName=(String)deptNo.get("UNIT_NAME_");
				}else{
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"根据手机号：【"+promoterIphone+"】查找不到对应的分支行机构代码。");
				}
		        
		        
		        
		        
		        merApplyBook.setPromoterIphone(promoterIphone);//业务员电话号码
		        merApplyBook.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
		        merApplyBook.setPromoterDeptName(promoterDeptName);
		        merApplyBook.setPromoterName(dto.getPromoterName());//业务员姓名
		        
		        //第三方系统通过后台方式申请商户方式，需要记录商户的结算账户信息，审批完成时需要记录到商户结算账务表中
		        String uuid=UUID.randomUUID().toString().replaceAll("-", "");
		        merApplyBook.setMerNo(merNo);
		        merApplyBook.setKey3des(uuid);
		        merApplyBook.setStlAcctNo(dto.getStlAcctNo());
		        merApplyBook.setStlAcctType(dto.getStlAcctType());
		        merApplyBook.setStlAcctName(dto.getStlAcctName());
		        merApplyBook.setBankId(dto.getBankId());
		        merApplyBook.setBankName(dto.getBankName());
		        dto.setPrivateKey(uuid);
		        //商户平台加密参数信息
				
//				MerPlatSettingPo merPlatSeting=new MerPlatSettingPo();
//				merPlatSeting.setMerPlatNo(merNo);
//				String uuid=UUID.randomUUID().toString().replaceAll("-", "");
//				merPlatSeting.setKey3des(uuid);//设置商户默认的加密串
//				dto.setPrivateKey(uuid);
//				merPlatSeting.setFileEncryType("0");
//				merPlatSeting.setFileTransferMode("0001");
//				merPlatSeting.setModifyUser("jepAdmin");
//				merPlatSeting.setDateLastMaint(dto.getSysDate());
//				merPlatSeting.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
		        
				//商户表信息
//				merBaseInfo.setUserId(userId);
//				merBaseInfo.setMerNo(merNo);
//				merBaseInfo.setMerName(merName);
//				merBaseInfo.setMerWithoutPwdSign(dto.getMerWithoutPwdSign());
//				merBaseInfo.setPayOpenFlag(payopenflag);
//				merBaseInfo.setMerBusiType(merBusiType);
//				merBaseInfo.setContact(contact);
//				merBaseInfo.setContactTel(contactTel);
//				merBaseInfo.setContactMobile(contactMobile);
//				merBaseInfo.setContactEmail(contactEmail);
//				merBaseInfo.setMerTel(merTel);
//				merBaseInfo.setMerFax(dto.getMerFax());
//				merBaseInfo.setMerAddr(dto.getMerAddr());
//				merBaseInfo.setMerPostalCode(dto.getMerPostalCode());
//				merBaseInfo.setWebsiteCode(dto.getWebsiteCode());
//				merBaseInfo.setWebsiteName(dto.getWebsiteName());
//				merBaseInfo.setWebsiteDomain(dto.getWebsiteDomain());
//				merBaseInfo.setWebsiteScop(dto.getWebsiteScop());
//				merBaseInfo.setCompanyName(companyName);
//				merBaseInfo.setEgalPersonName(egalPersonName);
//				merBaseInfo.setEgalPersonIdType(egalPersonIdType);
//				merBaseInfo.setEgalPersonIdNo(egalPersonIdNo);
//				merBaseInfo.setCompanyIdType(companyIdType);
//				merBaseInfo.setCompanyIdNo(companyIdNo);
//				merBaseInfo.setOrgDeptNo(orgDeptNo);
//				merBaseInfo.setBusiLicenseId(busiLicenseId);
//				merBaseInfo.setOpenDate(dto.getSysTime());
//				merBaseInfo.setExtensionParty(dto.getExtensionParty());
//				merBaseInfo.setMerState(DateBaseConstants_MER.MER_STAT_NORMAL);
//				merBaseInfo.setAliasName(dto.getAliasName());
				
				
//				merBaseInfo.setPromoterIphone(dto.getPromoterIphone());//业务员电话号码
//				merBaseInfo.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
//				merBaseInfo.setPromoterName(dto.getPromoterName());//业务员姓名
//				merBaseInfo.setQrcodeUsrWechat(DateBaseConstants_MER.QRCODE_USR_WAY_DIRECT);//微信支付直连
//				merBaseInfo.setQrcodeUsrAlipay(DateBaseConstants_MER.QRCODE_USR_WAY_DIRECT);//支付宝支付直连
				
				//商户账务信息信息
//				MerAcctInfoPo merAcctInfo = new MerAcctInfoPo();
//				merAcctInfo.setMerPlatNo(merNo);
//				merAcctInfo.setMerNo(merNo);
//				merAcctInfo.setOpenUserId(userId);
//				merAcctInfo.setMerModifyDate(dto.getSysDate());
//				merAcctInfo.setPayOpenFlag(payopenflag);
//				merAcctInfo.setMerName(merName);
//				merAcctInfo.setStlAcctNo(dto.getStlAcctNo());
//				merAcctInfo.setStlAcctType(dto.getStlAcctType());
//				merAcctInfo.setStlAcctName(dto.getStlAcctName());
//				merAcctInfo.setBankId(dto.getBankId());
//				merAcctInfo.setBankName(dto.getBankName());
//				merAcctInfo.setStlAcctKind("1");//结算账户性质
//				merAcctInfo.setStlMode("0");//0-平台结算1-委托清算
//				merAcctInfo.setStlPeriod(new BigDecimal(1));//结算周期
//				merAcctInfo.setStlCycle("1");//清算周期
//				merAcctInfo.setIsParentMer(DateBaseConstants_MER.IS_PARENT_MER_YES);
//				merAcctInfo.setStlCycleDay(BigDecimal.ZERO);
//				merAcctInfo.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
				
				daoService.insert(merApplyBook);
//				daoService.insert(merBaseInfo);
//				daoService.insert(merAcctInfo);
//				daoService.insert(merPlatSeting);
				
				
				//添加商户交易权限默认模版  限额  和当日累积金额
//				TransTemplatePo  transTemplatePo=new TransTemplatePo();
//				transTemplatePo.setIsDefault(DateBaseConstants_MER.MER_TRANS_CTRL_TEMPLATE_DEFAULT);//默认模版标志
//				transTemplatePo = daoService.selectOne(transTemplatePo);
//				if(transTemplatePo==null){
//					ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"默认权限模版不存在!");
//				}
//				
//				parmPo=new GnrParmPo();
//				parmPo.setParmId("MER_DAY_TOTALLIMIT_DEFALUT");
//				parmPo = daoService.selectOne(parmPo);
//				if(parmPo==null){
//					ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"默认商户日限额未配置");
//				}
				
//				MerTransLimitPo transLimitPo=new MerTransLimitPo();
//				transLimitPo.setIsDefault("1");
//				List<MerTransLimitPo> transLimitList = daoService.selectList(transLimitPo);
//				if(null!=transLimitList&&transLimitList.size()>0){
//					transLimitPo=transLimitList.get(0);
//				}
//				
//				MerTransTemplatePo merTransTemplate=new MerTransTemplatePo();
//				merTransTemplate.setMerNo(merNo);
//				merTransTemplate.setDailyAcmlativeAmt(BigDecimal.ZERO);
//				merTransTemplate.setDailyAcmlativeLimit(new BigDecimal(parmPo.getParmValue()));//日累积限额
//				if(null!=transLimitPo){
//					merTransTemplate.setMerTransCtrlCode(transLimitPo.getMerTransCtrlCode());//单笔限额
//				}
//				
//				merTransTemplate.setStatus(DateBaseConstants_MER.MER_TRANS_TEMPLATE_OPEN);
//				merTransTemplate.setTemplateId(transTemplatePo.getTemplateId());
//				merTransTemplate.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
//				daoService.insert(merTransTemplate);
				
				//配置互网联统一支付平台商户PC端收银台支付渠道   
//				List<ChannelMenuBookPo> channelMenuBookList=daoService.selectList(new ChannelMenuBookPo());
//				if(channelMenuBookList!=null&&channelMenuBookList.size()>0){
//					for(ChannelMenuBookPo po:channelMenuBookList){
//						MerChannelMenuPo merChannelMenuPo=new MerChannelMenuPo();
//						merChannelMenuPo.setChannelId(po.getChannelId());
//						merChannelMenuPo.setChannelMenuBookId(po.getId());
//						merChannelMenuPo.setMerNo(merNo);
//						merChannelMenuPo.setMerName(merName);
//						merChannelMenuPo.setChannelName(po.getChannelName());
//						merChannelMenuPo.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
//						daoService.insert(merChannelMenuPo);
//					}
//				}
				
//				MerOpenPayPo merOpenPay=new MerOpenPayPo();
//				merOpenPay.setMerNo(merNo);
//				merOpenPay.setMerName(merName);
//				merOpenPay.setUnionOpen("2");//未开通
//				merOpenPay.setAlipayOpen("2");//未开通
//				merOpenPay.setWechatOpen("2");//未开通
//				merOpenPay.setWechatAttention("2");//未开通
//				merOpenPay.setWechatAuthDir("2");//未开通
//				merOpenPay.setWechatBindAppid("2");//未开通
//				merOpenPay.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
//				daoService.insert(merOpenPay);
			}else{//修改
				merNo=merBaseInfo.getMerNo();
			
//				MerBaseInfoPo  merInfo=new MerBaseInfoPo();
//				merInfo.setMerName(merName);
//				merInfo=daoService.selectOne(merInfo);
//				if(merInfo!=null){
//					String  uMerNo = merInfo.getMerNo();
//					if(!uMerNo.equals(merNo)){
//						//
//						ExInfo.throwDipperEx(AppCodeDict.BISACC0000,"商户名称【"+merName+"】己存在!");
//					}
//				}
				
				
				//商户信息
				merBaseInfo.setMerName(merName);
				merBaseInfo.setAliasName(dto.getAliasName());
				merBaseInfo.setMerWithoutPwdSign(dto.getMerWithoutPwdSign());
				merBaseInfo.setPayOpenFlag(payopenflag);
				merBaseInfo.setMerBusiType(merBusiType);
				merBaseInfo.setContact(contact);
				merBaseInfo.setContactTel(contactTel);
				merBaseInfo.setContactMobile(contactMobile);
				merBaseInfo.setContactEmail(contactEmail);
				merBaseInfo.setMerTel(merTel);
				merBaseInfo.setMerFax(dto.getMerFax());
				merBaseInfo.setMerAddr(dto.getMerAddr());
				merBaseInfo.setMerPostalCode(dto.getMerPostalCode());
				merBaseInfo.setWebsiteCode(dto.getWebsiteCode());
				merBaseInfo.setWebsiteName(dto.getWebsiteName());
				merBaseInfo.setWebsiteDomain(dto.getWebsiteDomain());
				merBaseInfo.setWebsiteScop(dto.getWebsiteScop());
				merBaseInfo.setCompanyName(companyName);
				merBaseInfo.setEgalPersonName(egalPersonName);
				merBaseInfo.setEgalPersonIdType(egalPersonIdType);
				merBaseInfo.setEgalPersonIdNo(egalPersonIdNo);
				merBaseInfo.setCompanyIdType(companyIdType);
				merBaseInfo.setCompanyIdNo(companyIdNo);
				merBaseInfo.setOrgDeptNo(orgDeptNo);
				merBaseInfo.setBusiLicenseId(busiLicenseId);
				
				String promoterIphone=dto.getPromoterIphone();
				String promoterDeptNo=null;
		        String promoterDeptName=null;
				if(StringUtils.isNotBlank(promoterIphone)){
					HashMap<String,Object> deptNo=getUnitCodeByMobile(promoterIphone);
			        if(deptNo!=null&&deptNo.size()>0){
						promoterDeptNo=(String)deptNo.get("UNIT_CODE_");
						promoterDeptName=(String)deptNo.get("UNIT_NAME_");
					}
			        merBaseInfo.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
					merBaseInfo.setPromoterDeptName(promoterDeptName);//业务员所属部门名称
				}
				
		        
				merBaseInfo.setPromoterIphone(dto.getPromoterIphone());//业务员电话号码
				merBaseInfo.setPromoterName(dto.getPromoterName());//业务员姓名
				
				MerBaseInfoPo whereMerBaseInfo =new MerBaseInfoPo();
				whereMerBaseInfo.setMerNo(merNo);
				
				
				//商户账务信息信息
				MerAcctInfoPo merAcctInfo = new MerAcctInfoPo();
				merAcctInfo.setOpenUserId(userId);
				merAcctInfo.setMerModifyDate(dto.getSysDate());
				merAcctInfo.setPayOpenFlag(payopenflag);
				merAcctInfo.setMerName(merName);
				merAcctInfo.setStlAcctNo(dto.getStlAcctNo());
				merAcctInfo.setStlAcctType(dto.getStlAcctType());
				merAcctInfo.setStlAcctName(dto.getStlAcctName());
				merAcctInfo.setBankId(dto.getBankId());
				merAcctInfo.setBankName(dto.getBankName());
				merAcctInfo.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
				
				MerAcctInfoPo wherrMerAcctInfo = new MerAcctInfoPo();
				wherrMerAcctInfo.setMerNo(merBaseInfo.getMerNo());
				
				daoService.update(merBaseInfo,whereMerBaseInfo);
				daoService.update(merAcctInfo,wherrMerAcctInfo);
				
				
				MerPlatSettingPo merPlatSeting=new MerPlatSettingPo();
				merPlatSeting.setMerPlatNo(merNo);
				//merPlatSeting.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
				merPlatSeting = daoService.selectOne(merPlatSeting);
				if(null!=merPlatSeting){
					dto.setPrivateKey(merPlatSeting.getKey3des());
				}
				
				//修改了商户所属部门，需要更新对应的设置部门编号
				if(StringUtils.isNotBlank(promoterDeptNo)&&!promoterDeptNo.equals(merBaseInfo.getPromoterDeptNo())){
					MerPlatSettingPo setMerPlatSeting=new MerPlatSettingPo();
					setMerPlatSeting.setMerPlatNo(merNo);
					setMerPlatSeting.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
					daoService.update(setMerPlatSeting,merPlatSeting);
					
					MerOpenPayPo whereMerOpenPay=new MerOpenPayPo();
					whereMerOpenPay.setMerNo(merNo);
					MerOpenPayPo setMerOpenPay=new MerOpenPayPo();
					setMerOpenPay.setMerNo(merNo);
					setMerOpenPay.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
					daoService.update(setMerOpenPay,whereMerOpenPay);
					
					MerChannelMenuPo whereMerChannelMenuPo=new MerChannelMenuPo();
					whereMerChannelMenuPo.setMerNo(merNo);
					MerChannelMenuPo setMerChannelMenuPo=new MerChannelMenuPo();
					setMerChannelMenuPo.setMerNo(merNo);
					setMerChannelMenuPo.setPromoterDeptNo(promoterDeptNo);//业务员所属部门
					daoService.update(setMerChannelMenuPo,whereMerChannelMenuPo);
					
					MerTransTemplatePo merTransTemplate=new MerTransTemplatePo();
					merTransTemplate.setMerNo(merNo);
					MerTransTemplatePo setMerTransTemplate=new MerTransTemplatePo();
					setMerTransTemplate.setMerNo(merNo);
					setMerTransTemplate.setPromoterDeptNo(promoterDeptNo);
					daoService.update(setMerTransTemplate,merTransTemplate);//业务员所属部门
				}
			}
			dto.setMerNo(merNo);
	}
	
	private void checkParm(CreateOrUpdateFirstMerDto dto){
		// 检查参数
        String merName = dto.getMerName();
        String merWithoutPwdSign = dto.getMerWithoutPwdSign();
        String payopenflag = dto.getPayopenflag();
        String merBusiType = dto.getMerBusiType();
        String contact = dto.getContact();
        String contactTel = dto.getContactTel();
        String contactMobile = dto.getContactMobile();
        String contactEmail = dto.getContactEmail();
        String merTel = dto.getMerTel();
        String websiteScop = dto.getWebsiteScop();
        String companyName = dto.getCompanyName();
        String egalPersonName = dto.getEgalPersonName();
        String egalPersonIdType = dto.getEgalPersonIdType();
        String egalPersonIdNo = dto.getEgalPersonIdNo();
        String companyIdType = dto.getCompanyIdType();
        String companyIdNo = dto.getCompanyIdNo();
        String orgDeptNo = dto.getOrgDeptNo();
        String busiLicenseId = dto.getBusiLicenseId();
        
        String stlAcctNo = dto.getStlAcctNo();
        String stlAcctName = dto.getStlAcctName();
        String stlAcctType = dto.getStlAcctType();
        String bankId = dto.getBankId();
        String bankName = dto.getBankName();
        String merNo=dto.getMerNo();
        if("U".equals(dto.getOperateFlag())){
        	if (StringUtils.isBlank(merNo)) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
            }
        }
        
        if (StringUtils.isBlank(stlAcctNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户结算账户");
        }
        if (StringUtils.isBlank(stlAcctName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户结算账户姓名");
        }
        if (StringUtils.isBlank(stlAcctType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户结算账户类型");
        }
        if (StringUtils.isBlank(bankId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户结算账户银行编号");
        }
        if (StringUtils.isBlank(bankName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户结算账户银行名称");
        }
        
        
        if (StringUtils.isBlank(merName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户名称");
        }
        if (StringUtils.isBlank(merWithoutPwdSign)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户授权免密标志");
        }
        if (StringUtils.isBlank(payopenflag)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "支付功能开通标志");
        }
        if (StringUtils.isBlank(merBusiType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户业务类型");
        }
        if (StringUtils.isBlank(contact)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "联系人姓名");
        }
        if (StringUtils.isBlank(contactTel)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "联系人电话");
        }
        if (StringUtils.isBlank(contactMobile)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "联系人手机");
        }
        if (StringUtils.isBlank(contactEmail)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "联系人邮件");
        }
        if (StringUtils.isBlank(merTel)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户电话");
        }
        if (StringUtils.isBlank(websiteScop)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "经营范围");
        }
        if (StringUtils.isBlank(companyName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "公司名称");
        }
        if (StringUtils.isBlank(egalPersonName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "法人代表姓名");
        }
        if (StringUtils.isBlank(egalPersonIdType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "法人代表证件类型");
        }
        if (StringUtils.isBlank(egalPersonIdNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "法人代表身份证");
        }
        if (StringUtils.isBlank(companyIdType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "企业证件类型");
        }
        if (StringUtils.isBlank(companyIdNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "企业证件号");
        }
        if (StringUtils.isBlank(orgDeptNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "组织机构代码证号");
        }
//        if (StringUtils.isBlank(busiLicenseId)) {
//            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "营业执照");
//        }
	}
	
	private HashMap<String,Object> getUnitCodeByMobile(String promoterIphone){
		HashMap<String,Object> deptNo=null;
		if(StringUtils.isNotBlank(promoterIphone)){
        	Map<String, Object> parmMap=new HashMap<String, Object>();
	        parmMap.put("mobile", promoterIphone);
	        deptNo = daoService.selectOne(MerBaseInfoPo.class.getName().concat(".getUnitCodeByMobile"),parmMap);
			
        }
		return deptNo;
	}
	  public String getUserName(){
	    	boolean bool=true;
	    	String userName="";
	    	while(bool){
	    		userName=generateUsername();
	    		UsrRegInfoPo reg=new UsrRegInfoPo();
	    		reg.setUserName(userName);
	    		reg=daoService.selectOne(reg);
	    		if(reg==null){
	    			bool=false;
	    			break;
	    		}else{
	    			log.error("用户名  "+userName+  "己存在,重新生成");
	    		}
	    	}
	    	log.error("用户名："+userName);
	    	return userName;
	    }

	    public static  String generateUsername() {
//	        String han = "\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31\u53bb\u5b50\u5f97\u4e5f\u548c\u90a3\u8981\u4e0b\u770b\u5929\u65f6\u8fc7\u51fa\u5c0f\u4e48\u8d77\u4f60\u90fd\u628a\u597d\u8fd8\u591a\u6ca1\u4e3a\u53c8\u53ef\u5bb6\u5b66\u53ea\u4ee5\u4e3b\u4f1a\u6837\u5e74\u60f3\u751f\u540c\u8001\u4e2d\u5341\u4ece\u81ea\u9762\u524d\u5934\u9053\u5b83\u540e\u7136\u8d70\u5f88\u50cf\u89c1\u4e24\u7528\u5979\u56fd\u52a8\u8fdb\u6210\u56de\u4ec0\u8fb9\u4f5c\u5bf9\u5f00\u800c\u5df1\u4e9b\u73b0\u5c71\u6c11\u5019\u7ecf\u53d1\u5de5\u5411\u4e8b\u547d\u7ed9\u957f\u6c34\u51e0\u4e49\u4e09\u58f0\u4e8e\u9ad8\u624b\u77e5\u7406\u773c\u5fd7\u70b9\u5fc3\u6218\u4e8c\u95ee\u4f46\u8eab\u65b9\u5b9e\u5403\u505a\u53eb\u5f53\u4f4f\u542c\u9769\u6253\u5462\u771f\u5168\u624d\u56db\u5df2\u6240\u654c\u4e4b\u6700\u5149\u4ea7\u60c5\u8def\u5206\u603b\u6761\u767d\u8bdd\u4e1c\u5e2d\u6b21\u4eb2\u5982\u88ab\u82b1\u53e3\u653e\u513f\u5e38\u6c14\u4e94\u7b2c\u4f7f\u5199\u519b\u5427\u6587\u8fd0\u518d\u679c\u600e\u5b9a\u8bb8\u5feb\u660e\u884c\u56e0\u522b\u98de\u5916\u6811\u7269\u6d3b\u90e8\u95e8\u65e0\u5f80\u8239\u671b\u65b0\u5e26\u961f\u5148\u529b\u5b8c\u5374\u7ad9\u4ee3\u5458\u673a\u66f4\u4e5d\u60a8\u6bcf\u98ce\u7ea7\u8ddf\u7b11\u554a\u5b69\u4e07\u5c11\u76f4\u610f\u591c\u6bd4\u9636\u8fde\u8f66\u91cd\u4fbf\u6597\u9a6c\u54ea\u5316\u592a\u6307\u53d8\u793e\u4f3c\u58eb\u8005\u5e72\u77f3\u6ee1\u65e5\u51b3\u767e\u539f\u62ff\u7fa4\u7a76\u5404\u516d\u672c\u601d\u89e3\u7acb\u6cb3\u6751\u516b\u96be\u65e9\u8bba\u5417\u6839\u5171\u8ba9\u76f8\u7814\u4eca\u5176\u4e66\u5750\u63a5\u5e94\u5173\u4fe1\u89c9\u6b65\u53cd\u5904\u8bb0\u5c06\u5343\u627e\u4e89\u9886\u6216\u5e08\u7ed3\u5757\u8dd1\u8c01\u8349\u8d8a\u5b57\u52a0\u811a\u7d27\u7231\u7b49\u4e60\u9635\u6015\u6708\u9752\u534a\u706b\u6cd5\u9898\u5efa\u8d76\u4f4d\u5531\u6d77\u4e03\u5973\u4efb\u4ef6\u611f\u51c6\u5f20\u56e2\u5c4b\u79bb\u8272\u8138\u7247\u79d1\u5012\u775b\u5229\u4e16\u521a\u4e14\u7531\u9001\u5207\u661f\u5bfc\u665a\u8868\u591f\u6574\u8ba4\u54cd\u96ea\u6d41\u672a\u573a\u8be5\u5e76\u5e95\u6df1\u523b\u5e73\u4f1f\u5fd9\u63d0\u786e\u8fd1\u4eae\u8f7b\u8bb2\u519c\u53e4\u9ed1\u544a\u754c\u62c9\u540d\u5440\u571f\u6e05\u9633\u7167\u529e\u53f2\u6539\u5386\u8f6c\u753b\u9020\u5634\u6b64\u6cbb\u5317\u5fc5\u670d\u96e8\u7a7f\u5185\u8bc6\u9a8c\u4f20\u4e1a\u83dc\u722c\u7761\u5174\u5f62\u91cf\u54b1\u89c2\u82e6\u4f53\u4f17\u901a\u51b2\u5408\u7834\u53cb\u5ea6\u672f\u996d\u516c\u65c1\u623f\u6781\u5357\u67aa\u8bfb\u6c99\u5c81\u7ebf\u91ce\u575a\u7a7a\u6536\u7b97\u81f3\u653f\u57ce\u52b3\u843d\u94b1\u7279\u56f4\u5f1f\u80dc\u6559\u70ed\u5c55\u5305\u6b4c\u7c7b\u6e10\u5f3a\u6570\u4e61\u547c\u6027\u97f3\u7b54\u54e5\u9645\u65e7\u795e\u5ea7\u7ae0\u5e2e\u5566\u53d7\u7cfb\u4ee4\u8df3\u975e\u4f55\u725b\u53d6\u5165\u5cb8\u6562\u6389\u5ffd\u79cd\u88c5\u9876\u6025\u6797\u505c\u606f\u53e5\u533a\u8863\u822c\u62a5\u53f6\u538b\u6162\u53d4\u80cc\u7ec6";
	        String digital="12346567890";
	        String letter="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	        Random ran = new Random();
	        StringBuilder builder = new StringBuilder();
	        for (int i = 0; i < 4; i++) {
//	            String ch = han.charAt(ran.nextInt(han.length())) + "";
//	            builder.append(ch);
	        	String   ch = digital.charAt(ran.nextInt(digital.length())) + "";
	            builder.append(ch);
	            ch = letter.charAt(ran.nextInt(letter.length())) + "";
	            builder.append(ch);
	        }
	        return builder.toString();
	    }
}
