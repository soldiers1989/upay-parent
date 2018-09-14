package com.upay.gateway.client.mail;

import static com.pactera.dipper.core.utils.Constants.MDC.STEP;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.pactera.dipper.core.AppCtxDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.impl.SimpleMessage;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.core.utils.Constants;
import com.upay.gateway.client.mail.bean.Mail;


/**
 * Created by Guo on 2016/9/28. 邮件发送处理
 */
public class HtmlMailSendHandler extends AppCtxDipperHandler {
    private static final Logger LOG = LoggerFactory.getLogger(HtmlMailSendHandler.class);

    private JavaMailSender mailSender;


    @SuppressWarnings("all")
    @Override
    public Message handle(Message m) throws Exception {
        MDC.put(Constants.MDC.ID, m.getId());
        MDC.put(STEP, m.getFlowStep() + "");
        LOG.debug("\nsourceMessage={}", m);
        long mailHandlerTime = System.currentTimeMillis();

        Message message = m;

        try {
            // 将Message中bodys转换为Mail对象
            SimpleMessage target = m.getTarget();
            Mail mail = new Mail();
            BeanCopyUtil.copyMap2Bean((Map<String, Object>) target.getBodys(), mail);
            LOG.debug("copymap2bean waste time:[{}]ms", System.currentTimeMillis() - mailHandlerTime);
            // 建立邮件消息,发送简单邮件和html邮件的区别
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            // 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用,
            // multipart模式
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            // 设置收件人，寄件人
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setFrom(mail.getMailFrom());
            messageHelper.setSubject(mail.getMailSubject());
            // true 表示启动HTML格式的邮件
            messageHelper.setText(mail.getMailContent(), true);
            LOG.info("to::::::" + mail.getMailTo());
            LOG.info("from::::::" + mail.getMailFrom());
            LOG.info("subject::::::" + mail.getMailSubject());
            LOG.info("content::::::" + mail.getMailContent());
            // 发送邮件
            mailSender.send(mimeMessage);// 连接并发送邮件
            LOG.info("发送邮件成功");
        } catch (Exception e) {
            throw e;
        } finally {
            LOG.debug("mailHandlerTime waste time:[{}]ms", System.currentTimeMillis() - mailHandlerTime);
        }

        LOG.debug("\ntargetMessage={}", message);

        return message;
    }


    public JavaMailSender getMailSender() {
        return mailSender;
    }


    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
