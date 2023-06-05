package com.app20222.app20222_backend.services.mail.impl;

import java.io.File;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.entities.mail.Mail;
import com.app20222.app20222_backend.enums.mail.MailSentStatusEnum;
import com.app20222.app20222_backend.repositories.mail.MailRepository;
import com.app20222.app20222_backend.services.mail.MailService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailRepository mailRepository;

    @Value("${spring.mail.username}")
    private String mailHost;

    private final String baseCodeGenerator = "APP_20222_MAIL_";


    @Override
    public void sendMail(Mail mail) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(mail.getContent(), true);
            mimeMessageHelper.setFrom(mailHost);
            mimeMessageHelper.setTo(mail.getLstToAddress().toArray(String[]::new));

            /* add attachments (if exists or has attachments) */
            if (!Objects.isNull(mail.getLstFileAttached()) && !mail.getLstFileAttached().isEmpty()) {
                for (File file : mail.getLstFileAttached()) {
                    mimeMessageHelper.addAttachment(file.getName(), new FileSystemResource(file));
                }
            }

            try {
                // send email
                mailSender.send(mimeMessageHelper.getMimeMessage());
                mail.setStatus(MailSentStatusEnum.SENT_SUCCESS.getStatus());
            } catch (MailException e) {
                e.printStackTrace();
                mail.setStatus(MailSentStatusEnum.SENT_FAIL.getStatus());
            }

            // Lưu lịch sử gửi mail
            mail.setFromAddress(mailHost);
            mail.setSentTime(new Date());
            mail.setCode(generateMailCode());
            mailRepository.save(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate a unique mail code
     */
    private String generateMailCode() {
        Random random = new Random();
        String code = baseCodeGenerator + random.nextInt(90000) + 10000;
        while (mailRepository.existsByCode(code)) {
            code = baseCodeGenerator + random.nextInt(90000) + 10000;
        }
        return code;
    }
}
