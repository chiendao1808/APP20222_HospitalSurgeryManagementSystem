package com.app20222.app20222_backend.services.patient;

import java.util.List;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.dtos.patient.IGetDetailPatient;
import com.app20222.app20222_backend.dtos.patient.IGetListPatient;
import com.app20222.app20222_backend.dtos.patient.PatientCreateDTO;
import com.app20222.app20222_backend.dtos.patient.PatientUpdateDTO;
import com.app20222.app20222_backend.enums.users.IdentityTypeEnum;

@Service
public interface PatientService {


    void createPatient(PatientCreateDTO createDTO);

    void updatePatient(PatientUpdateDTO updateDTO);

    List<IGetListPatient> getListPatient(Long patientId, String code, IdentityTypeEnum identityType, String identificationNum, String name,
        String phoneNumber, String email);

    IGetDetailPatient getDetailPatient(Long patientId);
}
