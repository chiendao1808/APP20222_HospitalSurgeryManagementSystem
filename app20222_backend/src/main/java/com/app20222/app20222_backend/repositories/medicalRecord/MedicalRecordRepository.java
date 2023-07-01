package com.app20222.app20222_backend.repositories.medicalRecord;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.constants.sql.medicalRecord.SQLMedicalRecord;
import com.app20222.app20222_backend.dtos.fileAttach.IGetFileAttach;
import com.app20222.app20222_backend.dtos.medicalRecord.IGetDetailMedicalRecord;
import com.app20222.app20222_backend.dtos.medicalRecord.IGetListMedicalRecord;
import com.app20222.app20222_backend.entities.medicalRecord.MedicalRecord;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    @Query(nativeQuery = true, value = SQLMedicalRecord.GET_LIST_MEDICAL_RECORD)
    List<IGetListMedicalRecord> getListMedicalRecords(Long patientId, String patientName, String patientCode, String phoneNumber,
        Integer idType, String idNumber, String email, Date startDate, Date endDate);

    @Query(nativeQuery = true, value = SQLMedicalRecord.GET_DETAIL_MEDICAL_RECORD)
    IGetDetailMedicalRecord getDetailMedicalRecord(Long medicalRecordId);

    @Query(nativeQuery = true, value = SQLMedicalRecord.GET_LIST_MEDICAL_RECORD_FILE)
    List<IGetFileAttach> getListFileAttach(Long medicalRecordId);

}
