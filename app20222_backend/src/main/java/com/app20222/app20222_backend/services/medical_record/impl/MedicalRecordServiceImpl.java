package com.app20222.app20222_backend.services.medical_record.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app20222.app20222_backend.dtos.medical_record.IGetListMedicalRecord;
import com.app20222.app20222_backend.dtos.medical_record.MedicalRecordCreateDTO;
import com.app20222.app20222_backend.entities.file_attach.MedicalRecordFile;
import com.app20222.app20222_backend.entities.medical_record.MedicalRecord;
import com.app20222.app20222_backend.enums.users.IdentityTypeEnum;
import com.app20222.app20222_backend.repositories.file_attach.MedicalRecordFileRepository;
import com.app20222.app20222_backend.repositories.medical_record.MedicalRecordRepository;
import com.app20222.app20222_backend.services.medical_record.MedicalRecordService;
import com.app20222.app20222_backend.utils.auth.AuthUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    private final MedicalRecordFileRepository medicalRecordFileRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository, MedicalRecordFileRepository medicalRecordFileRepository){
        this.medicalRecordRepository = medicalRecordRepository;
        this.medicalRecordFileRepository = medicalRecordFileRepository;
    }

    @Transactional
    @Override
    public void createMedicalRecord(MedicalRecordCreateDTO createDTO) {
        //Create new medical_record
        MedicalRecord medicalRecord = new MedicalRecord();
        BeanUtils.copyProperties(createDTO, medicalRecord);
        medicalRecord.setCreatedBy(AuthUtils.getCurrentUserId());
        medicalRecord.setCreatedAt(new Date());
        medicalRecordRepository.save(medicalRecord);

        //save file attach with medical_record
        List<MedicalRecordFile> lstFileAttach = createDTO.getLstFileAttachId().stream()
            .map(item -> new MedicalRecordFile(createDTO.getPatientId(), item)).collect(Collectors.toList());
        medicalRecordFileRepository.saveAll(lstFileAttach);
    }

    @Override
    public List<IGetListMedicalRecord> getListMedicalRecords(Long patientId, String patientName, String patientCode, String phoneNumber,
        IdentityTypeEnum idType, String idNumber, String email, Date startDate, Date endDate) {
        return medicalRecordRepository.getListMedicalRecords(patientId, patientName, patientCode, phoneNumber, idType.getValue(), idNumber,
            email, startDate, endDate);
    }
}
