package com.app20222.app20222_backend.entities.features;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "features")
@TypeDefs(
    @TypeDef(name = "string-array", typeClass = StringArrayType.class)
)
public class Feature {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    @NotNull
    private String code;

    @Column(name = "parent_code", nullable = false)
    private String parentCode;

    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "lst_usable_role", columnDefinition = "text[]")
    @Type(type = "string-array")
    String[] lstUsableRole;
}
