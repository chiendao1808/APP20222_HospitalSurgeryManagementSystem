package com.app20222.app20222_backend.repositories.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.entities.mail.Mail;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {


    Boolean existsByCode(String mailCode);

}
