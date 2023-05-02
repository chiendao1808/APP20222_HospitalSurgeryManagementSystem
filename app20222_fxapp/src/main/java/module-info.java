// Khi thêm mới một dependency trong file pom.xml -> thực hiện requires
module com.app20222.app20222_fxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

        // Object JSON Mapper
        requires com.fasterxml.jackson.core;
        requires com.fasterxml.jackson.databind;
        requires com.fasterxml.jackson.annotation;
        requires lombok;
        requires org.mapstruct.processor;
        requires lombok.mapstruct;
        requires org.apache.commons.io;
        requires org.apache.commons.codec;


        // Http request + json proccess
        requires java.net.http;
        requires org.apache.httpcomponents.httpcore;
        requires org.apache.httpcomponents.httpclient;
        requires okhttp3;
        requires json.path;
        requires org.json;
        requires com.google.common;
        requires com.google.api.client;


        // FX Extended Module
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
            requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
            requires com.almasb.fxgl.all;
    
    opens com.app20222.app20222_fxapp to javafx.fxml;

    // export modules
    exports com.app20222.app20222_fxapp;
    exports com.app20222.app20222_fxapp.app_controllers;
    exports com.app20222.app20222_fxapp.dto.responses;

    opens com.app20222.app20222_fxapp.app_controllers to javafx.fxml;
    exports com.app20222.app20222_fxapp.dto.responses.users;
}