package com.app20222.app20222_backend.services.medicalRecord.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app20222.app20222_backend.constants.message.errorKey.ErrorKey;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst.Resources;
import com.app20222.app20222_backend.dtos.fileAttach.IGetFileAttach;
import com.app20222.app20222_backend.dtos.medicalRecord.IGetDetailMedicalRecord;
import com.app20222.app20222_backend.dtos.medicalRecord.IGetListMedicalRecord;
import com.app20222.app20222_backend.dtos.medicalRecord.MedicalRecordCreateDTO;
import com.app20222.app20222_backend.dtos.medicalRecord.MedicalRecordDetailDTO;
import com.app20222.app20222_backend.dtos.medicalRecord.MedicalRecordUpdateDTO;
import com.app20222.app20222_backend.entities.fileAttach.MedicalRecordFile;
import com.app20222.app20222_backend.entities.medicalRecord.MedicalRecord;
import com.app20222.app20222_backend.enums.users.IdentityTypeEnum;
import com.app20222.app20222_backend.exceptions.exceptionFactory.ExceptionFactory;
import com.app20222.app20222_backend.repositories.fileAttach.MedicalRecordFileRepository;
import com.app20222.app20222_backend.repositories.medicalRecord.MedicalRecordRepository;
import com.app20222.app20222_backend.services.medicalRecord.MedicalRecordService;
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
        Long medicalRecordId = medicalRecordRepository.save(medicalRecord).getId();

        //save file attach with medical_record
        List<MedicalRecordFile> lstFileAttach = createDTO.getLstFileAttachId().stream()
            .map(item -> new MedicalRecordFile(item, medicalRecordId)).collect(Collectors.toList());
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
        lstNewFileId.forEach(item -> lstMedicalRecordFile.add(new MedicalRecordFile(item, medRecordId)));
        medicalRecordFileRepository.saveAll(lstMedicalRecordFile);
    }
}
