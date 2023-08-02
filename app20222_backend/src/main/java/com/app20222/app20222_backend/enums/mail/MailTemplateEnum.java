package com.app20222.app20222_backend.enums.mail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app20222.app20222_backend.constants.mailTemplate.SurgeryMailTemplate;
import lombok.Getter;

@Getter
public enum MailTemplateEnum {

    CREATE_SURGERY_TO_USER_MAIL_TEMPLATE("SURGERY-01", "Phân công ca phẫu thuật mới",
        SurgeryMailTemplate.CREATE_SURGERY_MAIL_TO_USER_TEMPLATE,
        Arrays.asList("$SUGERY_NAME", "$SUGERY_CODE", "$TIME", "$SURGEY_ROOM")),

    CHANGE_ASSIGNMENT_SURGERY_MAIL_TO_USER_TEMPLATE("SURGERY-02", "Phân công bổ sung ca phẫu thuật",
        SurgeryMailTemplate.CHANGE_ASSIGNMENT_SURGERY_MAIL_TO_USER_TEMPLATE,
        Arrays.asList("$SUGERY_NAME", "$SUGERY_CODE", "$TIME", "$SURGEY_ROOM"));

    private final String code;

    private final String subject;

    private final String rawContent;

    private final List<String> mailParams;

    MailTemplateEnum(String code, String subject, String rawContent, List<String> mailParams){
        this.code = code;
        this.subject = subject;
        this.rawContent = rawContent;
        this.mailParams = mailParams;
    }

    private static final Map<String, MailTemplateEnum> mapMailTemplate = new HashMap<>();

    static {
        for(MailTemplateEnum templateEnum : MailTemplateEnum.values()){
            mapMailTemplate.put(templateEnum.code, templateEnum);
        }
    }

    public static MailTemplateEnum valueOfCode(String code){
        return mapMailTemplate.get(code);
    }


}
