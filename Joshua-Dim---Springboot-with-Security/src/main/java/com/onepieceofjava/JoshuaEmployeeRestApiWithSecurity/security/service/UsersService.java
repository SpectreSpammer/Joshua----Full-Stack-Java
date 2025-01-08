package com.onepieceofjava.JoshuaEmployeeRestApiWithSecurity.security.service;

import com.onepieceofjava.JoshuaEmployeeRestApiWithSecurity.security.model.Users;
import com.onepieceofjava.JoshuaEmployeeRestApiWithSecurity.security.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsersService implements UserDetailsService {

    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

        return User.withUsername(users.getUsername())
                .password(users.getPassword())
                .roles(users.getRoles().toArray(new String[0]))
                .build();
    }
}
