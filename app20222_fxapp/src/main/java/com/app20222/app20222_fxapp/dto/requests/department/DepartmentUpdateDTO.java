package com.app20222.app20222_fxapp.dto.requests.department;


import javax.validation.constraints.NotNull;
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
public class DepartmentUpdateDTO {

    @NotNull
    String name;

    String logoPath;

    String address;

    String phoneNumber;

    String email;

    String description;
}
