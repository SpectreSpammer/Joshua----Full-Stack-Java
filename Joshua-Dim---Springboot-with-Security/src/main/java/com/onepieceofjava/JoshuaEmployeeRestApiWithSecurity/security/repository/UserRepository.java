package com.onepieceofjava.JoshuaEmployeeRestApiWithSecurity.security.repository;

import com.onepieceofjava.JoshuaEmployeeRestApiWithSecurity.security.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<Users,Long> {
    Optional<Users> findByUserName(String username);
}
