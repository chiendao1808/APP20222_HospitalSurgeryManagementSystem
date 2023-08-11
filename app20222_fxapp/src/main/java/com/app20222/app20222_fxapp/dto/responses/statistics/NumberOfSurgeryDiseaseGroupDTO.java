package com.app20222.app20222_fxapp.dto.responses.statistics;

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
public class NumberOfSurgeryDiseaseGroupDTO {

    Integer diseaseGroupId;

    String diseaseGroupName;

    Integer numOfSurgery;

}
