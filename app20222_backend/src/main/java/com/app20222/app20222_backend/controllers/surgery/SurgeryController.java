package com.app20222.app20222_backend.controllers.surgery;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app20222.app20222_backend.dtos.requests.surgery.SurgeryCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/surgery")
@Slf4j
public class SurgeryController {



    @PostMapping
    @Operation(description = "Tạo ca phẫu thuật mới")
    public void createSurgery(@RequestBody @Validated SurgeryCreateDTO dto){
    }


}
