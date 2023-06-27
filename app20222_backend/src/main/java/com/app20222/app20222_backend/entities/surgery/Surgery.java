package com.app20222.app20222_backend.entities.surgery;

import com.app20222.app20222_backend.entities.base.BaseEntity;
import com.app20222.app20222_backend.entities.file_attach.FileAttach;
import com.app20222.app20222_backend.entities.patient.Patient;
import com.app20222.app20222_backend.entities.surgery_room.SurgeryRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "surgery")
public class Surgery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private Integer type;

    @Column(name = "disease_group_id", nullable = false)
    private Long diseaseGroupId;

    @Column(name = "started_at", nullable = false)
    private Date startedAt;

    @Column(name = "estimated_end_at")
    private Date estimatedEndAt;

    @Column(name = "end_at")
    private Date endAt;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @ManyToOne
    @JoinColumn(name = "patient_id", updatable = false, insertable = false)
    private Patient patient;


    @Column(name = "surgery_room_id")
    private Long surgeryRoomId;

    @ManyToOne
    @JoinColumn(name = "surgery_room_id", updatable = false, insertable = false)
    private SurgeryRoom surgeryRoom;

    @Column(name = "status")
    private Integer status;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "surgeries_files",
            joinColumns = @JoinColumn(name = "surgery_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<FileAttach> lstFileAttached;

}
