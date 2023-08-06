package com.app20222.app20222_fxapp.dto.file_attach;

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
public class FileAttachDTO {
    
    Long fileId;
    
    String fileName;
    
    String location;

    Integer type;

}
