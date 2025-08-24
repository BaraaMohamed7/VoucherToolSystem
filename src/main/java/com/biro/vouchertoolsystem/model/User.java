package com.biro.vouchertoolsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String dial;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", insertable = false)
    private UserRole role = UserRole.CUSTOMER;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
