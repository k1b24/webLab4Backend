package com.kib.weblab4.repo;

import com.kib.weblab4.entities.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<ApplicationUser, Integer> {

    boolean existsByUsername(String username);

}
