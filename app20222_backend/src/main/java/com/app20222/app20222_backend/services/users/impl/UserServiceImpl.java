package com.app20222.app20222_backend.services.users.impl;

import com.app20222.app20222_backend.constants.message.errorKey.ErrorKey;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst.Resources;
import com.app20222.app20222_backend.dtos.users.IGetDetailUser;
import com.app20222.app20222_backend.dtos.users.IGetListUser;
import com.app20222.app20222_backend.dtos.users.ProfileUserDTO;
import com.app20222.app20222_backend.dtos.users.RoleDTO;
import com.app20222.app20222_backend.dtos.users.UserCreateDTO;
import com.app20222.app20222_backend.dtos.users.UserDetailDTO;
import com.app20222.app20222_backend.dtos.users.UserUpdateDTO;
import com.app20222.app20222_backend.entities.users.User;
import com.app20222.app20222_backend.entities.users.UserRole;
import com.app20222.app20222_backend.enums.permission.BasePermissionEnum;
import com.app20222.app20222_backend.enums.users.UserStatusEnum;
import com.app20222.app20222_backend.exceptions.exceptionFactory.ExceptionFactory;
import com.app20222.app20222_backend.repositories.department.DepartmentRepository;
import com.app20222.app20222_backend.repositories.feature.FeatureRepository;
import com.app20222.app20222_backend.repositories.role.RoleRepository;
import com.app20222.app20222_backend.repositories.users.UserRepository;
import com.app20222.app20222_backend.repositories.users.UserRoleRepository;
import com.app20222.app20222_backend.services.permission.PermissionService;
import com.app20222.app20222_backend.services.users.UserService;
import com.app20222.app20222_backend.utils.DateUtils;
import com.app20222.app20222_backend.utils.StringUtils;
import com.app20222.app20222_backend.utils.auth.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    private final DepartmentRepository departmentRepository;

    private final FeatureRepository featureRepository;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PermissionService permissionService, PasswordEncoder passwordEncoder,
        UserRoleRepository userRoleRepository, ExceptionFactory exceptionFactory, DepartmentRepository departmentRepository,
        FeatureRepository featureRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.permissionService = permissionService;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.exceptionFactory = exceptionFactory;
        this.departmentRepository = departmentRepository;
        this.featureRepository = featureRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<IGetListUser> getListUser(String code, String name, String email, String phone, Long departmentId, Long roleId) {
        // Lấy danh sách viewable department id
        Set<Long> lstViewableDepartmentId = permissionService.getLstViewableDepartmentId();
        lstViewableDepartmentId.add(departmentId);
        List<IGetListUser> lstResult = userRepository.getListUser(AuthUtils.getCurrentUserId(), code, name, email, phone, lstViewableDepartmentId, roleId);
        lstResult.sort(Comparator.comparing(IGetListUser::getLastModifiedAt).reversed());
        return lstResult;
    }

    @Override
    public void createUser(UserCreateDTO createDTO) {
        // Check department exist
        if(!departmentRepository.existsById(createDTO.getDepartmentId()))
            throw exceptionFactory.resourceNotFoundException(ErrorKey.Department.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND,
                Resources.DEPARTMENT, ErrorKey.Department.ID, String.valueOf(createDTO.getDepartmentId()));
        // Create new user
        User user = new User();
        BeanUtils.copyProperties(createDTO, user);
        user.setCode(generateUserCode());
        user.setIdentityType(createDTO.getIdentityType().getValue());
        user.setCreatedBy(AuthUtils.getCurrentUserId());
        user.setCreatedAt(new Date());
        user.setStatus(UserStatusEnum.ACTIVE.getStatus());
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
        List<UserRole> lstCurrentUsersRoles = userRoleRepository.findAllByUserId(id);
        userRoleRepository.deleteAll(lstCurrentUsersRoles);
        List<UserRole> lstNewUserRole = updateDTO.getLstRoleId().stream().map(roleId -> new UserRole(id, roleId)).collect(Collectors.toList());
        userRoleRepository.saveAll(lstNewUserRole);
    }

    @Override
    public UserDetailDTO getDetailUser(Long userId) {
        permissionService.hasUserPermission(userId, BasePermissionEnum.VIEW);
        IGetDetailUser iGetDetailUser = userRepository.getDetailUser(userId);
        UserDetailDTO userDetails = new UserDetailDTO(iGetDetailUser);
        Set<Long> roleIds = userRoleRepository.findAllByUserId(userId).stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        List<RoleDTO> roles = roleRepository.findAllById(roleIds).stream().map(item -> new RoleDTO(item.getId(), item.getCode(), item.getDisplayedName())).collect(
            Collectors.toList());
        userDetails.setRoles(roles);
        return userDetails;
    }

    @Override
    public ProfileUserDTO getUserProfile() {
        return new ProfileUserDTO(userRepository.getDetailUser(AuthUtils.getCurrentUserId()));
    }

    @Override
    public void switchUserStatus(Long userId, UserStatusEnum status) {
        userRepository.switchUserStatus(userId, status.getStatus());
    }

    @Override
    public Set<String> getLstUserFeaturesByRoles(Set<String> roles) {
        if (Objects.isNull(roles) || roles.isEmpty()) {
            return Collections.emptySet();
        }
        String lstRoles = roles.toString().replace("[", "{").replace("]", "}");
        return featureRepository.getLstUserFeaturesByRole(lstRoles);
    }

    /**
     * Genarate  random user's code
     */
    private String generateUserCode(){
        String baseCode = "CBNV";
        Random random = new Random();
        String generatedCode = baseCode + (random.nextInt(900000) + 100000);
        while (userRepository.existsByCode(generatedCode)){
            generatedCode = baseCode + (random.nextInt(900000) + 100000);
        }
        return generatedCode;
    }

    /**
     * Generate new username and password for user
     */
    private void generateUsernamePassword(User user) {
        StringBuilder usernameBuilder = new StringBuilder();
        String fullName = user.getFullName();
        String[] fullNameArr = fullName.trim().split(" ");
        if (fullNameArr.length > 0) {
            usernameBuilder.append(StringUtils.convertVietnameseToEng(fullNameArr[fullNameArr.length - 1]).toLowerCase());
            for (int index = 0; index < fullNameArr.length - 1; index++) {
                usernameBuilder.append(StringUtils.convertVietnameseToEng(fullNameArr[index]).toLowerCase().charAt(0));
            }
        }
        //Set username and password to user
        Integer userLikeExisted = userRepository.countByUsername(usernameBuilder.toString());
        usernameBuilder.append(userLikeExisted == 0 ? "" : String.valueOf(userLikeExisted + 1));
        user.setUsername(usernameBuilder.toString());

        String password = user.getUsername() + "@" + DateUtils.asLocalDate(new Date()).getYear();
        user.setPassword(passwordEncoder.encode(password));
    }
}
