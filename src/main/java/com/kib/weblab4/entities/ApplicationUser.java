package com.kib.weblab4.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name="users")
@Data
public class ApplicationUser implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name="username", unique = true)
    private String username;
    private String password;
    private boolean enabled;
    @OneToMany(cascade = CascadeType.ALL)
    private List<UserAuthority> authorities = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Permission> permissions = new ArrayList<>();

    public void addAuthority(String authority) {
        authorities.add(new UserAuthority(this, authority));
    }

    public void addPermission(String permissionName) {permissions.add(new Permission(this, permissionName));}
}
