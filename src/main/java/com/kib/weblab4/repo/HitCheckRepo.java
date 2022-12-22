package com.kib.weblab4.repo;

import com.kib.weblab4.entities.HitCheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HitCheckRepo extends JpaRepository<HitCheckEntity, Integer> {

    void removeAllByUserId(Integer userId);

    List<HitCheckEntity> findAllByUserId(Integer userId);
}
