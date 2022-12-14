package com.kib.weblab4.converters;

import com.kib.weblab4.communication.HitCheckResponse;
import com.kib.weblab4.entities.HitCheckEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HitCheckEntityToHitCheckResponseConverter implements Converter<HitCheckEntity, HitCheckResponse> {
    @Override
    public HitCheckResponse convert(HitCheckEntity source) {
        HitCheckResponse hitCheckResponse = new HitCheckResponse();
        hitCheckResponse.setX(source.getX());
        hitCheckResponse.setY(source.getY());
        hitCheckResponse.setR(source.getR());
        hitCheckResponse.setHitCheckDate(source.getHitCheckDate());
        hitCheckResponse.setResult(source.isHitCheckResult());
        return hitCheckResponse;
    }
}
