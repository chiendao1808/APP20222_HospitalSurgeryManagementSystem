package com.app20222.app20222_backend.repositories.surgery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.entities.surgery.UserSurgery;

@Repository
public interface UserSurgeryRepository extends JpaRepository<UserSurgery, Long> {

}
