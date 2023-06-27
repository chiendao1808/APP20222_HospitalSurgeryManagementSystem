package com.app20222.app20222_backend.controllers.surgery;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app20222.app20222_backend.dtos.surgery.SurgeryCreateDTO;
import com.app20222.app20222_backend.dtos.surgery.SurgeryDetailDTO;
import com.app20222.app20222_backend.dtos.surgery.SurgeryUpdateDTO;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.enums.surgery.SurgeryStatusEnum;
import com.app20222.app20222_backend.services.surgery.SurgeryService;
import com.app20222.app20222_backend.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/surgery")
@Slf4j
public class SurgeryController {

    @Autowired
    private SurgeryService surgeryService;


    @PostMapping
    @Operation(description = "Tạo ca phẫu thuật mới")
    public void createSurgery(@RequestBody @Validated SurgeryCreateDTO dto){
        log.info("======= Started createSurgery ========");
        surgeryService.createSurgery(dto);
        log.info("======= End createSurgery ========");
    }

    @PutMapping
    @Operation(description = "Cập nhật thông tin ca phẫu thuật")
    public void updateSurgery(@RequestParam(name = "id") Long surgeryId,
        @RequestBody SurgeryUpdateDTO dto) {
        log.info("======= Started updateSurgery ========");
        surgeryService.updateSurgery(surgeryId, dto);
        log.info("======= End updateSurgery ========");
    }

    @GetMapping("/get-list")
    @Operation(description = "Lấy danh sách thông tin ca phẫu thuật")
    public List<IGetListSurgery> getListSurgery(
        @RequestParam(name = "surgeryName", required = false, defaultValue = "") String surgeryName,
        @RequestParam(name = "surgeryId", required = false, defaultValue = "-1") Long surgeryId,
        @RequestParam(name = "patientId", required = false, defaultValue = "-1") Long patientId,
        @RequestParam(name = "surgeryRoomId", required = false, defaultValue = "-1") Long surgeryRoomId,
        @RequestParam(name = "diseaseGroupId", required = false, defaultValue = "-1") Long diseaseGroupId,
        @RequestParam(name = "status", required = false, defaultValue = "ALL") SurgeryStatusEnum status,
        @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM)
        @RequestParam(name = "startedAt", required = false, defaultValue = "01/01/1970 00:00") Date startedAt,
        @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM)
        @RequestParam(name = "estimatedEndAt", required = false, defaultValue = "01/01/1970 00:00") Date estimatedEndAt
    ) {
        return surgeryService.getListSurgery(surgeryId, surgeryName, patientId, surgeryRoomId, diseaseGroupId, status, startedAt,
            estimatedEndAt);
    }

    @GetMapping("/get-details/{id}")
    @Operation(description = "Xem thông tin chi tiết ca phẫu thuật ")
    public SurgeryDetailDTO getDetailSurgery(@PathVariable(name = "id") Long id){
        return surgeryService.getDetailSurgery(id);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Xóa ca phẫu thuật")
    public void deleteSurgery(@PathVariable(name = "id") Long id){
        log.info("======= Started deleteSurgery ========");
        surgeryService.deleteSurgery(id);
        log.info("======= End deleteSurgery ========");
    }

    @PutMapping("/switch-status")
    @Operation(description = "Chuyển trạng thái ca phẫu thuật")
    public void switchSurgeryStatus(@RequestParam(name = "id") Long surgeryId, @RequestParam(name = "status") SurgeryStatusEnum status){
        log.info("======= Started switchSurgeryStatus ========");
        surgeryService.switchSurgeryStatus(surgeryId, status);
        log.info("======= End switchSurgeryStatus ========");
    }
}
