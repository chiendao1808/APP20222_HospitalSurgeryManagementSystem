package com.app20222.app20222_fxapp.dto.requests.surgeryRoom;

import java.util.Date;
import javax.validation.constraints.NotNull;
import com.app20222.app20222_fxapp.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotNull
    String code;

    String address;

    String description;

    Boolean currentAvailable = false;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    Date onServiceAt;

}
