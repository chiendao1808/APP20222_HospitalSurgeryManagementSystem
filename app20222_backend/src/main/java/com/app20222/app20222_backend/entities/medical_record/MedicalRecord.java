package com.app20222.app20222_backend.entities.medical_record;

import com.app20222.app20222_backend.entities.file_attach.FileAttach;
import com.app20222.app20222_backend.entities.patient.Patient;
import com.app20222.app20222_backend.entities.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medical_recored")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "summary")
    private String summary;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "medical_records_files",
        joinColumns = @JoinColumn(name = "medical_record_id"),
        inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<FileAttach> lstFileAttached;
}
