package com.app20222.app20222_backend.utils.auth;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import com.app20222.app20222_backend.entities.role.Role;
import com.app20222.app20222_backend.entities.users.User;
import com.app20222.app20222_backend.enums.role.RoleEnum;
import com.app20222.app20222_backend.enums.role.RoleLevelEnum;
import com.app20222.app20222_backend.security.models.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Slf4j
public class AuthUtils {


    private static final String[] HEADERS_TO_TRY = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"
    };

    /**
     * Get current user id
     *
     * @return : current user id
     */
    public static Long getCurrentUserId() {
        try {
            User currentUser = getCurrentUser();
            return Objects.nonNull(currentUser) ? currentUser.getId() : null;
        } catch (Exception ex) {
            log.error("Get current user id error");
            return null;
        }
    }

    /**
     * Get current user
     *
     * @return : current user
     */
    public static User getCurrentUser() {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getUser();
        } catch (Exception ex) {
            log.error("Get current user id error");
            return null;
        }
    }

    /**
     * Get lst role code of the current user
     */
    public static List<String> getCurrentUserRoles() {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getRoles().stream().map(Role::getCode).collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Get current user's roles error");
            return null;
        }
    }

    /**
     * Get current user's department id
     */
    public static Long getCurrentUserDepartmentId() {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getUser().getDepartmentId();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Get current user's department error");
            return null;
        }
    }


    /**
     * Check if the current user is super admin
     */
    public static Boolean isSuperAdmin() {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getRoles().stream().map(Role::getCode).collect(Collectors.toSet()).contains(RoleEnum.SUPER_ADMIN.getRoleCode());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Check super admin error");
            return false;
        }
    }

    /**
     * Check phân quyền theo các level của quyền
     */
    public static Boolean isRoleLevel(RoleLevelEnum roleLevel) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Set<String> roles = userDetails.getRoles().stream().map(Role::getCode).collect(Collectors.toSet());
            boolean isLevel = false;
            switch (roleLevel) {
                case SYSTEM:
                    isLevel = roles.contains(RoleEnum.SUPER_ADMIN.getRoleCode());
                    break;
                case HOSPITAL_MANAGER:
                    isLevel = CollectionUtils.containsAny(roles,
                        Arrays.asList(RoleEnum.HOSPITAL_ADMIN.getRoleCode(), RoleEnum.HOSPITAL_MANAGER.getRoleCode()));
                    break;
                case DEPARTMENT_MANAGER:
                    isLevel = CollectionUtils.containsAny(roles,
                        Arrays.asList(RoleEnum.DEPARTMENT_ADMIN.getRoleCode(), RoleEnum.DEPARTMENT_MANAGER.getRoleCode()));
                    break;
                case PERSONEL:
                    isLevel = CollectionUtils.containsAny(roles,
                        Arrays.asList(RoleEnum.DOCTOR.getRoleCode(), RoleEnum.NURSE.getRoleCode(), RoleEnum.STAFF.getRoleCode()));
                    break;
                default:
                    break;
            }
            return isLevel;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("get role level hospital error");
            return false;
        }
    }

    /**
     * Get client's request ip address
     */
    public static String getIpAddress(HttpServletRequest request){
        for(String header : HEADERS_TO_TRY){
            String ipAddress = request.getHeader(header);
            if(Objects.nonNull(ipAddress) && StringUtils.hasText(ipAddress) && !"unknown".equalsIgnoreCase(ipAddress)){
                return ipAddress;
            }
        }
        return request.getRemoteAddr();
    }

}
