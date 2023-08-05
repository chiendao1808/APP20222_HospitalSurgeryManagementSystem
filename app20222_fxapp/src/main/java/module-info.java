// Khi thêm mới một dependency trong file pom.xml -> thực hiện requires
module com.app20222.app20222_fxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    // Common utils
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires lombok;
    requires org.mapstruct.processor;
    requires lombok.mapstruct;
    requires org.apache.commons.io;
    requires org.apache.commons.codec;
    requires java.validation;

    // Http request + json proccess
    requires java.net.http;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires okhttp3;
    requires org.json;
    requires com.google.common;
    requires com.google.api.client;
    requires org.apache.httpcomponents.httpmime;

    // FX Extended Module
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.app20222.app20222_fxapp to javafx.fxml;

    // export request package
    exports com.app20222.app20222_fxapp.dto.requests.users;
    exports com.app20222.app20222_fxapp.dto.requests.auth;
    exports com.app20222.app20222_fxapp.dto.requests.patient;
    exports com.app20222.app20222_fxapp.dto.requests.surgery;
    exports com.app20222.app20222_fxapp.dto.requests.surgeryRoom;
    exports com.app20222.app20222_fxapp.dto.requests.department;
    exports com.app20222.app20222_fxapp.dto.requests.medicalRecord;

    // export response package
    exports com.app20222.app20222_fxapp.dto.responses.users;
    exports com.app20222.app20222_fxapp.dto.responses.auth;
    exports com.app20222.app20222_fxapp.dto.responses.exception;
    exports com.app20222.app20222_fxapp.dto.responses.patient;
    exports com.app20222.app20222_fxapp.dto.responses.surgery;
    exports com.app20222.app20222_fxapp.dto.responses.surgeryRoom;
    exports com.app20222.app20222_fxapp.dto.responses.deparment;
    exports com.app20222.app20222_fxapp.dto.responses.medicalRecord;
    exports com.app20222.app20222_fxapp;

    // other dto exports
    exports com.app20222.app20222_fxapp.dto.file_attach;
    exports com.app20222.app20222_fxapp.dto.common;
    exports com.app20222.app20222_fxapp.dto.responses;
    // export view controller

    /**
     * Main View
     */
    opens com.app20222.app20222_fxapp.app_controllers.main_view to javafx.fxml;
    exports com.app20222.app20222_fxapp.app_controllers.main_view;

    /**
     * Login View
     */
    opens com.app20222.app20222_fxapp.app_controllers.login_view to javafx.fxml;
    exports com.app20222.app20222_fxapp.app_controllers.login_view;

    /**
     * bệnh nhân
     */
    opens com.app20222.app20222_fxapp.app_controllers.patient_view to javafx.fxml;
    exports com.app20222.app20222_fxapp.app_controllers.patient_view;
    /**
     * Hồ so benh an
     */
    opens com.app20222.app20222_fxapp.app_controllers.medicalRecord_view to javafx.fxml;
    exports com.app20222.app20222_fxapp.app_controllers.medicalRecord_view;
    /**
     * ca phâu thuật
     */
    opens com.app20222.app20222_fxapp.app_controllers.surgery_view to javafx.fxml;
    exports com.app20222.app20222_fxapp.app_controllers.surgery_view;
    /**
     * người dùng
     */
    opens com.app20222.app20222_fxapp.app_controllers.user_view to javafx.fxml;
    exports com.app20222.app20222_fxapp.app_controllers.user_view;
    /**
     * phòng phẫu thuật
     */
    opens com.app20222.app20222_fxapp.app_controllers.surgeryRoom_view to javafx.fxml;
    exports com.app20222.app20222_fxapp.app_controllers.surgeryRoom_view;
    /**
     * khoa bộ phận
     */
    opens com.app20222.app20222_fxapp.app_controllers.department_view to javafx.fxml;
    exports com.app20222.app20222_fxapp.app_controllers.department_view;

}