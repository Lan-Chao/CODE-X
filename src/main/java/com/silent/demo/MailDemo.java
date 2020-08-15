package com.silent.demo;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.net.smtp.SMTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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

    public void sendWithEnclosure() throws Exception {

//        log.info("准备发送邮件，{}", JSONObject.toJSONString(mail));

        boolean code = true;

        String[] mail = {"zhaochangren@didachuxing.com"};

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

        SMTPClient client = new SMTPClient();
        client.connect("zhaochangren@didachuxing.com");



//        javaMailSender.send();
        javaMailSender.send(mimeMessage);
    }



}
