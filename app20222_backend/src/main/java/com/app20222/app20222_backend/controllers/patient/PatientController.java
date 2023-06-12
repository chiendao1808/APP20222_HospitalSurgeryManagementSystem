package com.app20222.app20222_backend.controllers.patient;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app20222.app20222_backend.dtos.patient.IGetDetailPatient;
import com.app20222.app20222_backend.dtos.patient.IGetListPatient;
import com.app20222.app20222_backend.dtos.patient.PatientCreateDTO;
import com.app20222.app20222_backend.dtos.patient.PatientUpdateDTO;
import com.app20222.app20222_backend.enums.users.IdentityTypeEnum;
import com.app20222.app20222_backend.services.patient.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/patient")
@Slf4j
public class PatientController {

    @Autowired
    private PatientService patientService;


    @GetMapping("/get-list")
    @Operation(description = "Lấy danh sách bệnh nhân trong hệ thống")
    public List<IGetListPatient> getListPatient(
        @RequestParam(name = "patientId", required = false, defaultValue = "-1") Long patientId,
        @RequestParam(name = "code", required = false, defaultValue = "") String code,
        @RequestParam(name = "identityType", required = false, defaultValue = "ALL") IdentityTypeEnum identityType,
        @RequestParam(name = "idNumber", required = false, defaultValue = "") String identificationNum,
        @RequestParam(name = "name", required = false, defaultValue = "") String name,
        @RequestParam(name = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
        @RequestParam(name = "email", required = false, defaultValue = "") String email) {
        return patientService.getListPatient(patientId, code, identityType, identificationNum, name, phoneNumber, email);
    }

    @GetMapping("/get-detail")
    @Operation(description = "Lấy thông tin chi tiết bệnh nhân")
    public IGetDetailPatient getDetailPatient(@RequestParam(name = "id") Long id) {
        return patientService.getDetailPatient(id);
    }


    @PostMapping
    @Operation(description = "Tạo mới bệnh nhân")
    public void createPatient(@RequestBody @Validated PatientCreateDTO createDTO) {
        patientService.createPatient(createDTO);
    }

    @PutMapping
    @Operation(description = "Cập nhật thông tin bệnh nhân")
    public void updatePatient(@RequestParam(name = "id") Long id,
        @RequestBody @Validated PatientUpdateDTO updateDTO) {
        patientService.updatePatient(id, updateDTO);
    }

}
