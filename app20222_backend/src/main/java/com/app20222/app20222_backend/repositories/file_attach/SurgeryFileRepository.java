package com.app20222.app20222_backend.repositories.file_attach;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.entities.file_attach.SurgeryFile;

@Repository
public interface SurgeryFileRepository extends JpaRepository<SurgeryFile, Long> {

}
