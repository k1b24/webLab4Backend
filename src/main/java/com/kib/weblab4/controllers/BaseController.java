package com.kib.weblab4.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kib.weblab4.communication.ErrorMessage;
import com.kib.weblab4.exceptions.PermissionIsMissingException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class BaseController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    void handle(HttpServletResponse response, MethodArgumentNotValidException exception) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ObjectMapper mapper = new ObjectMapper();
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrorList = result.getFieldErrors();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()))) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fieldError : fieldErrorList) {
                sb.append(fieldError.getDefaultMessage());
                sb.append(" ");
            }
            bw.write(mapper.writeValueAsString(new ErrorMessage(sb.toString())));
        }
    }

    @ExceptionHandler(PermissionIsMissingException.class)
    void handlePermissionIsMissingException(HttpServletResponse response, PermissionIsMissingException exception) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }


}
