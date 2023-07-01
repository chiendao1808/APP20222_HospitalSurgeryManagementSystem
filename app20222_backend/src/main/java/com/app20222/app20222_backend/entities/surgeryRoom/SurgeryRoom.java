package com.app20222.app20222_backend.entities.surgeryRoom;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.app20222.app20222_backend.entities.base.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "surgery_room")
public class SurgeryRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    @NotNull
    private String code;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "current_available")
    private Boolean currentAvailable;

    @Column(name = "on_service_at")
    private Date onServiceAt;
}
