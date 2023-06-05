package com.app20222.app20222_backend.services.mail;

import java.io.File;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.entities.mail.Mail;

@Service
public interface MailService {

    void sendMail(Mail mail);

}
