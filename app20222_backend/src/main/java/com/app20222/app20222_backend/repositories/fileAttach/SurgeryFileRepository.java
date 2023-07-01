package com.app20222.app20222_backend.repositories.fileAttach;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.constants.sql.surgery.SQLSurgery;
import com.app20222.app20222_backend.dtos.fileAttach.IGetFileAttach;
import com.app20222.app20222_backend.entities.fileAttach.SurgeryFile;

@Repository
public interface SurgeryFileRepository extends JpaRepository<SurgeryFile, Long> {

    @Query(nativeQuery = true, value = SQLSurgery.GET_LIST_SURGERY_FILE)
    List<IGetFileAttach> getListFileAttach(Long surgeryId);

}
