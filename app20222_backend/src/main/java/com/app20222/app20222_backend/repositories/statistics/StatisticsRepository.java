package com.app20222.app20222_backend.repositories.statistics;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.constants.sql.statistics.SQLStatistics;
import com.app20222.app20222_backend.dtos.statistics.IMonthSurgeryStatistics;
import com.app20222.app20222_backend.dtos.statistics.INumberOfSurgeryByDiseaseGroup;
import com.app20222.app20222_backend.dtos.statistics.INumberSurgeryPreviewDTO;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.entities.surgery.Surgery;

@Repository
public interface StatisticsRepository extends JpaRepository<Surgery, Long> {

    @Query(nativeQuery = true, value = SQLStatistics.GET_PREVIEW_CURRENT_SURGERY_NUM)
    INumberSurgeryPreviewDTO getPreviewCurrentNumberSurgery(Set<Long> lstViewableSurgeryId);

    @Query(nativeQuery = true, value = SQLStatistics.GET_DATA_NUM_OF_SURGERY_BY_DISEASE_GROUP_AND_INTERVAL)
    List<INumberOfSurgeryByDiseaseGroup> getListNumberOfSurgeryByDiseaseGroupAndInterval(Set<Long> lstViewableSurgeryId, String intervalType);

    @Query(nativeQuery = true, value = SQLStatistics.GET_PREVIEW_SURGERY_NUM_MONTHLY_IN_A_YEAR)
    List<IMonthSurgeryStatistics> getMonthSurgeryStatistics(Set<Long> lstViewableSurgeryId, Date firstDateOfYear, Date lastDateOfYear);

    @Query(nativeQuery = true, value = SQLStatistics.GET_PREVIEW_LIST_SURGERY)
    List<IGetListSurgery> getPreviewSurgeryList(Set<Long> lstViewableSurgeryId, Date startTime, Date endTime);

}
