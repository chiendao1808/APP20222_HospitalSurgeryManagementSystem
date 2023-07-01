package com.app20222.app20222_backend.dtos.surgeryRoom;

import java.util.Date;
import javax.validation.constraints.NotNull;
import com.app20222.app20222_backend.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurgeryRoomCreateDTO {

    @NotNull
    String name;

    @Schema(description = "Mã số phòng đồng bộ với các mã số phòng trong hệ thống bệnh viện")
    @NotNull
    String code;

    String address;

    String description;

    @Schema(description = "Ngày đưa vào hoạt động")
    Boolean currentAvailable = false;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    Date onServiceAt;
}
