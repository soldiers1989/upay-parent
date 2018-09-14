package com.upay.gateway.client.core;

import java.util.Map;

import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.presys.cp.client.AbstractClientDipperHandler;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;


public class CoreClientHandler extends AbstractClientDipperHandler {

    @Override
    protected void setInitParam(Map<String, Object> init) {
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        // System.out.println(source.toString());
        target.putAll(source);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        // System.out.println(source.toString());
        // source.get("source")
        // respCode
        target.putAll(source);

        doStr(source, target, fault);
    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
        target.put(CommonConstants_GNR.ROUTE_TIME_OUT_KEY, CommonConstants_GNR.ROUTE_TIME_OUT_VALUE);
    }


    @Override
    public boolean isErrorThrow() {
        return true;
    }


    @Override
    public boolean isFailureThrow() {
        return true;
    }


    public void doStr(Map<String, Object> source, Map<String, Object> target, Fault fault) {

        String respCode = (String) target.get("respCode");
        String respMsg=(String)target.get("respMsg");
        switch (respCode) {
        case "DPS353":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("该产品不允许转账存入");
            break;
        case "CMS168":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("已经正常换卡");
            break;
        case "B001":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("卡片信息不存在");
            break;
        case "B002":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效的卡片");
            break;
        case "B003":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效的币种");
            break;
        case "B010":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户已经关闭，无法对卡片做销卡操作");
            break;
        case "B011":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("主卡为销卡状态，不可为附卡进行销卡撤销操作");
            break;
        case "B013":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("卡片非冻结状态，无需解冻");
            break;
        case "B015":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("客户信息不存在");
            break;
        case "B016":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证失败");
            break;
        case "B020":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("卡号或证件无效");
            break;
        case "B021":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效证件");
            break;
        case "B027":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效的电话号码，只能输入0XX-XXXXXXXX");
            break;
        case "B028":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效的移动电话");
            break;
        case "B029":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("生日与身份证不匹配");
            break;
        case "B048":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效的账单周期");
            break;
        case "B055":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证错误原因：证件类型不匹配");
            break;
        case "B056":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
//            fault.setMsgAll("账户信息验证错误原因：证件号码不匹配");
            fault.setMsgAll("客户姓名不匹配");
            break;
        case "B057":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证错误原因：移动电话不匹配");
            break;
        case "B058":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证错误原因：家庭电话不匹配");
            break;
        case "B059":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证错误原因：姓名不匹配");
            break;
        case "B060":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证错误原因：生日不匹配");
            break;
        case "B061":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证错误原因：单位电话不匹配");
            break;
        case "B062":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证错误原因：查询密码不匹配");
            break;
        case "B063":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证错误原因：交易密码不匹配");
            break;
        case "B064":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证错误原因：卡片有效期不匹配");
            break;
        case "B065":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证错误原因：卡片验证码不匹配");
            break;
        case "B066":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证错误原因：直属联系人姓名不匹配");
            break;
        case "B067":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户信息验证错误原因：直属联系人电话不匹配");
            break;
        case "B068":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("查询不到对应账单信息");
            break;
        case "B078":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账单年月不能为空");
            break;
        case "B079":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效的账单年月");
            break;
        case "B088":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效挂失原因代码");
            break;
        case "B091":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("年费减免金额大于已收年费金额");
            break;
        case "B092":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("金额不能为空或负值");
            break;
        case "B093":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("短信发送标志不合法，只能为：Y、N、C");
            break;
        case "B094":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("等待调额复核结果，不能重复申请");
            break;
        case "B095":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("授权处理拒绝,年费减免失败");
            break;
        case "B097":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("查询密码错误次数超过最大次数");
            break;
        case "B098":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("交易密码错误次数超过最大次数");
            break;
        case "B100":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("卡卡片有效期不能为空");
            break;
        case "B104":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("卡片Cvv2不能为空");
            break;
        case "B107":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("限笔不能为空或负值");
            break;
        case "B108":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("卡号长度有误");
            break;
        case "B109":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("卡片未激活");
            break;
        case "B110":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("卡片已过期");
            break;
        case "B111":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("已销户");
            break;
        case "B112":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("已冻结");
            break;
        case "B113":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户已关闭");
            break;
        case "B114":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("卡片已挂失");
            break;
        case "B115":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户止付");
            break;
        case "B121":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("电话号码不能为空");
            break;
        case "B130":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
//            fault.setMsgAll("CVV2次数超限");
            fault.setMsgAll("安全码次数超限");
            break;
        case "L001":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("原交易日期不能为空");
            break;
        case "L002":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("原交易金额不能为空");
            break;
        case "L003":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("分期总期数不能为空 ");
            break;
        case "L004":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("分期手续费收取方式不能为空");
            break;
        case "L005":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("找不到原消费交易");
            break;
        case "L011":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("卡片已经失效");
            break;
        case "NAIC":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("未激活");
            break;
        case "NEXP":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("有效期之外");
            break;
        case "NPIN":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无交易密码");
            break;
        case "NQIN":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无查询密码");
            break;
        case "LOCQ":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("查询密码锁定");
            break;
        case "LOCP":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("交易密码锁定");
            break;
        case "M001":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("必输项出现空值");
            break;
        case "M009":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("卡片状态异常");
            break;
        case "M010":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("原密码未设置，请使用设置密码功能");
            break;
        case "M011":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("验密失败,加密机报错");
            break;
        case "M012":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("原密码错误");
            break;
        case "M019":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("该密码尚未设置");
            break;
        case "M020":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("该卡已经做过挂失补卡，不允许此操作");
            break;
        case "M80003":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效商户");
            break;
        case "M80006":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("出错");
            break;
        case "M80009":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("请求正在处理中");
            break;
        case "M80012":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效交易");
            break;
        case "M80013":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效金额");
            break;
        case "M80014":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效卡号(无此账号)");
            break;
        case "M80020":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效响应");
            break;
        case "M80021":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("不能采取行动");
            break;
        case "M80022":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("故障怀疑");
            break;
        case "M80025":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("找不到原始交易");
            break;
        case "M80030":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("格式错误");
            break;
        case "M80031":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("交换中心不支持的银行");
            break;
        case "M80036":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("受限制的卡");
            break;
        case "M80039":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无贷记账户");
            break;
        case "M80040":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("请求的功能尚不支持");
            break;
        case "M80042":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无此账户");
            break;
        case "M80043":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("被窃卡");
            break;
        case "M80051":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("资金不足");
            break;
        case "M80053":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无此储蓄卡账户");
            break;
        case "M80054":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("过期的卡");
            break;
        case "M80055":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("不正确的密码");
            break;
        case "M80056":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无此卡记录");
            break;
        case "M80057":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("不允许持卡人进行的交易");
            break;
        case "M80061":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("超出金额限制");
            break;
        case "M80062":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("受限制的卡");
            break;
        case "M80068":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("接收的响应超时");
            break;
        case "M80078":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效账户");
            break;
        case "M80094":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("重复交易");
            break;
        case "M80095":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("核对差错");
            break;
        case "M80099":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("PIN格式错");
            break;
        case "M800A0":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("MAC鉴别失败");
            break;
        case "M800A1":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("转账货币不一致");
            break;
        case "M800A3":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("转入行无此账户");
            break;
        case "M800A7":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("安全处理失败");
            break;
        case "M800D1":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("机构代码错误");
            break;
        case "M800D2":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("日期错误");
            break;
        case "M800D5":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无此文件");
            break;
        case "M800D7":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("文件锁定");
            break;
        case "M800D8":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("未成功");
            break;
        case "M800Y1":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("脱机交易成功");
            break;
        case "M800Z1":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("脱机交易失败");
            break;
        case "M80041":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("此卡已挂失或吞卡");
            break;
        case "S00049":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("通讯失败");
            break;
        case "S00018":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("超时无响应");
            break;
        case "999999":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("贷记卡通讯失败");
            break;
        case "SSSS":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("交易成功");
            break;
        case "S001":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("系统处理异常");
            break;
        case "S002":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("未知的服务码");
            break;
        case "S003":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("非法的请求字段");
            break;
        case "S004":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效操作类型");
            break;
        case "S006":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("报文验证错误");
            break;
        case "S007":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("无效的日期类型");
            break;

        case "DPS347":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("超出该产品当日支取转帐限额");
            break;
        case "HS3026":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("身份证号不匹配");
            break;
        case "HS3027":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("电话号码不匹配");
            break;
        case "HS3024":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("客户姓名不匹配");
            break;
        case "999996":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("银行卡卡号不存在");
            break;
        case "DPS308":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("银行账户余额不足");
            break;
        case "CMS158":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户已冻结");
            break;
        case "CMS153":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户挂失");
            break;
        case "CMS154":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户挂失");
            break;
        case "CMS192":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("账户已销户");
            break;
        case "CMS118":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("超过单笔支付限额");
            break;
        case "CMS119":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("超过累计支付限额");
            break;
        case "PUB400":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("银行卡状态不正确(止付，其他异常状态)");
            break;
        case "CMS177":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("银行卡状态不正确(止付，其他异常状态)");
            break;
        case "DPS306":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("银行卡状态不正确(止付，其他异常状态)");
            break;
        case "DPS345":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("超出该产品当日存入转帐限额");
            break;
        case "GLS106":
            fault.setCodeAll(AppCodeDict.BISCOR0001);
//            fault.setMsgAll("帐户[1019901201123001000]不允许透支");
            fault.setMsgAll("平台资金池账户不允许透支");
            break;
        default:
            fault.setCodeAll(AppCodeDict.BISCOR0001);
            fault.setMsgAll("核心错误: "+respMsg);
            break;
        }
    }
}
