package com.kib.weblab4.services;

import com.kib.weblab4.communication.HitCheckRequest;
import com.kib.weblab4.communication.HitCheckResponse;
import com.kib.weblab4.converters.HitCheckEntityToHitCheckResponseConverter;
import com.kib.weblab4.entities.HitCheckEntity;
import com.kib.weblab4.math.ResultCalculator;
import com.kib.weblab4.repo.HitCheckRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
final public class HitCheckService {
    private final ResultCalculator resultCalculator;
    private final HitCheckRepo hitCheckRepo;
    private final HitCheckEntityToHitCheckResponseConverter hitCheckEntityToHitCheckResponseConverter;

    @Autowired
    public HitCheckService(ResultCalculator resultCalculator, HitCheckRepo hitCheckRepo, HitCheckEntityToHitCheckResponseConverter hitCheckEntityToHitCheckResponseConverter) {
        this.resultCalculator = resultCalculator;
        this.hitCheckRepo = hitCheckRepo;
        this.hitCheckEntityToHitCheckResponseConverter = hitCheckEntityToHitCheckResponseConverter;
    }

    public HitCheckResponse processHitCheckRequest(HitCheckRequest hitCheckRequest) {
        HitCheckEntity hitCheckEntity = proceedHitCheckRequest(hitCheckRequest);
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

    public List<HitCheckResponse> getAllHitCheckResults() {
        List<HitCheckEntity> hitCheckEntities = hitCheckRepo.findAll();
        List<HitCheckResponse> hitCheckResponses = new ArrayList<>();
        for (HitCheckEntity elem : hitCheckEntities) {
            hitCheckResponses.add(hitCheckEntityToHitCheckResponseConverter.convert(elem));
        }
        return hitCheckResponses;
    }
}
