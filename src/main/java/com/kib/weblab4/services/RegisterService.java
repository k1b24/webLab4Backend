package com.kib.weblab4.services;

import com.kib.weblab4.communication.UserDetails;
import com.kib.weblab4.entities.ApplicationUser;
import com.kib.weblab4.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerNewUser(UserDetails userDetails) {
        if (userRepo.existsByUsername(userDetails.getUsername())) return false;
        ApplicationUser newUser = new ApplicationUser();
        newUser.setUsername(userDetails.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        newUser.setEnabled(true);
        newUser.addAuthority("user");
        userRepo.save(newUser);
        return true;
    }
}
