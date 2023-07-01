package com.app20222.app20222_backend.dtos.medical_record;

import java.util.Date;
import com.app20222.app20222_backend.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

public interface IGetListMedicalRecord {

    Long getId();

    @Schema(description = "Id bệnh nhân")
    Long getPatientId();

    @Schema(description = "Tên bệnh nhân")
    String getPatientName();

    @Schema(description = "Mã số bệnh nhân")
    String getPatientCode();

    @Schema(description = "Ngày lập bản ghi hồ sơ bệnh án (ngày khám)")
    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    Date getCreatedAt();

    @Schema(description = "Id Người lập bản ghi hồ sơ")
    Long getCreatedById();

    @Schema(description = "Tên người lập bản ghi hồ sơ")
    String getCreatedByName();

}
