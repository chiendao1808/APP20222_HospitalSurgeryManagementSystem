package com.app20222.app20222_backend.controllers;

import com.app20222.app20222_backend.dtos.responses.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/hello")
@Tag(name = "Main Controller")
@Slf4j
public class MainController {

    @GetMapping
    public List<BaseResponse> response(HttpServletRequest request){
        System.out.println(request.getRequestURL());
        return List.of(new BaseResponse(200, "Hello JavaFX Client !"), new BaseResponse(201,"First JavaFX App!"));
    }
}
