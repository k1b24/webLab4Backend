package com.kib.weblab4.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name="authorities")
@Data
public class UserAuthority {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name="username", referencedColumnName = "username")
    private ApplicationUser user;

    private String authority;

    public UserAuthority(ApplicationUser applicationUser, String authority) {
        this.user = applicationUser;
        this.authority = authority;
    }
}
