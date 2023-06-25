package com.app20222.app20222_backend.services.users;

import com.app20222.app20222_backend.dtos.users.IGetListUser;
import com.app20222.app20222_backend.dtos.users.UserCreateDTO;
import com.app20222.app20222_backend.dtos.users.UserDetailDTO;
import com.app20222.app20222_backend.dtos.users.UserUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface UserService {

    /**
     * Lấy danh sách người dùng trong hệ thống
     */
    List<IGetListUser> getListUser(String code, String name, String email, String phone, Long departmentId, Long roleId);

    /**
     * Tạo mới người dùng
     */
    void createUser(UserCreateDTO createDTO);

    /**
     * Cập nhật thông tin người dùng
     */
    void updateUser(Long id, UserUpdateDTO updateDTO);

    /**
     * Lấy thông tin chi tiết của người dùng
     */
    UserDetailDTO getDetailUser(Long userId);

    /**
     * Lấy danh sách các chức năng theo role
     */
    Set<String> getLstUserFeaturesByRoles(Set<String> roles);
}
