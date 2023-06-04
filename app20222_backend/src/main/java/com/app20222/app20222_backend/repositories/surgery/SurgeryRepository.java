package com.app20222.app20222_backend.repositories.surgery;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.constants.sql.surgery.SQLSurgery;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.dtos.surgery.IGetOverlapSurgery;
import com.app20222.app20222_backend.entities.surgery.Surgery;
import com.app20222.app20222_backend.enums.surgery.SurgeryStatusEnum;

@Repository
public interface SurgeryRepository extends JpaRepository<Surgery, Long> {

    @Query(value = "SELECT id FROM Surgery")
    Set<Long> findAllSurgeryIds();

    @Query(nativeQuery = true, value = SQLSurgery.GET_LIST_VIEWABLE_SURGERY_ID_BY_DEPARTMENT)
    Set<Long> findViewableSurgeryIdsByDepartment(Long createdBy, Long departmentId);

    @Query(nativeQuery = true, value = SQLSurgery.GET_LIST_VIEWABLE_SURGERY_ID_BY_USER)
    Set<Long> findViewableSurgeryIdsByUser(Long userId);

    @Query(nativeQuery = true, value = SQLSurgery.GET_OVERLAP_SURGERY)
    List<IGetOverlapSurgery> getOverlapSurgery(Date startedTime, Date estimatedEndTime);

    @Query(nativeQuery = true, value = SQLSurgery.GET_LIST_SURGERY)
    List<IGetListSurgery> getListSurgery(Set<Long> lstViewableSurgeryId, String surgeryName, Long patientId, Long diseaseGroupId, Long surgeryRoomId,
        Integer status, Date startedAt, Date estimatedEndAt);

}
