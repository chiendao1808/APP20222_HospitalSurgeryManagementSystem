package com.app20222.app20222_backend.entities.medicalRecord;

import com.app20222.app20222_backend.entities.base.BaseEntity;
import com.app20222.app20222_backend.entities.fileAttach.FileAttach;
import com.app20222.app20222_backend.entities.patient.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medical_record")
public class MedicalRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    private Patient patient;

    @Column(name = "summary")
    private String summary;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "medical_records_files",
        joinColumns = @JoinColumn(name = "medical_record_id"),
        inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<FileAttach> lstFileAttached;
}
