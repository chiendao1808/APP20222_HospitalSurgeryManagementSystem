package com.app20222.app20222_fxapp.dto.requests.surgery;

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
public class SurgeryRoleDTO {

    Long assigneeId;

    String assigneeName;

    Integer surgeryRoleType;
}
