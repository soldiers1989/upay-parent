package com.upay.busi.usr;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.impl.conn.Wire;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.zxing.Reader;
import com.google.zxing.Writer;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.DataBaseConstants_BATCH;
//import com.upay.commons.encryptor.UnionAPI;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestUserPrelogin {

//    private Logger logger = LoggerFactory.getLogger(TestUserPrelogin.class);

//    @Autowired
//    UserPreloginService pre;
    @Resource
    IDipperCached ca;
//    @Resource
//    UnionAPI un;
    @Resource
    IDaoService dao;
    
    @Test
    public void execute() throws Exception {
//        Map<String,Object> map=new HashMap<String,Object>();
//        map.put("transAmt", 0.07);
//        map.put("orderStat", "2");
//        List<String> transSeqList=dao.selectList(PayFlowListPo.class.getName().concat(".getaaa"),map);
//        StringBuffer buf=new StringBuffer();
//        PrintWriter p=null;
//        for(int j=0;j<transSeqList.size();j++){            
//            buf.append(transSeqList.get(j)).append("\n");
//        }
//        if(buf.length()>0){                
//            File file=new File("D:\\data_PAY2002.config");
//            FileWriter w=new FileWriter(file,true);
//            p=new PrintWriter(w);
//            p.print(buf.toString());
//            p.flush();
//        }
//        p.close();
//        Map<String,Object> map=new HashMap<String,Object>();
//        map.put("transAmt", 0.07);
//        map.put("orderStat", "0");
//        List<String> orderList=dao.selectList(PayFlowListPo.class.getName().concat(".getbbb"),map);
//        StringBuffer buf=new StringBuffer();
//        for(int i=0;i<orderList.size();i++){
//            buf.append(orderList.get(i)).append("\n");
//        }
//        File file=new File("D:\\data_PAY0015.config");
//        PrintWriter p=new PrintWriter(file);
//        p.print(buf.toString());
//        p.close();
        
        
//        ca.set("UPAY_SESSION_APP:UR000000000777", "SHANG",3*60);
//        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        PayOrderListPo order=new PayOrderListPo();
//        order.setOrderNo("UPAY201612190000003039");
//        order=dao.selectOne(order);
//        Date now=new Date();
//        Date createTime=order.getOrderTime();
//        System.out.println(order.getOuterOrderEndDate()==null?false:order.getOuterOrderEndDate().compareTo(now)>=0?false:true);
//        System.out.println(sim.format(now));
//        System.out.println(sim.format(createTime));
//        System.out.println((now.getTime() - createTime.getTime()) / 1000L / 60L);
//        System.out.println(order.getOrderLmtTime());
//        System.out.println((((now.getTime() - createTime.getTime()) / 1000L / 60L) - order.getOrderLmtTime())>=0);
//        System.out.println(order.getOuterOrderEndDate().compareTo(now)>=0);
//        boolean checkMerDate=(order.getOuterOrderEndDate()==null?false:order.getOuterOrderEndDate().compareTo(now)>=0?false:true)||(((int) ((now.getTime() - createTime.getTime()) / 1000L / 60L) - order.getOrderLmtTime()) >= 0);
//        System.out.println(checkMerDate);
//        System.out.println(((int) ((now.getTime() - createTime.getTime()) / 1000L / 60L) - order.getOrderLmtTime()) >= 0||order.getOuterOrderEndDate()==null?false:order.getOuterOrderEndDate().compareTo(now)>=0?false:true);
//        Date now=new Date();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("now", now);
//        map.put("transCode", "SI_XXX0001");
//        map.put("merNo", "12345");
//        map.put("secMerNo", "123451");
//        map=dao.selectOne("com.upay.dao.po.fee.FeeGetPo.getOneActive",map);
//        System.out.println(map);
//        UsrPwdListPo usr=new UsrPwdListPo();
//        usr.setUserId("UR000000000110");
//        usr.setCountDate(new SimpleDateFormat("yyyy-MM-dd").parse("2016-10-13"));
//        usr=dao.selectOne(usr);
//        System.out.println(usr.getLogDayErr());
//        System.out.println(usr.getLogTotErr());
//        int logDayMaxErr =
//                new BigDecimal(ParmsContext.getParmByKey(CmparmConstants.LOG_DAY_MAX_ERR).toString())
//                    .intValue();
//        int logTotMaxErr =
//                new BigDecimal(ParmsContext.getParmByKey(CmparmConstants.LOG_TOT_MAX_ERR).toString()).intValue();
//        System.out.println(logDayMaxErr);
//        System.out.println(logTotMaxErr);

//        Map<String,Object> map=new HashMap<String,Object>();
//        map.put("orderNo", "UPAY201609050000000181");
//        List<Object> mm=dao.selectList(PayFlowListPo.class.getName().concat(".test"), map);
//        System.out.println(mm);
//        String str=null;
//        if(StringUtils.isNotBlank(str)){
//            System.out.println(un.encrypt(str));
//        }
//        un.getShortConnection();
//        System.out.println(un.encrypt("123123"));
//        System.out.println(ca.get("SESSION_WEB_INVALID_MINUTE"));
        System.out.println(ca.set(CacheConstants.SESSION_WEB.concat("UR000000000528"),"5e3f470359fb40f49103267e965d9475",30*60));
//        System.out.println(ca.set(CacheConstants.SESSION_APP.concat("UR000000000172"),"5e3f470359fb40f49103267e965d9475",30*60));
//        ca.set(CacheConstants.SMS_RESEND_TIMEOUT.concat("13764238132"), "999999", 30*60);
//        ca.set(CacheConstants.SMS_NO.concat("13764238132"), "999999", 30*60);
//        System.out.println(DipperParm.getParmByKey(CommonConstants_GNR.ORDER_LMT_TIME));
//        Thread.sleep(5*1000);
//        System.out.println(ca.get(CacheConstants.SESSION_WEB.concat("UR000000000063")));
//        Thread.sleep(6*1000);
//        System.out.println(ca.get(CacheConstants.SESSION_WEB.concat("UR000000000063")));
//        UserPreloginDto dto=new UserPreloginDto();
//        dto.setUserId("UR000000000003");
//        dto.setChnlId("01");
//        dto.setMobile("12321212323");
//        dto.setLoginMode("1");
//        dto.setAddrGetFlag("1");
//        dto.setLoginStat("1");
//        UserPreloginDto dt= pre.execute(dto, null);
//        System.out.println(dt);
//        System.out.println(Md5Util.toMD5("123456"));
    }
    public static void main(String[] args) throws Exception {
    }
    public static String chineseToUnicode(String str){  
        String result="";  
        for (int i = 0; i < str.length(); i++){  
            int chr1 = (char) str.charAt(i);  
            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)  
                result+="\\u" + Integer.toHexString(chr1);  
            }else{  
                result+=str.charAt(i);  
            }  
        }  
        return result;  
    }
//    class A{
//        private Map<String,Object> map=new HashMap<String,Object>();
//    }
}
