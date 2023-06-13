package com.app20222.app20222_backend.repositories.medical_record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.entities.medical_record.MedicalRecord;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

}
