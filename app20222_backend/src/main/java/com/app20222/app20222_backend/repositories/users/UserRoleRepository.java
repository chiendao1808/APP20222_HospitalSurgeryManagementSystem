package com.app20222.app20222_backend.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.app20222.app20222_backend.entities.users.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Transactional
    @Modifying
    void deleteAllByUserId(Long userId);

}
