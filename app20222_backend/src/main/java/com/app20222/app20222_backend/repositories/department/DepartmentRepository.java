package com.app20222.app20222_backend.repositories.department;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.entities.department.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "SELECT department.id FROM Department department")
    Set<Long> getAllDepartmentId();

    Boolean existsByCode(String code);
}
