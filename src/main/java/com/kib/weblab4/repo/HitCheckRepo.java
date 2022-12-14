package com.kib.weblab4.repo;

import com.kib.weblab4.entities.HitCheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HitCheckRepo extends JpaRepository<HitCheckEntity, Integer> {
}
