package com.silent.demo;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.net.smtp.SMTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedInputStream;
import java.util.Map;

/**
 * @author zhaochangren
 * @Title: MailDemo
 * @ProjectName CODE-X
 * @Description: mail
 * @date 2020/7/7 16:55
 */

@Service
public class MailDemo {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    /**
     * @throws MessagingException : Could not connect to SMTP host , 可以重试
     * */
    public void sendWithEnclosure() throws Exception {

        boolean code = true;

        String[] mail = {"11111111@qq.com"};

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        System.out.println(mimeMessageHelper.isValidateAddresses());
        // 邮件发送来源
        mimeMessageHelper.setFrom(mailProperties.getUsername());
        // 邮件发送目标
        mimeMessageHelper.setTo(mail);
        // 设置标题
        mimeMessageHelper.setSubject("title");
        // 设置内容
        mimeMessageHelper.setText("test");
        System.out.println(mimeMessageHelper.isValidateAddresses());

        javaMailSender.send(mimeMessage);
    }



}
