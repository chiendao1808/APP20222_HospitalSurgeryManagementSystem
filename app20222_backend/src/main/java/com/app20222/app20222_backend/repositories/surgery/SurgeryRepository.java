package com.app20222.app20222_backend.repositories.surgery;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.app20222.app20222_backend.constants.sql.surgery.SQLSurgery;
import com.app20222.app20222_backend.dtos.surgery.IGetDetailSurgery;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.dtos.surgery.IGetOverlapSurgery;
import com.app20222.app20222_backend.dtos.surgery.IGetSurgeryAssignment;
import com.app20222.app20222_backend.entities.surgery.Surgery;

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

    @Query(nativeQuery = true, value = SQLSurgery.GET_DETAILS_SURGERY)
    IGetDetailSurgery getDetailSurgery(Long surgeryId);

    @Query(nativeQuery = true, value = SQLSurgery.GET_SURGERY_ASSIGNMENTS)
    List<IGetSurgeryAssignment> getSurgeryAssignment(Long surgeryId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = SQLSurgery.DELETE_ALL_SURGERY_FILE_ATTACHED_BY_SURGERY_ID)
    void deleteSurgeryFileBySurgeryId(Long surgeryId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Surgery surgery SET surgery.status = :status WHERE surgery.id = :surgeryId")
    Integer changeSurgeryStatus(Long surgeryId, Integer status);

}
