package com.app20222.app20222_backend.security.service;

import com.app20222.app20222_backend.entities.users.User;
import com.app20222.app20222_backend.repositories.users.UserRepository;
import com.app20222.app20222_backend.security.models.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        try{
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
            return new CustomUserDetails(user);
        } catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Load user by user name error");
        }
    }
}
