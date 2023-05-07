package com.app20222.app20222_backend.utils.auth;

import com.app20222.app20222_backend.entities.users.User;
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
}
