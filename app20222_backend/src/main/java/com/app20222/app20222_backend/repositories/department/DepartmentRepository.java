package com.app20222.app20222_backend.repositories.department;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.constants.sql.department.SQLDepartment;
import com.app20222.app20222_backend.dtos.department.IGetListDepartment;
import com.app20222.app20222_backend.entities.department.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "SELECT department.id FROM Department department")
    Set<Long> getAllDepartmentId();

    Boolean existsByCode(String code);

    @Query(nativeQuery = true, value = SQLDepartment.GET_LIST_DEPARTMENT)
    List<IGetListDepartment> getListDepartment(Long id, String code, String name, String email, String phone);
}
