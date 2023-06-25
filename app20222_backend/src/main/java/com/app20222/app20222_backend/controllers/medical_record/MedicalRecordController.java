package com.app20222.app20222_backend.controllers.medical_record;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app20222.app20222_backend.dtos.medical_record.IGetListMedicalRecord;
import com.app20222.app20222_backend.dtos.medical_record.MedicalRecordCreateDTO;
import com.app20222.app20222_backend.enums.users.IdentityTypeEnum;
import com.app20222.app20222_backend.services.medical_record.MedicalRecordService;
import com.app20222.app20222_backend.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/medical-record")
@Slf4j
public class MedicalRecordController {


    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    @Operation(description = "Tạo bản ghi hồ sơ bệnh án")
    public void createMedicalRecord(@RequestBody MedicalRecordCreateDTO createDTO) {
        medicalRecordService.createMedicalRecord(createDTO);
    }

    @GetMapping("/get-list")
    @Operation(description = "Lấy danh sách các bản ghi hồ sơ bệnh án theo lần khám")
    List<IGetListMedicalRecord> getListMedicalRecords(
        @RequestParam(name = "patientId", required = false, defaultValue = "-1") Long patientId,
        @RequestParam(name = "patientName", required = false, defaultValue = "") String patientName,
        @RequestParam(name = "patientCode", required = false, defaultValue = "") String patientCode,
        @RequestParam(name = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
        @RequestParam(name = "idType", required = false, defaultValue = "ALL") IdentityTypeEnum identityType,
        @RequestParam(name = "idNum", required = false, defaultValue = "") String idNum,
        @RequestParam(name = "email", required = false, defaultValue = "") String email,
        @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH)
        @RequestParam(name = "startDate", required = false, defaultValue = "01/01/1970") Date startDate,
        @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH)
        @RequestParam(name = "startDate", required = false, defaultValue = "01/01/1970") Date endDate) {
        return null;
    }

}
