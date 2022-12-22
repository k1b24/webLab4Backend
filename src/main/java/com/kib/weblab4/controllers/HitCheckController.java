package com.kib.weblab4.controllers;

import com.kib.weblab4.communication.HitCheckRequest;
import com.kib.weblab4.communication.HitCheckResponse;
import com.kib.weblab4.permissions.HasPermissions;
import com.kib.weblab4.services.HitCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class HitCheckController extends BaseController {

    private final HitCheckService hitCheckService;

    @Autowired
    public HitCheckController(HitCheckService hitCheckService) {
        this.hitCheckService = hitCheckService;
    }

    @HasPermissions(value = {"hello", "there"})
    @PostMapping(value = "/check")
    public HitCheckResponse checkHit(@RequestBody @Valid HitCheckRequest hitCheckRequest, Authentication authentication) {
        return hitCheckService.processHitCheckRequest(hitCheckRequest, authentication);
    }

    @GetMapping(value = "/results")
    public List<HitCheckResponse> getAllHitCheckResults(Authentication authentication) {
        return hitCheckService.getAllHitCheckResults(authentication);
    }

    @PostMapping(value = "/deleteResults")
    public ResponseEntity<?> deleteResults(Authentication authentication) {
        hitCheckService.clearHitCheckResultsByUser(authentication);
        return ResponseEntity.ok().build();
    }



}
