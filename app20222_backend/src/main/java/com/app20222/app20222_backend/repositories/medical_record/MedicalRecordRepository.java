package com.app20222.app20222_backend.repositories.medical_record;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.constants.sql.medical_record.SQLMedicalRecord;
import com.app20222.app20222_backend.dtos.medical_record.IGetListMedicalRecord;
import com.app20222.app20222_backend.entities.medical_record.MedicalRecord;
import com.app20222.app20222_backend.enums.users.IdentityTypeEnum;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    @Query(nativeQuery = true, value = SQLMedicalRecord.GET_LIST_MEDICAL_RECORD)
    List<IGetListMedicalRecord> getListMedicalRecords(Long patientId, String patientName, String patientCode, String phoneNumber,
        Integer idType, String idNumber, String email, Date startDate, Date endDate);

}
