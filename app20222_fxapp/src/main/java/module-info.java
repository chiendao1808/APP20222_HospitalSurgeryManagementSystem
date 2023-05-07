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
        requires json.path;
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
    
    opens com.app20222.app20222_fxapp to javafx.fxml;

    // export package
    exports com.app20222.app20222_fxapp;
    exports com.app20222.app20222_fxapp.dto.responses;
    exports com.app20222.app20222_fxapp.dto.responses.users;

    // export view controller

    /**
     * Main View
     */
    exports com.app20222.app20222_fxapp.app_controllers.main_view;
    opens com.app20222.app20222_fxapp.app_controllers.main_view to javafx.fxml;

    /**
     * Login View
     */
    exports com.app20222.app20222_fxapp.app_controllers.login_view;
    opens com.app20222.app20222_fxapp.app_controllers.login_view to javafx.fxml;

}