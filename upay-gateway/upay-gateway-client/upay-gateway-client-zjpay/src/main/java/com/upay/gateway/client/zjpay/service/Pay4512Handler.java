package com.upay.gateway.client.zjpay.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.core.utils.Constants.Channel;
import com.pactera.dipper.exception.DipperException;
import com.pactera.dipper.flow.utils.Constants.FlowStatus;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.commons.dict.AppCodeDict;
import com.upay.gateway.client.zjpay.pay.DefaultZJPayClientHandler;


public class Pay4512Handler extends DefaultZJPayClientHandler {

    private static final Logger log = LoggerFactory.getLogger(Pay4512Handler.class);


    @Override
    protected void setInitParam(Map<String, Object> arg0) {
        log.info("中金机构支付账户交易明细查询start");
        // head
        arg0.put(Channel._TRAN_CODE_NAME, "4512");
        arg0.put("institutionID", "001853");
        // body
        arg0.put("paymentAccountName", arg0.get("paymentAccountName"));
        arg0.put("paymentAccountNumber", arg0.get("paymentAccountNumber"));
        arg0.put("startDate", arg0.get("startDate"));
        arg0.put("endDate", arg0.get("endDate"));
        log.info("中金机构支付账户交易明细查询end");
    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
        log.info("中金doErrorHandle start!");
        target.put("coreStat", CoreStat.TIME_OUT);
        target.put(FlowStatus.CONFIRMATION_STATUS, Boolean.TRUE);
        target.put("errCode", fault.getOutCode());
        target.put("errMsg", fault.getOutMsg());
        String backCode = fault.getCode();
        switch (backCode) {
        case "2000":
            fault.setMsgAll("成功受理请求");
            break;
        case "2001":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("系统内部错误。");
            break;
        case "2002":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("验证签名失败。");
            break;
        case "2003":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("解析报文错误。");
            break;
        case "2004":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("报文格式错误。");
            break;
        case "2005":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("不支持的版本。");
            break;
        case "2006":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("无效交易类型。");
            break;
        case "2007":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("无此操作的权限。");
            break;
        case "2008":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("重复交易请求。");
            break;
        case "2009":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("无效日期。");
            break;
        case "2010":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("无效日期范围。");
            break;
        case "2011":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("数据库存取异常。");
            break;
        case "2012":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("会话不存在。");
            break;
        case "2013":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("系统不支持GET方法。");
            break;
        case "2016":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("参数不正确。");
            break;
        case "2017":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("交易证书过期。");
            break;
        case "240001":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("请求中不存在message参数或者signature参数");
            break;
        case "240002":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("不存在此交易号码，请查看参数TxCode。");
            break;
        case "240003":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("不存在此机构，请查看参数InstitutionID。");
            break;
        case "240004":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("不存在此订单，请查看参数OrderNo。");
            break;
        case "240005":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("不存在此支付交易，请查看参数PaymentNo。");
            break;
        case "240006":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("不存在此结算交易，请查看参数SerialNumber。");
            break;
        case "240007":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("订单号长度不正确，请查看参数OrderNo。");
            break;
        case "240008":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("支付交易流水号长度不正确，请查看参数PaymentNo。");
            break;
        case "240009":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("交易流水号长度不正确，请查看参数SerialNumber。");
            break;
        case "240010":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("订单号重复，请查看参数OrderNo。");
            break;
        case "240011":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("支付交易流水号重复，请查看参数PaymentNo。");
            break;
        case "240012":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("交易流水号重复，请查看参数SerialNumber。");
            break;
        case "240013":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("账户类型错误，请查看参数AccountType。");
            break;
        case "240014":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("金额格式不对。");
            break;
        case "240015":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("结算金额大于可结算金额。");
            break;
        case "240016":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("备注信息太多。");
            break;
        case "240017":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("该笔订单没有支付，不能退款。");
            break;
        case "240018":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("退款（累计）金额大于订单金额。");
            break;
        case "240019":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("账户名称与账户号码不匹配。");
            break;
        case "240020":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("不存在此退款交易，请查看参数SerialNumber。");
            break;
        case "240021":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("金额必须大于0。");
            break;
        case "240022":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("银行账户信息不完整。");
            break;
        case "240023":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("该笔订单已经退款，只能退款一次。");
            break;
        case "240024":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("（支付平台）账户不存在。");
            break;
        case "240025":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("无结算对账记录。");
            break;
        case "240026":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("金额长度太大。");
            break;
        case "240027":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("日期格式错误。");
            break;
        case "240028":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("保证金退息标识不得为空，并且只能为0或1。");
            break;
        case "240029":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("该笔订单的退款正在处理，请等待后续通知。");
            break;
        case "240030":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("开始时间大于结束时间。");
            break;
        case "240031":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("监管银行为空，请联系支付平台工作人员配置监管银行。");
            break;
        case "240032":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("每日15点后，不可以做保证金退款。");
            break;
        case "240033":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("交易流水号为空，请查看参数SerialNumber。");
            break;

        case "250001":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("未找到对应批次号的代付。");
            break;
        case "250002":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("未找到对应批次号的代付明细。");
            break;
        case "250003":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("批次号重复。");
            break;
        case "250008":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("批备注过长。");
            break;

        case "260009":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("分支行城市长度不明确，请查看参数City。");
            break;
        case "260010":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("开户证件类型长度不正确，请查看参数IdentificationType。");
            break;
        case "260011":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("证件号码长度不正确，请查看参数IdentificationNumber。");
            break;
        case "260012":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("备注长度不正确，请查看参数Note。");
            break;
        case "260013":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("协议用户编号长度不正确，请查看参数ContractUserID。");
            break;
        case "2600014":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("手机号长度不正确，请查看参数PhoneNumber。");
            break;
        case "2600015":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("电子邮箱长度不正确，请查看参数Email。");
            break;
        case "2600016":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("结算标识为空或长度不正确，请查看参数SettlementFlag。");
            break;

        case "270001":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("支付交易流水号长度不正确，请查看参数PaymentNo");
            break;
        case "270002":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("支付交易流水号包含特殊字符，请查看参数PaymentNo。");
            break;
        case "270003":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("金额长度错误。最大值：999999999999999999。");
            break;
        case "270004":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("金额格式不对。");
            break;
        case "270005":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("金额必须大于0。");
            break;
        case "270006":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("手续费金额长度错误。最大值：999999999。");
            break;
        case "270007":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("手续费格式不对。");
            break;
        case "270008":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("手续费必须大于等于0。");
            break;
        case "270009":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("付款人注册ID太长，请查看参数PayerID。");
            break;
        case "270010":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("付款人注册ID包含特殊字符，请查看参数PayerID。");
            break;
        case "270011":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("付款方名称太长，请查看参数PayerName。");
            break;
        case "270012":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("付款方名称包含特殊字符，请查看参数PayerName。");
            break;
        case "270013":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("代付客户ID长度不正确，请查看参数CustomerID。");
            break;
        case "270014":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("代付客户ID格式不对，请查看参数CustomerID。");
            break;
        case "270015":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("代付客户交易授权码太长，请查看参数CustomerTxCode。");
            break;
        case "270016":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("代付客户交易授权码包含特殊字符，请查看参数CustomerTxCode。");
            break;
        case "270017":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("资金用途信息太多。");
            break;
        case "270018":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("资金用途信息包含特殊字符，请查看参数Usage。");
            break;
        case "270019":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("备注信息太多。");
            break;
        case "270020":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("备注信息包含特殊字符，请查看参数Remark。");
            break;
        case "270021":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("结算标识信息长度不正确，请查看参数SettlementFlag。");
            break;
        case "270022":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("结算标识信息包含特殊字符，请查看参数SettlementFlag。");
            break;
        case "270023":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("付款人金科信安平台账号不正确或授权码输入不正确。");
            break;
        default:
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("通用错误");
        }
        log.info("中金doErrorHandle end!");
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        log.info("中金doFailureHandle start!");
        String backCode = fault.getCode();
        switch (backCode) {
        case "2000":
            fault.setMsgAll("成功");
            break;
        case "2001":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("系统内部错误。");
            break;
        case "2002":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("验证签名失败。");
            break;
        case "2003":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("解析报文错误。");
            break;
        case "2004":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("报文格式错误。");
            break;
        case "2005":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("不支持的版本。");
            break;
        case "2006":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("无效交易类型。");
            break;
        case "2007":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("无此操作的权限。");
            break;
        case "2008":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("重复交易请求。");
            break;
        case "2009":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("无效日期。");
            break;
        case "2010":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("无效日期范围。");
            break;
        case "2011":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("数据库存取异常。");
            break;
        case "2012":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("会话不存在。");
            break;
        case "2013":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("系统不支持GET方法。");
            break;
        case "2016":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("参数不正确。");
            break;
        case "2017":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("交易证书过期。");
            break;
        case "240001":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("请求中不存在message参数或者signature参数");
            break;
        case "240002":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("不存在此交易号码，请查看参数TxCode。");
            break;
        case "240003":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("不存在此机构，请查看参数InstitutionID。");
            break;
        case "240004":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("不存在此订单，请查看参数OrderNo。");
            break;
        case "240005":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("不存在此支付交易，请查看参数PaymentNo。");
            break;
        case "240006":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("不存在此结算交易，请查看参数SerialNumber。");
            break;
        case "240007":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("订单号长度不正确，请查看参数OrderNo。");
            break;
        case "240008":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("支付交易流水号长度不正确，请查看参数PaymentNo。");
            break;
        case "240009":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("交易流水号长度不正确，请查看参数SerialNumber。");
            break;
        case "240010":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("订单号重复，请查看参数OrderNo。");
            break;
        case "240011":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("支付交易流水号重复，请查看参数PaymentNo。");
            break;
        case "240012":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("交易流水号重复，请查看参数SerialNumber。");
            break;
        case "240013":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("账户类型错误，请查看参数AccountType。");
            break;
        case "240014":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("金额格式不对。");
            break;
        case "240015":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("结算金额大于可结算金额。");
            break;
        case "240016":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("备注信息太多。");
            break;
        case "240017":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("该笔订单没有支付，不能退款。");
            break;
        case "240018":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("退款（累计）金额大于订单金额。");
            break;
        case "240019":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("账户名称与账户号码不匹配。");
            break;
        case "240020":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("不存在此退款交易，请查看参数SerialNumber。");
            break;
        case "240021":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("金额必须大于0。");
            break;
        case "240022":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("银行账户信息不完整。");
            break;
        case "240023":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("该笔订单已经退款，只能退款一次。");
            break;
        case "240024":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("（支付平台）账户不存在。");
            break;
        case "240025":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("无结算对账记录。");
            break;
        case "240026":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("金额长度太大。");
            break;
        case "240027":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("日期格式错误。");
            break;
        case "240028":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("保证金退息标识不得为空，并且只能为0或1。");
            break;
        case "240029":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("该笔订单的退款正在处理，请等待后续通知。");
            break;
        case "240030":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("开始时间大于结束时间。");
            break;
        case "240031":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("监管银行为空，请联系支付平台工作人员配置监管银行。");
            break;
        case "240032":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("每日15点后，不可以做保证金退款。");
            break;
        case "240033":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("交易流水号为空，请查看参数SerialNumber。");
            break;

        case "250001":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("未找到对应批次号的代付。");
            break;
        case "250002":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("未找到对应批次号的代付明细。");
            break;
        case "250003":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("批次号重复。");
            break;
        case "250008":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("批备注过长。");
            break;

        case "260009":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("分支行城市长度不明确，请查看参数City。");
            break;
        case "260010":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("开户证件类型长度不正确，请查看参数IdentificationType。");
            break;
        case "260011":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("证件号码长度不正确，请查看参数IdentificationNumber。");
            break;
        case "260012":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("备注长度不正确，请查看参数Note。");
            break;
        case "260013":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("协议用户编号长度不正确，请查看参数ContractUserID。");
            break;
        case "2600014":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("手机号长度不正确，请查看参数PhoneNumber。");
            break;
        case "2600015":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("电子邮箱长度不正确，请查看参数Email。");
            break;
        case "2600016":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("结算标识为空或长度不正确，请查看参数SettlementFlag。");
            break;

        case "270001":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("支付交易流水号长度不正确，请查看参数PaymentNo");
            break;
        case "270002":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("支付交易流水号包含特殊字符，请查看参数PaymentNo。");
            break;
        case "270003":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("金额长度错误。最大值：999999999999999999。");
            break;
        case "270004":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("金额格式不对。");
            break;
        case "270005":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("金额必须大于0。");
            break;
        case "270006":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("手续费金额长度错误。最大值：999999999。");
            break;
        case "270007":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("手续费格式不对。");
            break;
        case "270008":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("手续费必须大于等于0。");
            break;
        case "270009":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("付款人注册ID太长，请查看参数PayerID。");
            break;
        case "270010":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("付款人注册ID包含特殊字符，请查看参数PayerID。");
            break;
        case "270011":
            fault.setCodeAll(AppCodeDict.BISPAY0005);
            fault.setMsgAll("付款方名称太长，请查看参数PayerName。");
            break;
        case "270012":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("付款方名称包含特殊字符，请查看参数PayerName。");
            break;
        case "270013":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("代付客户ID长度不正确，请查看参数CustomerID。");
            break;
        case "270014":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("代付客户ID格式不对，请查看参数CustomerID。");
            break;
        case "270015":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("代付客户交易授权码太长，请查看参数CustomerTxCode。");
            break;
        case "270016":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("代付客户交易授权码包含特殊字符，请查看参数CustomerTxCode。");
            break;
        case "270017":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("资金用途信息太多。");
            break;
        case "270018":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("资金用途信息包含特殊字符，请查看参数Usage。");
            break;
        case "270019":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("备注信息太多。");
            break;
        case "270020":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("备注信息包含特殊字符，请查看参数Remark。");
            break;
        case "270021":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("结算标识信息长度不正确，请查看参数SettlementFlag。");
            break;
        case "270022":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("结算标识信息包含特殊字符，请查看参数SettlementFlag。");
            break;
        case "270023":
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("付款人金科信安平台账号不正确或授权码输入不正确。");
            break;
        default:
            fault.setCodeAll(AppCodeDict.BISPAY0004);
            fault.setMsgAll("通用错误");
        }

        target.put("errCode", fault.getCode());
        target.put("errMsg", fault.getMsg());

        throw new DipperException(fault, "failure");
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault arg2) {
        log.info("中金doSuccessHandle start!");

        log.info("中金doSuccessHandle end!");
    }

}
