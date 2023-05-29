package com.app20222.app20222_backend.utils.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.app20222.app20222_backend.entities.role.Role;
import com.app20222.app20222_backend.entities.users.User;
import com.app20222.app20222_backend.enums.role.RoleEnum;
import com.app20222.app20222_backend.security.models.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class AuthUtils {

    /**
     * Get current user id
     * @return : current user id
     */
    public static Long getCurrentUserId(){
        try{
            CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getUserId();
        }catch (Exception ex)
        {
            log.error("Get current user id error");
            return null;
        }
    }

    /**
     * Get current user
     * @return : current user
     */
    public static User getCurrentUser(){
        try{
            CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getUser();
        }catch (Exception ex)
        {
            log.error("Get current user id error");
            return null;
        }
    }

    /**
     * Get lst role code of the current user
     */
    public static List<String> getCurrentUserRoles(){
        try{
            CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getRoles().stream().map(Role::getCode).collect(Collectors.toList());
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("Get current user's roles error");
            return null;
        }
    }

    /**
     * Get current user's department id
     */
    public static Long getCurrentUserDepartmentId(){
        try{
            CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getUser().getDepartmentId();
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("Get current user's department error");
            return null;
        }
    }


    /**
     * Check if the current user is super admin
     */
    public static Boolean isSuperAdmin(){
        try{
            CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getRoles().stream().map(Role::getCode).collect(Collectors.toSet()).contains(RoleEnum.SUPER_ADMIN.getRoleCode());
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("Check super admin error");
            return false;
        }
    }

}
