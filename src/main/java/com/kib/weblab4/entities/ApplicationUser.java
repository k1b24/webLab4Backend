package com.kib.weblab4.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="users")
@Data
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name="username", unique = true)
    private String username;
    private String password;
    private boolean enabled;
    @OneToMany(cascade = CascadeType.ALL)
    private List<UserAuthority> authorities = new ArrayList<>();

    public void addAuthority(String authority) {
        authorities.add(new UserAuthority(this, authority));
    }

}
