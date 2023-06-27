package com.app20222.app20222_backend.controllers.users;

import com.app20222.app20222_backend.dtos.users.IGetListUser;
import com.app20222.app20222_backend.dtos.users.UserCreateDTO;
import com.app20222.app20222_backend.dtos.users.UserDetailDTO;
import com.app20222.app20222_backend.dtos.users.UserUpdateDTO;
import com.app20222.app20222_backend.enums.users.UserStatusEnum;
import com.app20222.app20222_backend.services.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "User Controllers")
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Lấy danh sách users trong hệ thống")
    public List<IGetListUser> getListUsers(@RequestParam(name = "code", required = false, defaultValue = "") String code,
        @RequestParam(name = "name", required = false, defaultValue = "") String name,
        @RequestParam(name = "email", required = false, defaultValue = "") String email,
        @RequestParam(name = "phone", required = false, defaultValue = "") String phone,
        @RequestParam(name = "departmentId", required = false, defaultValue = "-1") Long departmentId,
        @RequestParam(name = "roleId", required = false, defaultValue = "-1") Long roleId) {
        return userService.getListUser(code, name, email, phone, departmentId, roleId);
    }

    @PostMapping
    @Operation(description = "Tạo mới người dùng")
    public void createUser(@RequestBody @Validated UserCreateDTO createDTO) {
        userService.createUser(createDTO);
    }

    @PutMapping
    @Operation(description = "Cập nhật thông tin người dùng")
    public void updateUser(@RequestParam(name = "id") Long id,
        @RequestBody @Validated UserUpdateDTO updateDTO) {
        userService.updateUser(id, updateDTO);
    }

    @GetMapping("/get-detail")
    @Operation(description = "Lấy thông tin chi tiết người dùng")
    public UserDetailDTO getDetailUser(@RequestParam(name = "id") Long id) {
        return userService.getDetailUser(id);
    }

    @PutMapping("/switch-status")
    @Operation(description = "Chuyển trạng thái hoạt động của người dùng")
    public void switchUserStatus(@RequestParam(name = "id") Long userId, @RequestParam(name = "status") UserStatusEnum status) {
        log.info("========= started switchUserStatus ==========");
        userService.switchUserStatus(userId, status);
        log.info("========= end switchUserStatus ==========");
    }

}
