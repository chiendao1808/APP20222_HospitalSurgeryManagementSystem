package com.app20222.app20222_backend.entities.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseEntity {

    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = "modified_by")
    private Long modifiedBy;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
}
