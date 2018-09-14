package com.upay.busi.pay.service.impl;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.EsbSwitchDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.dao.ISequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 流水转换
 */
public class EsbSwitchService extends AbstractDipperHandler<EsbSwitchDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(EsbSwitchService.class);


    @Resource(name = "esbCliDipperHandler")
    private IDipperHandler<Message> esbCliDipperHandler;

    @Resource
    private ISequenceService sequenceService;


    private String channel = "esbcli";
    private final String charsetName = "UTF-8";


    public void getAccountType(EsbSwitchDto dto) throws Exception {
        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                        MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                                new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                                "交易成功"));
        Map<String, Object> body = (Map<String, Object>) m.getTarget().getBodys();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("transCode", "990001");
        body.put("svcCd", "30130001");
        body.put("svcScn", "03");
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizSerialNo", sequenceService.generateEsbNo());
        body.put("AcctChkFlg", "11111111111111");//CMS:卡 DPS:账号 GLS:内部账 LNS:贷款账号
        body.put("fileFlg", "0");
        body.put("channelId", "74");
        body.put("currency", "01"); // 币种


        Map<String, Object> result = new HashMap<>();
        Map<String, String> temp = new HashMap<>();
        //CMS:卡DPS:账号GLS:内部账LNS:贷款账号
        temp.put("CMS", "4");
        temp.put("DPS", "3");
        temp.put("GLS", "2");
        //判断账户类型是 本行卡  二类户  三类户  时  发送   查询账号类型请求  因为传递  可能时卡号   也可能 时账号
        String accountType = dto.getAccountType();
        if (DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(accountType) ||
                DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(accountType) ||
                DataBaseConstants_PAY.TWO_TYPES_OF_ACCOUNT.equals(accountType) ||
                DataBaseConstants_PAY.THREE_TYPES_OF_ACCOUNT.equals(accountType) ||
                DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(accountType) ||
                DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(accountType)) {
            body.put("setAccount", dto.getAcctNo());
            m = esbCliDipperHandler.handle(m);
            result = (Map<String, Object>) m.getTarget().getBodys();


            if (result.containsKey("SubStmCd")) {
                //代收 时   付款方  时本行的情况
                if (CommonConstants_GNR.TRANS_CODE_SINGLE_COLLECTION.equals(dto.getTransCode())) {
                    //付款方  代收
                    dto.setAcctNoDataSrcFlg(temp.get(result.get("SubStmCd") + ""));
                }

                //代付 时   收款方  时本行的情况
                if (CommonConstants_GNR.TRANS_CODE_SINGLE_PAYMENT.equals(dto.getTransCode())) {
                    //收款方  代付
                    dto.setAcctNoSrc(temp.get(result.get("SubStmCd") + ""));
                }
            }
        }else{

            //代收 时   付款方  不是本行的情况
            if (CommonConstants_GNR.TRANS_CODE_SINGLE_COLLECTION.equals(dto.getTransCode())) {
                //付款方  代收
                dto.setAcctNoDataSrcFlg("2");
            }

            //代付 时   收款方  不是本行的情况
            if (CommonConstants_GNR.TRANS_CODE_SINGLE_PAYMENT.equals(dto.getTransCode())) {
                dto.setAcctNoSrc("2");
            }
        }


        if (CommonConstants_GNR.TRANS_CODE_SINGLE_COLLECTION.equals(dto.getTransCode())) {


            //收款方
            String payeeAccountType = dto.getPayeeAccountType();
            if (DataBaseConstants_PAY.TWO_TYPES_OF_ACCOUNT.equals(accountType) || DataBaseConstants_PAY.TWO_TYPES_OF_ACCOUNT.equals(payeeAccountType)) {
                //设置三类户  必要参数
                dto.setAgntFlg("1");
                //付款方 账号  和姓名
                dto.setOppCstNm(dto.getAcctName());
                dto.setCntrprtAcctNo(dto.getAcctNo());
            }

            if (DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payeeAccountType) ||
                    DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(payeeAccountType) ||
                    DataBaseConstants_PAY.TWO_TYPES_OF_ACCOUNT.equals(payeeAccountType) ||
                    DataBaseConstants_PAY.THREE_TYPES_OF_ACCOUNT.equals(payeeAccountType) ||
                    DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payeeAccountType) ||
                    DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payeeAccountType)) {
                body.put("setAccount", dto.getPayeeAccountNo());
                m = esbCliDipperHandler.handle(m);
                result = (Map<String, Object>) m.getTarget().getBodys();
                //收款方
                dto.setAcctNoSrc(temp.get(result.get("SubStmCd") + ""));
            }
        }

        if (CommonConstants_GNR.TRANS_CODE_SINGLE_PAYMENT.equals(dto.getTransCode())) {
            //付款方
            String payerAccountType = dto.getPayerAccountType();
            //代付
            if (CommonConstants_GNR.TRANS_CODE_SINGLE_PAYMENT.equals(dto.getTransCode())) {
                //二类户
                if (DataBaseConstants_PAY.TWO_TYPES_OF_ACCOUNT.equals(accountType) || DataBaseConstants_PAY.TWO_TYPES_OF_ACCOUNT.equals(payerAccountType)) {
                    //设置三类户  必要参数
                    dto.setAgntFlg("1");
                    //付款方 账号  和姓名
                    dto.setOppCstNm(dto.getPayerAccountName());
                    dto.setCntrprtAcctNo(dto.getPayerAccountName());
                }
            }
            if (DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payerAccountType) ||
                    DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(payerAccountType) ||
                    DataBaseConstants_PAY.TWO_TYPES_OF_ACCOUNT.equals(payerAccountType) ||
                    DataBaseConstants_PAY.THREE_TYPES_OF_ACCOUNT.equals(payerAccountType) ||
                    DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payerAccountType) ||
                    DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payerAccountType)) {
                body.put("setAccount", dto.getPayerAccountNo());
                m = esbCliDipperHandler.handle(m);
                result = (Map<String, Object>) m.getTarget().getBodys();
                dto.setAcctNoDataSrcFlg(temp.get(result.get("SubStmCd") + ""));
            }
        }

    }


    @Override
    public EsbSwitchDto execute(EsbSwitchDto dto, Message message)
            throws Exception {


        //流水转换  日期转换
       // dto.setTempTransSubSeq(dto.getTransSubSeq());
        Map<String, Object> bodysMap = (Map<String, Object>) message.getTarget().getBodys();
     //   dto.setTransSubSeq(bodysMap.get("destTransSubSeq") + "");
        String machineDate = bodysMap.get("machineDate") + "";
        bodysMap.put("bkDate", machineDate);
        //账户类型  组装
        getAccountType(dto);
        bodysMap.put("AcctNoSrc", dto.getAcctNoSrc());
        return dto;
    }
}
