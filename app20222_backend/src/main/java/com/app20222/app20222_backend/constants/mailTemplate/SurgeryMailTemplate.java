package com.app20222.app20222_backend.constants.mailTemplate;

public class SurgeryMailTemplate {

    public static final String CREATE_SURGERY_MAIL_TO_USER_TEMPLATE =
        "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "   <header>\n" +
            "      <meta content = \"text/html;charset=utf-8\">\n" +
            "   </header>\n" +
            "   <body>\n" +
            "      <h1> Xin chào, bạn đã được phân công vào một ca phẫu thuật mới</h1>\n" +
            "      <div>\n" +
            "         <p>Thông tin về ca phẫu thuật:</p>\n" +
            "         <span> - Tên ca phẫu thuật: $SURGERY_NAME</span> <br>\n" +
            "         <span> - Mã ca phẫu thuật: $SURGERY_CODE </span> <br>\n" +
            "         <span> - Vai trò: $SURGERY_ROLE_TYPE </span> <br>\n" +
            "         <span> - Thời gian thực hiện: $TIME </span> <br>\n" +
            "         <span> - Phòng phẫu thuật: $SURGERY_ROOM</span> <br>\n" +
            "         <p>Trân trọng, </p>\n" +
            "         <p>Đội ngũ quản trị bệnh viện</p>\n" +
            "      </div>\n" +
            "   </body>\n" +
            "</html>";


    public static final String CHANGE_ASSIGNMENT_SURGERY_MAIL_TO_USER_TEMPLATE =
        "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "   <header>\n" +
            "      <meta content = \"text/html;charset=utf-8\">\n" +
            "   </header>\n" +
            "   <body>\n" +
            "      <h1> Xin chào, bạn đã được phân công bổ sung vào một ca phẫu thuật mới</1>\n" +
            "      <div>\n" +
            "         <p>Thông tin về ca phẫu thuật:</p>\n" +
            "         <span> - Tên ca phẫu thuật: $SURGERY_NAME</span> <br>\n" +
            "         <span> - Mã ca phẫu thuật: $SURGERY_CODE </span> <br>\n" +
            "         <span> - Vai trò: $SURGERY_ROLE_TYPE </span> <br>\n" +
            "         <span> - Thời gian thực hiện: $TIME </span> <br>\n" +
            "         <span> - Phòng phẫu thuật: $SURGERY_ROOM</span> <br>\n" +
            "         <p>Trân trọng, </p>\n" +
            "         <p>Đội ngũ quản trị bệnh viện</p>\n" +
            "      </div>\n" +
            "   </body>\n" +
            "</html>";

}
