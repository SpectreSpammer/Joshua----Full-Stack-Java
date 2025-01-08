package com.onepieceofjava.JoshuaEmployeeRestApiWithSecurity.security.init;

import com.onepieceofjava.JoshuaEmployeeRestApiWithSecurity.security.model.User;
import com.onepieceofjava.JoshuaEmployeeRestApiWithSecurity.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {

        if(userRepository.findByUserName("admin").isEmpty()){
           User admin = new User();
           admin.setUsername("admin");
           admin.setPassword(passwordEncoder.encode("admin123"));
           admin.setRoles(Set.of("ADMIN"));
           userRepository.save(admin);
           System.out.println("Admin user is created.!");

        }

        if(userRepository.findByUserName("user").isEmpty()){
            User admin = new User();
            admin.setUsername("user");
            admin.setPassword(passwordEncoder.encode("user123"));
            admin.setRoles(Set.of("USER"));
            userRepository.save(admin);
            System.out.println("Regular user is created.!");

        }
    }
}
