package com.app20222.app20222_backend.entities.fileAttach;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medical_records_files")
public class MedicalRecordFile {

    @Id
    @Column(name = "file_id", nullable = false)
    @NotNull
    private Long fileId;

    @Column(name = "medical_record_id", nullable = false)
    @NotNull
    private Long medicalRecordId;

}
