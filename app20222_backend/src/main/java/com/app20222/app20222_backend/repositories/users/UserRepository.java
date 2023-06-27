package com.app20222.app20222_backend.repositories.users;

import com.app20222.app20222_backend.constants.sql.users.SQLUser;
import com.app20222.app20222_backend.dtos.users.IGetDetailUser;
import com.app20222.app20222_backend.dtos.users.IGetListUser;
import com.app20222.app20222_backend.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);

    @Query(nativeQuery = true, value = SQLUser.GET_LIST_USER)
    List<IGetListUser> getListUser(String code, String name, String email, String phone, Set<Long> lstDepartmentId);

    @Query(value = "SELECT user.id FROM User user WHERE user.departmentId = :departmentId")
    Set<Long> findAllByDepartmentId(Long departmentId);

    @Query(nativeQuery = true, value = SQLUser.GET_DETAIL_USER)
    IGetDetailUser getDetailUser(Long userId);

    Boolean existsByUsername(String username);

    Integer countByUsername(String username);

    Boolean existsByCode(String code);

    Boolean existsByIdNotAndIdentityTypeAndIdentificationNumber(Long id, Integer identityType, String identificationNumber);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User user SET user.status = :status WHERE user.id = :userId")
    Integer switchUserStatus(Long userId, Integer status);
}
