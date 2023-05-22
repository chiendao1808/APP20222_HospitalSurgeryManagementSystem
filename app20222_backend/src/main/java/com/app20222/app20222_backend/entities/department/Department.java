package com.app20222.app20222_backend.entities.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @NotNull
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    @NotNull
    private String code;

    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @Column(name = "logo_path")
    private String logoPath;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "description")
    private String description;

}
