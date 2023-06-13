package com.app20222.app20222_backend.services.users.impl;

import com.app20222.app20222_backend.constants.message.error_field.ErrorKey;
import com.app20222.app20222_backend.constants.message.message_const.MessageConst;
import com.app20222.app20222_backend.constants.message.message_const.MessageConst.Resources;
import com.app20222.app20222_backend.dtos.users.IGetDetailUser;
import com.app20222.app20222_backend.dtos.users.IGetListUser;
import com.app20222.app20222_backend.dtos.users.UserCreateDTO;
import com.app20222.app20222_backend.dtos.users.UserDetailDTO;
import com.app20222.app20222_backend.dtos.users.UserUpdateDTO;
import com.app20222.app20222_backend.entities.users.User;
import com.app20222.app20222_backend.entities.users.UserRole;
import com.app20222.app20222_backend.enums.permission.BasePermissionEnum;
import com.app20222.app20222_backend.exceptions.exception_factory.ExceptionFactory;
import com.app20222.app20222_backend.repositories.users.UserRepository;
import com.app20222.app20222_backend.repositories.users.UserRoleRepository;
import com.app20222.app20222_backend.services.permission.PermissionService;
import com.app20222.app20222_backend.services.users.UserService;
import com.app20222.app20222_backend.utils.StringUtils;
import com.app20222.app20222_backend.utils.auth.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final PermissionService permissionService;

    private final PasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;

    private final ExceptionFactory exceptionFactory;

    public UserServiceImpl(UserRepository userRepository, PermissionService permissionService, PasswordEncoder passwordEncoder,
        UserRoleRepository userRoleRepository, ExceptionFactory exceptionFactory){
        this.userRepository = userRepository;
        this.permissionService = permissionService;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.exceptionFactory = exceptionFactory;
    }

    @Override
    public List<IGetListUser> getListUser(String code, String name, String email, String phone, Long departmentId, Long roleId) {
        // Lấy danh sách viewable department id
        Set<Long> lstViewableDepartmentId = permissionService.getLstViewableDepartmentId();
        lstViewableDepartmentId.add(departmentId);
        return userRepository.getListUser(code, name, email, phone, lstViewableDepartmentId);
    }

    @Override
    public void createUser(UserCreateDTO createDTO) {
        User user = new User();
        BeanUtils.copyProperties(createDTO, user);
        user.setCode(generateUserCode());
        user.setIdentityType(createDTO.getIdentityType().getValue());
        user.setCreatedBy(AuthUtils.getCurrentUserId());
        user.setCreatedAt(new Date());
        generateUsernamePassword(user);
        Long userId = userRepository.save(user).getId();

        // Set user_role
        List<UserRole> lstUserRole = createDTO.getLstRoleId().stream().map(roleId -> new UserRole(userId, roleId)).collect(Collectors.toList());
        userRoleRepository.saveAll(lstUserRole);
    }

    @Transactional
    @Override
    public void updateUser(Long id, UserUpdateDTO updateDTO) {
        // check edit permission
        User user = permissionService.hasUserPermission(id, BasePermissionEnum.EDIT);
        if (userRepository.existsByIdNotAndIdentityTypeAndIdentificationNumber(id, updateDTO.getIdentityType().getValue(),
            updateDTO.getIdentificationNumber())) {
            throw exceptionFactory.resourceExistedException(ErrorKey.User.EXISTED_ERROR_CODE, Resources.USER, MessageConst.RESOURCE_EXISTED,
                ErrorKey.User.IDENTIFICATION_NUMBER, updateDTO.getIdentificationNumber());
        }
        BeanUtils.copyProperties(updateDTO, user);
        user.setModifiedBy(AuthUtils.getCurrentUserId());
        user.setModifiedAt(new Date());
        userRepository.save(user);

        // Cập nhật user role;
        userRoleRepository.deleteAllByUserId(id);
        List<UserRole> lstUserRole = updateDTO.getLstRoleId().stream().map(roleId -> new UserRole(id, roleId)).collect(Collectors.toList());
        userRoleRepository.saveAll(lstUserRole);
    }

    @Override
    public UserDetailDTO getDetailUser(Long userId) {
        permissionService.hasUserPermission(userId, BasePermissionEnum.VIEW);
        IGetDetailUser iGetDetailUser = userRepository.getDetailUser(userId);
        return new UserDetailDTO(iGetDetailUser);
    }

    /**
     * Genarate  random user's code
     */
    private String generateUserCode(){
        String baseCode = "CBNV";
        Random random = new Random();
        String generatedCode = baseCode + random.nextInt(900000) + 100000;
        while (userRepository.existsByCode(generatedCode)){
            generatedCode = baseCode + random.nextInt(900000) + 100000;
        }
        return generatedCode;
    }

    /**
     * Generate new username and password for user
     */
    private void generateUsernamePassword(User user) {
        StringBuilder usernameBuilder = new StringBuilder();
        String fullName = user.getFullName();
        String[] fullNameArr = fullName.split(" ");
        if(fullNameArr.length > 0){
            usernameBuilder.append(StringUtils.convertVietnameseToEng(fullNameArr[0]).toLowerCase());
            for(int index = 1; index < fullNameArr.length; index++){
                usernameBuilder.append(StringUtils.convertVietnameseToEng(fullNameArr[index]).toLowerCase().charAt(0));
            }
        }
        //Set username and password to user
        Integer userLikeExisted = userRepository.countByUsername(usernameBuilder.toString());
        usernameBuilder.append(userLikeExisted == 0 ? "" : String.valueOf(userLikeExisted + 1));
        user.setUsername(usernameBuilder.toString());

        String password = user.getUsername() + "@" + new Random().nextInt(1000);
        user.setPassword(passwordEncoder.encode(password));
    }
}
