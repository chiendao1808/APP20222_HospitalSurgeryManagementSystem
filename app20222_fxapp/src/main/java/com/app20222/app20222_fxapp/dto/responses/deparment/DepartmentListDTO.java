package com.app20222.app20222_fxapp.dto.responses.deparment;

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
public class DepartmentListDTO {

    Long id;

    String code;

    String name;

    String address;

    String phoneNumber;

    String email;

    String description;

    String logoPath;

}
