package com.app20222.app20222_fxapp.dto.requests.surgeryRoom;

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
public class SurgeryRoomUpdateDTO {

    String name;

    String address;

    String description;

    Boolean currentAvailable = false;

}