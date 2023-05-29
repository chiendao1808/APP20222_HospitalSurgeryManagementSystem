package com.app20222.app20222_backend.repositories.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.entities.role.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
