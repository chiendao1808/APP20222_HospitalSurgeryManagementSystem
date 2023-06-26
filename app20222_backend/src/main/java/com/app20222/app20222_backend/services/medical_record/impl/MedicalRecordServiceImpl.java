package com.app20222.app20222_backend.services.medical_record.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app20222.app20222_backend.constants.message.error_field.ErrorKey;
import com.app20222.app20222_backend.constants.message.message_const.MessageConst;
import com.app20222.app20222_backend.constants.message.message_const.MessageConst.Resources;
import com.app20222.app20222_backend.dtos.file_attach.IGetFileAttach;
import com.app20222.app20222_backend.dtos.medical_record.IGetDetailMedicalRecord;
import com.app20222.app20222_backend.dtos.medical_record.IGetListMedicalRecord;
import com.app20222.app20222_backend.dtos.medical_record.MedicalRecordCreateDTO;
import com.app20222.app20222_backend.dtos.medical_record.MedicalRecordDetailDTO;
import com.app20222.app20222_backend.dtos.medical_record.MedicalRecordUpdateDTO;
import com.app20222.app20222_backend.entities.file_attach.MedicalRecordFile;
import com.app20222.app20222_backend.entities.medical_record.MedicalRecord;
import com.app20222.app20222_backend.enums.users.IdentityTypeEnum;
import com.app20222.app20222_backend.exceptions.exception_factory.ExceptionFactory;
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

    private final ExceptionFactory exceptionFactory;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository, MedicalRecordFileRepository medicalRecordFileRepository,
        ExceptionFactory exceptionFactory){
        this.medicalRecordRepository = medicalRecordRepository;
        this.medicalRecordFileRepository = medicalRecordFileRepository;
        this.exceptionFactory = exceptionFactory;
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

    @Override
    public MedicalRecordDetailDTO getMedicalRecordDetail(Long medicalRecordId) {
        if (!medicalRecordRepository.existsById(medicalRecordId)) {
            throw exceptionFactory.resourceNotFoundException(ErrorKey.MedicalRecord.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND,
                Resources.MEDICAL_RECORD, ErrorKey.MedicalRecord.ID, String.valueOf(medicalRecordId));
        }
        MedicalRecordDetailDTO response = new MedicalRecordDetailDTO();
        // Get medical record's info
        IGetDetailMedicalRecord detailMedicalRecord = medicalRecordRepository.getDetailMedicalRecord(medicalRecordId);
        // Get list of attached files
        List<IGetFileAttach> lstFilesAttach = medicalRecordRepository.getListFileAttach(medicalRecordId);
        response.setDetailMedicalRecord(detailMedicalRecord);
        response.setLstMedicalRecordFile(lstFilesAttach);
        return response;
    }

    @Transactional
    @Override
    public void updateMedicalRecord(Long medRecordId, MedicalRecordUpdateDTO updateDTO) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(medRecordId).orElseThrow(
            () -> exceptionFactory.resourceNotFoundException(ErrorKey.MedicalRecord.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND,
                Resources.MEDICAL_RECORD, ErrorKey.MedicalRecord.ID, String.valueOf(medRecordId)));
        // update medical_record info
        medicalRecord.setSummary(updateDTO.getSummary());
        medicalRecordRepository.save(medicalRecord);
        // update file_attach info
        // xóa các bản ghi hiện tại
        medicalRecordFileRepository.deleteAllByMedicalRecordId(medRecordId);
        Set<Long> lstNewFileId = updateDTO.getLstFileAttachId();
        List<MedicalRecordFile> lstMedicalRecordFile = new ArrayList<>();
        lstNewFileId.forEach(item -> lstMedicalRecordFile.add(new MedicalRecordFile(medRecordId, item)));
        medicalRecordFileRepository.saveAll(lstMedicalRecordFile);
    }
}
