package com.app20222.app20222_backend.entities.fileAttach;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "file_attach")
public class FileAttach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name")
    private String fileName;

    @Column(name = "type")
    private Integer fileType;

    @Column(name = "file_ext")
    private String fileExtension;

    @Column(name = "size")
    private Long size;

    @Column(name = "stored_type")
    private Integer storedType;

    @Column(name = "location")
    private String location;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
