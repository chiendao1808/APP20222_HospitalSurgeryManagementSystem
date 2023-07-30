package com.app20222.app20222_backend.repositories.patient;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.constants.sql.patient.SQLPatient;
import com.app20222.app20222_backend.dtos.patient.IGetDetailPatient;
import com.app20222.app20222_backend.dtos.patient.IGetListPatient;
import com.app20222.app20222_backend.entities.patient.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {


    Boolean existsByCode(String code);

    Boolean existsByIdentityTypeAndIdentificationNumber(Integer identityType, String identificationNumber);

    Boolean existsByHealthInsuranceNumber(String healthInsuranceNumber);

    Boolean existsByIdNotAndIdentityTypeAndIdentificationNumber(Long id, Integer identityType, String identificationNumber);


    @Query(nativeQuery = true, value = SQLPatient.GET_LIST_PATIENT)
    List<IGetListPatient> getListPatient(Long patientId, String code, Integer identityType, String identificationNum, String name,
        String phoneNumber, String email);

    @Query(nativeQuery = true, value = SQLPatient.GET_DETAIL_PATIENT)
    IGetDetailPatient getDetailPatient(Long id);

}
