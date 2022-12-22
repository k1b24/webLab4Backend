package com.kib.weblab4.controllers;

import com.kib.weblab4.communication.UserDetails;
import com.kib.weblab4.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class RegisterController extends BaseController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDetails userDetails) {
        boolean result = registerService.registerNewUser(userDetails);
        //TODO вместо булиан бросать эксепшн и обрабатывать его
        if (result) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
