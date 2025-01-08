package com.onepieceofjava.JoshuaEmployeeRestApiWithSecurity.security.repository;

import com.onepieceofjava.JoshuaEmployeeRestApiWithSecurity.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String username);
}
