package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

/**
 * Created by cw on 2017/7/19.
 */
@Component
public class MailUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${mail.fromMail.addr}")
    private String from;

    public boolean sendRegisterMail(String email, String code) {
        MimeMessage message = mailSender.createMimeMessage();

        String register_link = "http://118.89.159.95:10352/api/email=" + email + "/code=" +code;

        //创建邮件正文
        Context context = new Context();
        context.setVariable("register_link", register_link);
        String emailContent = templateEngine.process("UserRegisterTemplate", context);
        String sub = "低电查询验证邮件";
        return tosend(message, email, emailContent, sub);
    }

    public boolean sendLowPowerMail(String email, String dorm, String power, String dayTime) {
        MimeMessage message = mailSender.createMimeMessage();
        dorm = dorm + "寝室";

        //创建邮件正文
        Context context = new Context();
        context.setVariable("dormName", dorm);
        context.setVariable("power", power);
        context.setVariable("dayTime", dayTime);
        String emailContent = templateEngine.process("UserLowPowerTemplate", context);
        String sub = "寝室低电通知";
        return tosend(message, email, emailContent, sub);

    }

    public boolean sendFindBackPasswordMail(String email) {
        UUID uuid = UUID.randomUUID();

        MimeMessage message = mailSender.createMimeMessage();

        String findback_link = "http://localhost:10352/api/email=" + email + "/ucode=" +uuid;

        //创建邮件正文
        Context context = new Context();
        context.setVariable("findback_link", findback_link);
        context.setVariable("email", email);
        String emailContent = templateEngine.process("FindBackTemplate", context);
        String sub = "低电查询密码找回邮件";

        return tosend(message, email, emailContent, sub);

    }

    public boolean sendFindBackPasswordCodeMail(String email, String code) {
        MimeMessage message = mailSender.createMimeMessage();


        //创建邮件正文
        Context context = new Context();
        context.setVariable("confirm_code", code);
        context.setVariable("email", email);
        String emailContent = templateEngine.process("ConfirmCodeTemplate", context);
        String sub = "低电查询密码找回邮件";

        return tosend(message, email, emailContent, sub);

    }

    private boolean tosend(MimeMessage message, String email, String emailContent, String sub) {
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject(sub);
            helper.setText(emailContent, true);

            mailSender.send(message);
            logger.info("html邮件发送成功");
            return true;
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
            return false;
        }
    }

}
