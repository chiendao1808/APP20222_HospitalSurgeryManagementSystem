package com.app20222.app20222_backend.repositories.auth;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.entities.auth.AuthInfo;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {

    Optional<AuthInfo> findFirstByUserIdOrderByCreatedAtDesc(Long userId);

}
