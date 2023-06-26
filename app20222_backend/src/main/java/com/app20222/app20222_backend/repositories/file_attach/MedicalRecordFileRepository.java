package com.app20222.app20222_backend.repositories.file_attach;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.app20222.app20222_backend.entities.file_attach.MedicalRecordFile;

@Repository
public interface MedicalRecordFileRepository extends JpaRepository<MedicalRecordFile, Long> {

    @Transactional
    @Modifying
    void deleteAllByMedicalRecordId(Long medicalRecordId);

}
