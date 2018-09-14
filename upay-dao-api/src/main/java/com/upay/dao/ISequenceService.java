package com.upay.dao;

import com.pactera.dipper.dao.service.IDipperSequenceService;

import java.util.Date;


public interface ISequenceService extends IDipperSequenceService {

    /**
     * 获取用户号
     * 
     * @return
     */
    public String generateUserId();


    /**
     * 生成用户操作流水号,也是系统流水号
     * 
     * @return
     */
    public String generateOperSeq();


    /**
     * 生成订单号
     * 
     * @return
     */
    public String generateOrderNo(String modeType);
    /**
     * 生成通知ID
     * 
     * @return
     */
    public String generateNotifyId(String modeType);


    /**
     * 商品副本号
     * 
     * @return
     */
    public String generateBakNo();


    /**
     * 获取短信序列号
     * 
     * @return
     */
    public String generateSmsNoSeq();


    /**
     * 获取消息序列号
     * 
     * @return
     */
    public String generateNoticeNoSeq();


    /**
     * 获取商品序列号
     * 
     * @return
     */
    public String generateProductNoSeq();


    /**
     * 获取广告位编号
     * 
     * @return
     */
    public String generateAdvertiseNo();


    /**
     * 获取商户序列号
     * 
     * @return
     */
    public String generateMerNoSeq();


    /**
     * 获取商户序列号
     * 
     * @return
     */
    public String generateStoreNoSeq();


    /**
     * 获取交易流水号
     */
    public String generatePayFlowSeq();


    /**
     * 获取限额流水号
     */
    public String generateLmtAmountSeq(String accType);


    /**
     * 获取电子账户核心账户
     * 
     * @return
     */
    public String generateeAcctNoSeq();


    /**
     * 客户帐校验位的生成
     * 
     * @return
     */
    public String vilidatorAcctNo(String key);


    /**
     * 获取商户申请编号
     */
    public String generateMerApplyNo();


    // zjpay 流水号生成
    public String generateZjpaySysSeq(Date today);


    /**
     * 按序列名生成序列
     * 
     * @param key
     * @return
     */
    public String generateSeq(String key);


    /**
     * 生成手机号变更的认证申请编号的方法
     */
    public String authApplyNo();


    /**
     * 生成费用方法编号的方法
     */
    public String generateFeeID();


    /**
     * 生成FEE_CODE的方法
     */
    public String generateFeeCode();
    
    /**
     * 生成STP_NO的方法
     */
    public String generateStpNo();
    
    /**
     * 生成ESB_NO的方法
     */
    public String generateEsbNo();

}
