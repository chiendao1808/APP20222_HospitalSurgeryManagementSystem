package com.app20222.app20222_backend.repositories.file_attach;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.entities.file_attach.FileAttach;

@Repository
public interface FileAttachRepository extends JpaRepository<FileAttach, Long> {


    Set<FileAttach> findAllByIdIn(Set<Long> lstFileAttachId);

}
