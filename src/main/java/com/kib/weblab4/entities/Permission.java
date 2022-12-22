package com.kib.weblab4.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name="username", referencedColumnName = "username")
    private ApplicationUser user;

    public Permission() {}

    public Permission(ApplicationUser applicationUser, String permissionName) {
        this.name = permissionName;
        this.user = applicationUser;
    }
}
