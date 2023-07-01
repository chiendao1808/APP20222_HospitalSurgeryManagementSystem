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
@Table(name = "surgeries_files")
public class SurgeryFile {

    @Id
    @Column(name = "file_id", nullable = false)
    @NotNull
    private Long fileId;

    @Column(name = "surgery_id", nullable = false)
    @NotNull
    private Long surgeryId;

}
