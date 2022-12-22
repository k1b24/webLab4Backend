package com.kib.weblab4.communication;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDetails {

    @NotNull
    private String username;
    @NotNull
    private String password;

}
