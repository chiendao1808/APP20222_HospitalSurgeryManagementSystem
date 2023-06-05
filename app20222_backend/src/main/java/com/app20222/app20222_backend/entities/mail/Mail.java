package com.app20222.app20222_backend.entities.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import com.app20222.app20222_backend.common.coverter.ListStringConverter;
import com.app20222.app20222_backend.enums.mail.MailSentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "mail")
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "lst_to_address")
    @Convert(converter = ListStringConverter.class)
    private List<String> lstToAddress;

    @Column(name = "from_address", nullable = false)
    @NotNull
    private String fromAddress;

    @Column(name = "subject")
    @NotNull
    private String subject;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Integer status = MailSentStatusEnum.WAITING_SENT.getStatus();

    @Column(name = "is_has_attachments")
    private Boolean isHasAttachments;

    @Column(name = "sent_time")
    private Date sentTime;

    @Transient
    private List<File> lstFileAttached = new ArrayList<>();
}
