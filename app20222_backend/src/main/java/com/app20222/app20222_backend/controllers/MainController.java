package com.app20222.app20222_backend.controllers;

import com.app20222.app20222_backend.constants.ApiConstants;
import com.app20222.app20222_backend.dtos.responses.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping(ApiConstants.DEFAULT_API_PATH + "/hello")
@Slf4j
public class MainController {

    @GetMapping
    public List<BaseResponse> response(HttpServletRequest request){
        System.out.println(request.getRequestURL());
        return List.of(new BaseResponse(200, "Hello JavaFX Client !"), new BaseResponse(201,"First JavaFX App!"));
    }
}
