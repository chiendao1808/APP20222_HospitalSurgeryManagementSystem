package com.app20222.app20222_backend.services.medical_record;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.dtos.medical_record.IGetListMedicalRecord;
import com.app20222.app20222_backend.dtos.medical_record.MedicalRecordCreateDTO;
import com.app20222.app20222_backend.enums.users.IdentityTypeEnum;

@Service
public interface MedicalRecordService {

    /**
     * Tạo mới bản ghi hồ sơ bệnh án theo lần khám
     */
    void createMedicalRecord(MedicalRecordCreateDTO createDTO);

    /**
     * Tìm kiếm danh sách bản ghi hồ sơ bệnh án
     */
    List<IGetListMedicalRecord> getListMedicalRecords(Long patientId, String patientName, String patientCode, String phoneNumber,
        IdentityTypeEnum idType, String idNumber, String email, Date startDate, Date endDate);

}
