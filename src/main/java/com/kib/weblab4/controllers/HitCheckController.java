package com.kib.weblab4.controllers;

import com.kib.weblab4.communication.HitCheckRequest;
import com.kib.weblab4.communication.HitCheckResponse;
import com.kib.weblab4.services.HitCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
public class HitCheckController extends BaseController {

    private final HitCheckService hitCheckService;

    @Autowired
    public HitCheckController(HitCheckService hitCheckService) {
        this.hitCheckService = hitCheckService;
    }

    @PostMapping(value = "/check")
    public HitCheckResponse checkHit(@RequestBody @Valid HitCheckRequest hitCheckRequest) {
        //TODO Сохранять юзера
        return hitCheckService.processHitCheckRequest(hitCheckRequest);
    }

        @GetMapping(value = "/results")
    public List<HitCheckResponse> getAllHitCheckResults() {
        //TODO Получать точки, принадлежащие пользователю
        return hitCheckService.getAllHitCheckResults();
    }



}
