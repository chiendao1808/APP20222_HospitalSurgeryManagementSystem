package com.app20222.app20222_backend.services.patient.impl;

import static com.app20222.app20222_backend.constants.message.errorKey.ErrorKey.Patient.DUPLICATED_ERROR_CODE;
import static com.app20222.app20222_backend.constants.message.errorKey.ErrorKey.Patient.EXISTED_ERROR_CODE;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.constants.message.errorKey.ErrorKey;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst.Resources;
import com.app20222.app20222_backend.dtos.patient.IGetDetailPatient;
import com.app20222.app20222_backend.dtos.patient.IGetListPatient;
import com.app20222.app20222_backend.dtos.patient.PatientCreateDTO;
import com.app20222.app20222_backend.dtos.patient.PatientUpdateDTO;
import com.app20222.app20222_backend.entities.patient.Patient;
import com.app20222.app20222_backend.enums.permission.BasePermissionEnum;
import com.app20222.app20222_backend.enums.users.IdentityTypeEnum;
import com.app20222.app20222_backend.exceptions.exceptionFactory.ExceptionFactory;
import com.app20222.app20222_backend.repositories.patient.PatientRepository;
import com.app20222.app20222_backend.services.patient.PatientService;
import com.app20222.app20222_backend.services.permission.PermissionService;
import com.app20222.app20222_backend.utils.auth.AuthUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final ExceptionFactory exceptionFactory;

    private final PermissionService permissionService;


    public PatientServiceImpl(PatientRepository patientRepository, ExceptionFactory exceptionFactory, PermissionService permissionService) {
        this.patientRepository = patientRepository;
        this.exceptionFactory = exceptionFactory;
        this.permissionService = permissionService;
    }


    @Override
    public void createPatient(PatientCreateDTO createDTO) {

        if (Objects.nonNull(createDTO.getIdentityType()) && Objects.nonNull(createDTO.getIdentificationNumber())
            && patientRepository.existsByIdentityTypeAndIdentificationNumber(createDTO.getIdentityType().getValue(),
            createDTO.getIdentificationNumber())) {
            throw exceptionFactory.resourceExistedException(EXISTED_ERROR_CODE, Resources.PATIENT, MessageConst.RESOURCE_EXISTED,
                ErrorKey.Patient.IDENTIFICATION_NUMBER, createDTO.getIdentificationNumber());
        }

        // Create new patient
        Patient newPatient = new com.app20222.app20222_backend.entities.patient.Patient();
        BeanUtils.copyProperties(createDTO, newPatient);
        newPatient.setIdentityType(createDTO.getIdentityType().getValue());
        newPatient.setCode(generatePatientCode());
        newPatient.setCreatedBy(AuthUtils.getCurrentUserId());
        newPatient.setCreatedAt(new Date());
        patientRepository.save(newPatient);
    }

    @Override
    public List<IGetListPatient> getListPatient(Long patientId, String code, IdentityTypeEnum identityType, String identificationNum,
        String name, String phoneNumber, String email) {
        return patientRepository.getListPatient(patientId, code, identityType.getValue(), identificationNum, name, phoneNumber, email);
    }

    @Override
    public IGetDetailPatient getDetailPatient(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw exceptionFactory.resourceNotFoundException(ErrorKey.Patient.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND,
                Resources.PATIENT, ErrorKey.Patient.ID, String.valueOf(patientId));
        }
        return patientRepository.getDetailPatient(patientId);
    }

    @Override
    public void updatePatient(Long id, PatientUpdateDTO updateDTO) {
        Patient patient = permissionService.hasPatientPermission(id, BasePermissionEnum.EDIT);

        // check duplicated identification number and identity type
        if (patientRepository.existsByIdNotAndIdentityTypeAndIdentificationNumber(id, updateDTO.getIdentityType().getValue(),
            updateDTO.getIdentificationNumber())) {
            throw exceptionFactory.resourceExistedException(DUPLICATED_ERROR_CODE, Resources.PATIENT, MessageConst.RESOURCE_DUPLICATED,
                ErrorKey.Patient.IDENTIFICATION_NUMBER, updateDTO.getIdentificationNumber());
        }

        //update patient info
        BeanUtils.copyProperties(updateDTO, patient);
        patient.setModifiedBy(AuthUtils.getCurrentUserId());
        patient.setModifiedAt(new Date());
        patientRepository.save(patient);
    }

    private String generatePatientCode(){
        String baseCode = "BN";
        Random random = new Random();
        String generatedCode = baseCode + random.nextInt(900000) + 100000;
        while (patientRepository.existsByCode(generatedCode)){
            generatedCode = baseCode + random.nextInt(900000) + 100000;
        }
        return generatedCode;
    }
}
