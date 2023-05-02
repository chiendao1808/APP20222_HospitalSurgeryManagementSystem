package com.app20222.app20222_backend.repositories.users;

import com.app20222.app20222_backend.constants.sql.users.SQLUser;
import com.app20222.app20222_backend.dtos.responses.users.IGetListUser;
import com.app20222.app20222_backend.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = SQLUser.GET_LIST_USER)
    List<IGetListUser> getListUser();
}
