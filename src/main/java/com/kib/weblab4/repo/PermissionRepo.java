package com.kib.weblab4.repo;

import com.kib.weblab4.entities.ApplicationUser;
import com.kib.weblab4.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Integer> {

//    Permission findPermissionBy

}
