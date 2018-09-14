package com.upay.gateway.client.mail.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.upay.gateway.client.mail.HtmlMailSendHandler;


/**
 * Created by Guo on 2016/9/30.
 */
public class MailSendTest {

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:META-INF/beans/*.xml");
        String channel = "mailcli";
        final String charsetName = "UTF-8";

        HtmlMailSendHandler mailSendHandler = context.getBean(HtmlMailSendHandler.class);
        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                        "交易成功"));
        Map<String, Object> body = (Map<String, Object>) m.getTarget().getBodys();

        body.put("mailTo", "569598269@qq.com");
        body.put("mailFrom", "254550232@qq.com");
        // body.put("mailContent",
        // "<html><head></head><body><h1>hello!!spring html Mail</h1></body></html>");
        body.put(
            "mailContent",
            "\n"
                    + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                    + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                    + "<head>\n"
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\n"
                    + "<title></title>\n"
                    + "<style>\n"
                    + "html,body,div,span,applet,object,iframe,blockquote,pre,abbr,acronym,address,big,cite,code,del,dfn,em,font,img,ins,kbd,p,q,s,samp,small,strike,strong,sub,sup,tt,var,dd,dl,dt,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,li,ul,button{border:0;outline:0;margin:0;padding:0}\n"
                    + "body{font-size:12px;font-family:Arial, 宋体,;background:#fff;color:#4d4d4d;line-height:18px}\n"
                    + "ul,li,ol{list-style:none}\n"
                    + "table,td,input,textarea{font-size:12px}\n"
                    + "h1,h3,h4,h5,h6,h2{font-size:14px;font-weight:700;margin:0;padding:0}\n"
                    + "table{border-collapse:collapse;border-spacing:0}\n"
                    + "input,button,textarea,select{font-family:inherit;font-weight:inherit;font-size:100%}\n"
                    + "select,input,button,textarea{font:100% Tahoma,Helvetica,Arial,sans-serif}\n"
                    + "a{text-decoration:none;cursor:pointer;color:#07F}\n"
                    + "a:hover{text-decoration:underline}\n"
                    + ".fixfloat{zoom:1}\n"
                    + ".clearfix{display:block;clear:both}\n"
                    + "\n"
                    + "\n"
                    + ".mt20{margin-top:20px;}\n"
                    + ".mt10{margin-top:10px;}\n"
                    + ".f14{font-size:14px;}\n"
                    + ".fb{font-weight:bold}\n"
                    + ".link1{color:#666666;text-decoration:none;}\n"
                    + ".link1:hover{text-decoration:none;}\n"
                    + "\n"
                    + ".orange{ color:#ff7200;text-decoration:underline;}\n"
                    + ".orange:hover{text-decoration:none;}\n"
                    + "\n"
                    + ".color1{color:#999999;}\n"
                    + "\n"
                    + ".main_wrap{width:700px;margin:0 auto;}\n"
                    + ".top_line{border-top:1px solid #dddddd;padding-top:5px;margin-top:40px;}\n"
                    + ".bot_line{border-bottom:1px solid #dddddd;padding-bottom:5px;}\n"
                    + ".right{float:right;width:400px;margin-top:48px;text-align:right;}\n"
                    + ".right span{margin:0 8px;color:#c2c1c1}\n"
                    + ".right a{margin-top:70px;}\n"
                    + "\n"
                    + ".main_wrap h3{font-size:14px;line-height:60px;padding:0 10px;color:#323232}\n"
                    + ".main_wrap p{line-height:24px;padding:0 10px;}\n"
                    + "\n"
                    + "</style>\n"
                    + "</head>\n"
                    + "\n"
                    + "<body>\n"
                    + "<div class=\"main_wrap bot_line\">\n"
                    + "     <div class=\"right\">\n"
                    + "          <a href=\"https://www.viinpay.com/index.xhtml\" class=\"gray\">登录</a>\n"
                    + "          <span>|</span>\n"
                    + "          <a href=\"https://www.viinpay.com/info/help/help-name-help.html\" class=\"gray\">帮助中心</a>\n"
                    + "     </div>\n"
                    + "\n"
                    + "     <a href=\"https://www.viinpay.com/\" ><img src=\"https://www.viinpay.com/images/logo.png\" alt=\"微银支付\"/></a>\n"
                    + "</div>\n"
                    + "\n"
                    + "\n"
                    + "<div class=\"main_wrap\">\n"
                    + "     <h3 class=\"mt10\">亲爱的会员:[$]您好！</h3>\n"
                    + "     <p class=\"f14\">您现在就可以激活并使用微银支付账户。账户激活成功后，您可以使用多种网上支付方式、增值服务和更多账户服务。</p>\n"
                    + "     <p class=\"mt20 f14 fb\"><a href=\"[$]\" class=\"orange\">点击此处激活账户</a></p>\n"
                    + "     <p class=\"mt20\">如果上述文字点击无效，请把下面网页地址复制到浏览器地址栏中打开：</p>\n"
                    + "     <p><a href=\"[$]\" class=\"link1\">[$]</a></p>\n"
                    + "</div>\n"
                    + "\n"
                    + "\n"
                    + "<div class=\"clearfix\"></div>\n"
                    + "<div class=\"main_wrap top_line\">\n"
                    + "     <p class=\"mt10 color1\">此为系统邮件，请勿回复</p>\n"
                    + "     <p class=\"color1\">请保管好您的邮箱，避免微银支付账户被他人盗用</p>\n"
                    + "     <p class=\"mt20 color1\">如有任何疑问，可访问微银支付网站访问 <a href=\"https://www.viinpay.com/\" class=\"link1\">https://www.viinpay.com/</a></p>\n"
                    + "     <p class=\"color1\">请保管好您的邮箱，避免微银支付账户被他人盗用</p>\n" + "</div>\n" + "</body>\n"
                    + "</html>\n");
        body.put("mailSubject", "邮件发送测试");

        m = mailSendHandler.handle(m);
        System.out.println(m);
    }

}
