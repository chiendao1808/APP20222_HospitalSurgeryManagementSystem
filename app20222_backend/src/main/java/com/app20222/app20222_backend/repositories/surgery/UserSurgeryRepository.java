package com.app20222.app20222_backend.repositories.surgery;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.app20222.app20222_backend.entities.surgery.UserSurgery;

@Repository
public interface UserSurgeryRepository extends JpaRepository<UserSurgery, Long> {


    @Transactional
    @Modifying
    Integer deleteAllBySurgeryId(Long surgeryId);

    @Query("SELECT userId FROM UserSurgery WHERE surgeryId = :surgeryId")
    Set<Long> findUserIdBySurgeryId(Long surgeryId);

    List<UserSurgery> findAllBySurgeryId(Long surgeryId);

}
