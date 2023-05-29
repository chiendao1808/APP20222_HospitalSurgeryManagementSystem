package com.app20222.app20222_backend.repositories.surgery;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.constants.sql.surgery.SQLSurgery;
import com.app20222.app20222_backend.entities.surgery.Surgery;

@Repository
public interface SurgeryRepository extends JpaRepository<Surgery, Long> {

    @Query(value = "SELECT id FROM Surgery")
    Set<Long> findAllSurgeryIds();

    @Query(nativeQuery = true, value = SQLSurgery.GET_LIST_VIEWABLE_SURGERY_ID_BY_DEPARTMENT)
    Set<Long> findViewableSurgeryIdsByDepartment(Long createdBy, Long departmentId);

    @Query(nativeQuery = true, value = SQLSurgery.GET_LIST_VIEWABLE_SURGERY_ID_BY_USER)
    Set<Long> findViewableSurgeryIdsByUser(Long userId);

}
