package com.app20222.app20222_backend.utils.permissionUtils;

import java.util.List;
import java.util.Objects;
import com.app20222.app20222_backend.enums.role.RoleEnum;
import com.app20222.app20222_backend.utils.auth.AuthUtils;

public class PermissionUtils {

    public static final List<String> DEPARTMENT_ROLES = List.of(RoleEnum.NURSE.getRoleCode(), RoleEnum.DOCTOR.getRoleCode(),
        RoleEnum.DEPARTMENT_ADMIN.getRoleCode(),
        RoleEnum.DEPARTMENT_MANAGER.getRoleCode());

    public static final List<String> HOSPITAL_ROLES = List.of(RoleEnum.HOSPITAL_ADMIN.getRoleCode(), RoleEnum.HOSPITAL_MANAGER.getRoleCode());

    public static final List<String> ADMIN_ROLES = List.of(RoleEnum.SUPER_ADMIN.getRoleCode(), RoleEnum.DEPARTMENT_ADMIN.getRoleCode(),
        RoleEnum.HOSPITAL_ADMIN.getRoleCode());


    /**
     * ================== ROLE =================================================================
     */

    public static Boolean checkDepartmentRoles(){
        List<String> currentRoles = AuthUtils.getCurrentUserRoles();
        if(Objects.isNull(currentRoles)) return false;
        return currentRoles.stream().anyMatch(DEPARTMENT_ROLES::contains);
    }

    public static Boolean checkHospitalRoles(){
        List<String> currentRoles = AuthUtils.getCurrentUserRoles();
        if(Objects.isNull(currentRoles)) return false;
        return currentRoles.stream().anyMatch(HOSPITAL_ROLES::contains);
    }

    public static Boolean checkAdminRoles(){
        List<String> currentRoles = AuthUtils.getCurrentUserRoles();
        if(Objects.isNull(currentRoles)) return false;
        return currentRoles.stream().anyMatch(ADMIN_ROLES::contains);
    }

}
