package com.app20222.app20222_backend.entities.surgery;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.app20222.app20222_backend.entities.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_surgeries")
public class UserSurgery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "surgery_id")
    private Long surgeryId;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "surgery_id", insertable = false, updatable = false)
    private Surgery surgery;

    @Column(name = "surgery_role_type", nullable = false)
    private Integer surgeryRoleType;

}
