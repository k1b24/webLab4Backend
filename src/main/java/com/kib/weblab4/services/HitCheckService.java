package com.kib.weblab4.services;

import com.kib.weblab4.communication.HitCheckRequest;
import com.kib.weblab4.communication.HitCheckResponse;
import com.kib.weblab4.converters.HitCheckEntityToHitCheckResponseConverter;
import com.kib.weblab4.entities.ApplicationUser;
import com.kib.weblab4.entities.HitCheckEntity;
import com.kib.weblab4.math.ResultCalculator;
import com.kib.weblab4.repo.HitCheckRepo;
import com.kib.weblab4.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class HitCheckService {
    private final ResultCalculator resultCalculator;
    private final HitCheckRepo hitCheckRepo;
    private final HitCheckEntityToHitCheckResponseConverter hitCheckEntityToHitCheckResponseConverter;
    private final UserRepo userRepo;

    @Autowired
    public HitCheckService(ResultCalculator resultCalculator, HitCheckRepo hitCheckRepo, HitCheckEntityToHitCheckResponseConverter hitCheckEntityToHitCheckResponseConverter, UserRepo userRepo) {
        this.resultCalculator = resultCalculator;
        this.hitCheckRepo = hitCheckRepo;
        this.hitCheckEntityToHitCheckResponseConverter = hitCheckEntityToHitCheckResponseConverter;
        this.userRepo = userRepo;
    }

    public HitCheckResponse processHitCheckRequest(HitCheckRequest hitCheckRequest, Authentication authentication) {
        HitCheckEntity hitCheckEntity = proceedHitCheckRequest(hitCheckRequest);
        ApplicationUser user = userRepo.findByUsername(authentication.getName());
        hitCheckEntity.setUserId(user.getId());
        hitCheckRepo.save(hitCheckEntity);
        return hitCheckEntityToHitCheckResponseConverter.convert(hitCheckEntity);
    }

    private HitCheckEntity proceedHitCheckRequest(HitCheckRequest hitCheckRequest) {
        boolean checkResult = resultCalculator.calculate(hitCheckRequest.getX(),
                hitCheckRequest.getY(),
                hitCheckRequest.getR());
        HitCheckEntity hitCheckEntity = new HitCheckEntity();
        hitCheckEntity.setX(hitCheckRequest.getX());
        hitCheckEntity.setY(hitCheckRequest.getY());
        hitCheckEntity.setR(hitCheckRequest.getR());
        hitCheckEntity.setHitCheckResult(checkResult);
        hitCheckEntity.setHitCheckDate(Instant.now().minusSeconds(hitCheckRequest.getTimezoneOffset() * 60L));
        return hitCheckEntity;
    }

    public List<HitCheckResponse> getAllHitCheckResults(Authentication authentication) {
        List<HitCheckEntity> hitCheckEntities = hitCheckRepo.findAllByUserId(userRepo.findByUsername(authentication.getName()).getId());
        List<HitCheckResponse> hitCheckResponses = new ArrayList<>();
        for (HitCheckEntity hitCheckEntity : hitCheckEntities) {
            hitCheckResponses.add(hitCheckEntityToHitCheckResponseConverter.convert(hitCheckEntity));
        }
        return hitCheckResponses;
    }

    @Transactional
    public void clearHitCheckResultsByUser(Authentication authentication) {
        System.out.println(userRepo.findByUsername(authentication.getName()).getId());
        hitCheckRepo.removeAllByUserId(userRepo.findByUsername(authentication.getName()).getId());
    }
}
